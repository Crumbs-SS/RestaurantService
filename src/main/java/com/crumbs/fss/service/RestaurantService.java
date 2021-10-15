package com.crumbs.fss.service;
import com.crumbs.fss.dto.AddRestaurantDto;
import com.crumbs.fss.dto.UpdateRestaurantDto;
import com.crumbs.fss.exception.DuplicateLocationException;
import com.crumbs.fss.exception.OwnerRestaurantMismatchException;
import com.crumbs.lib.entity.*;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final LocationRepository locationRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final UserDetailsRepository userDetailRepository;
    private final RestaurantStatusRepository restaurantStatusRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    RestaurantService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            LocationRepository locationRepository,
            RestaurantCategoryRepository restaurantCategoryRepository,
            UserDetailsRepository userDetailRepository,
            RestaurantStatusRepository restaurantStatusRepository
    ){
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.locationRepository = locationRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.userDetailRepository = userDetailRepository;
        this.restaurantStatusRepository = restaurantStatusRepository;
    }

    public Owner checkOwnerExists(String username){
        UserDetails user = userDetailRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Owner owner = user.getOwner();
        if (null == owner) {throw new EntityNotFoundException();}
        return owner;
    }
    public boolean checkRestaurantBelongsToOwner(Owner owner, Owner restaurantOwner){
        return owner.getId().equals(restaurantOwner.getId());
    }

    public List<Restaurant> getOwnerRestaurants(String username){
        Owner owner = checkOwnerExists(username);
        return owner.getRestaurants();
    }

    public Restaurant addRestaurant(String username, AddRestaurantDto a) {

        Owner owner = checkOwnerExists(username);

        if(locationRepository.findLocationByStreet(a.getStreet())!=null)
            throw new DuplicateLocationException();

        Location location = Location.builder()
                .street(a.getStreet())
                .city(a.getCity())
                .state(a.getState())
                .build();

        locationRepository.save(location);

        RestaurantStatus restaurantStatus = restaurantStatusRepository.findById("REGISTERED").orElse(null);

        Restaurant temp = Restaurant.builder()
                .name(a.getName())
                .priceRating(a.getPriceRating())
                .location(location)
                .restaurantOwner(owner)
                .restaurantStatus(restaurantStatus)
                .build();

        Restaurant restaurant = restaurantRepository.save(temp);

        List<String> categories = a.getCategories();
        if(categories!= null && !categories.isEmpty()) {
            categories.forEach(category -> restaurantCategoryRepository.insertRestaurantCategory(restaurant.getId(), category));
        }
        return restaurant;
    }


    public Restaurant updateRestaurant(String username, Long id, UpdateRestaurantDto updateRestaurantDTO){

        Owner owner = checkOwnerExists(username);
        Restaurant temp = restaurantRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(!checkRestaurantBelongsToOwner(owner, temp.getRestaurantOwner()))
            throw new OwnerRestaurantMismatchException();

        if(updateRestaurantDTO.getStreet() != null && !updateRestaurantDTO.getStreet().equals(temp.getLocation().getStreet()) && locationRepository.findLocationByStreet(updateRestaurantDTO.getStreet())!=null)
            throw new DuplicateLocationException();

        // Update User Details
        Optional<String> firstName = Optional.of(updateRestaurantDTO.getFirstName());
        firstName.ifPresent(s -> temp.getRestaurantOwner().getUserDetails().setFirstName(s));

        Optional<String> lastName = Optional.of(updateRestaurantDTO.getLastName());
        lastName.ifPresent(s -> temp.getRestaurantOwner().getUserDetails().setLastName(s));

        Optional<String> email = Optional.of(updateRestaurantDTO.getEmail());
        email.ifPresent(s -> temp.getRestaurantOwner().getUserDetails().setEmail(s));

        //Update Restaurant Location
        Optional<String> street = Optional.of(updateRestaurantDTO.getStreet());
        street.ifPresent(s -> temp.getLocation().setStreet(s));

        Optional<String> city = Optional.of(updateRestaurantDTO.getCity());
        city.ifPresent(s -> temp.getLocation().setCity(s));

        Optional<String> state = Optional.of(updateRestaurantDTO.getState());
        state.ifPresent(s -> temp.getLocation().setState(s));

        //Update Restaurant Details
        Optional<String> name = Optional.of(updateRestaurantDTO.getName());
        name.ifPresent(temp::setName);

        Optional<Integer> priceRating = Optional.of(updateRestaurantDTO.getPriceRating());
        priceRating.ifPresent(temp::setPriceRating);

        //delete old categories
        if(!temp.getCategories().isEmpty()) restaurantCategoryRepository.deleteByRestaurantID(id);

        //replace with new ones
        Optional<List<String>> newCategories = Optional.of(updateRestaurantDTO.getCategories());
        newCategories.ifPresent(categories -> categories.forEach(category -> restaurantCategoryRepository.insertRestaurantCategory(temp.getId(), category)));

        //update menu items
        Optional<List<MenuItem>> newMenu = Optional.of(updateRestaurantDTO.getMenu());
        newMenu.ifPresent(menu ->
        {
            Optional<List<MenuItem>> oldMenu = Optional.of(temp.getMenuItems());
            //delete items not contained in new menu
            oldMenu.ifPresent(m ->
            {
                m.forEach(item -> {
                    if (!menu.contains(item))
                        menuItemRepository.delete(item);
                });
            });

            //for newly added menu items, set restaurant to restaurant (otherwise restaurant_ID stays null on save)
            menu.forEach(item -> {
                if (item.getId() == null) item.setRestaurant(temp);
            });
            temp.setMenuItems(menu);

        });

        return restaurantRepository.save(temp);
    }
    public Restaurant requestDeleteRestaurant(String username, Long id){

        Owner owner = checkOwnerExists(username);
        Restaurant temp = restaurantRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(!checkRestaurantBelongsToOwner(owner, temp.getRestaurantOwner()))
            throw new OwnerRestaurantMismatchException();

        RestaurantStatus status = restaurantStatusRepository.findById("PENDING_DELETE").orElse(null);
        temp.setRestaurantStatus(status);

        return restaurantRepository.save(temp);
    }


}
