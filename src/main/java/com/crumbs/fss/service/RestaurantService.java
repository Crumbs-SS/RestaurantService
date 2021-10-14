package com.crumbs.fss.service;
import com.crumbs.fss.dto.AddRestaurantDto;
import com.crumbs.fss.dto.UpdateRestaurantDto;
import com.crumbs.fss.exception.DuplicateLocationException;
import com.crumbs.fss.exception.OwnerRestaurantMismatchException;
import com.crumbs.lib.entity.*;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.repository.*;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

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
        String firstName = updateRestaurantDTO.getFirstName();
        if(Strings.isNullOrEmpty(firstName))
            temp.getRestaurantOwner().getUserDetails().setFirstName(firstName);

        String lastName = updateRestaurantDTO.getLastName();
        if(Strings.isNullOrEmpty(lastName))
            temp.getRestaurantOwner().getUserDetails().setLastName(lastName);

        String email = updateRestaurantDTO.getEmail();
        if(Strings.isNullOrEmpty(email))
            temp.getRestaurantOwner().getUserDetails().setEmail(email);

        //Update Restaurant Location
        String street = updateRestaurantDTO.getStreet();
        if(Strings.isNullOrEmpty(street))
            temp.getLocation().setStreet(street);

        String city = updateRestaurantDTO.getCity();
        if(Strings.isNullOrEmpty(city))
            temp.getLocation().setCity(city);

        String state = updateRestaurantDTO.getState();
        if(Strings.isNullOrEmpty(state))
            temp.getLocation().setState(state);

        //Update Restaurant Details
        String name = updateRestaurantDTO.getName();
        if(Strings.isNullOrEmpty(name))
            temp.setName(name);

        Integer priceRating = updateRestaurantDTO.getPriceRating();
        if(priceRating!= null)
            temp.setPriceRating(priceRating);

        //delete old categories
        if(!temp.getCategories().isEmpty())
            restaurantCategoryRepository.deleteByRestaurantID(id);

        //replace with new ones
        List<String> newCategories = updateRestaurantDTO.getCategories();
        if(newCategories!= null) {
            newCategories.forEach(category -> restaurantCategoryRepository.insertRestaurantCategory(temp.getId(), category));
        }

        //update menu items
        List<MenuItem> newMenu = updateRestaurantDTO.getMenu();

        //only update menu if there were changes
        if(newMenu != null) {

            List<MenuItem> oldMenu = temp.getMenuItems();

            //for newly added menu items, set restaurant to restaurant (otherwise restaurant_ID stays null on save)
            newMenu.forEach(item -> {
                if (item.getId() == null)
                    item.setRestaurant(temp);
            });
            temp.setMenuItems(newMenu);

            //for old menu items that are now deleted, delete them (they don't automatically delete on save)
            if(oldMenu != null && !oldMenu.isEmpty()) {
                oldMenu.forEach(item -> {
                    if (!newMenu.contains(item))
                        menuItemRepository.delete(item);
                });
            }
        }

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
