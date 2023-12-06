package com.techelevator.service;

import com.techelevator.dao.PropertyDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Address;
import com.techelevator.model.Property;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService{

    private PropertyDao propertyDao;

    private UserDao userDao;
    public List<Property> searchPropertiesByZipcode (String zipcode) {
        try {
           return propertyDao.getPropertiesByZipcode(zipcode);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }
    @Override
    public List<Property> searchPropertiesByStateAndCity (String state, String city) {
        try {
            return propertyDao.getPropertiesByCityState(state, city);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }
    @Override
    public List<Property> searchPropertiesByNumberOfRooms (int numberOfRooms) {
        try {
            return propertyDao.getPropertiesByNumberOfRooms(numberOfRooms);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }
    @Override
    public List<Property> searchPropertiesByRent (BigDecimal rent) {
        try {
            return propertyDao.getPropertiesByRent(rent);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }
    @Override
    public List<Property> searchPropertiesByAvailability (boolean isAvailable) {
        try {
            return propertyDao.getPropertiesByAvailability(isAvailable);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }

//    @Override
//    public List<Property> searchProperties(String city, String state, String zipcode, int numberOfRooms, BigDecimal rent, boolean isAvailable) {
//        try {
//            List<Property> propertyList = new ArrayList<>();
////            if ((state != null && !state.isEmpty()) || (city != null && !city.isEmpty())) {
////                hotelList = hotelDao.getHotelsByStateAndCity(state, city);
////            } else {
////                hotelList = hotelDao.getHotels();
//////            }
////            propertyDao.getPropertiesByNumberOfRooms();
////            propertyDao.getPropertiesByRent();
////            propertyDao.getPropertiesByZipcode();
////            propertyDao.getPropertiesByCityState();
////            propertyDao.getPropertiesByAvailability();
////
//            if (address != null ) {
//
//                if (city != null && !city.isEmpty() && state != null && !state.isEmpty() && zipcode != null && !zipcode.isEmpty()&& numberOfRooms != 0 && rent != null && isAvailable) {
//
//                }
//            }
//
//
//            return propertyList;
//        } catch (DaoException e){
//            throw new ServiceException("An error has occurred: " + e.getMessage());
//        }
//    }

    @Override
    public Property viewPropertyById(Principal principal, int propertyId) {
        try {
            return propertyDao.getPropertyById(propertyId);
        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }

//    @Override
//    public List<Property> viewPropertiesByNumberOfRooms(Principal principal, int numberOfRooms) {
//        return null;
//    }
//
//    @Override
//    public List<Property> viewPropertiesByZipcode(Principal principal, String zip) {
//        return null;
//    }
//
//    @Override
//    public List<Property> viewPropertiesByAvailability(Principal principal, boolean isAvailable) {
//        return null;
//    }
    @Override
    public List<Property> viewPropertiesByUsername(Principal principal, String username) {
        try {
            List<Property> properties = new ArrayList<>();
            if(principal.getName().equalsIgnoreCase(username)) {
                properties = propertyDao.getPropertiesByUsername(username);
            }
            return properties;

        } catch (DaoException e) {
            throw new ServiceException("An error has occurred: " + e.getMessage());
        }
    }

    @Override
    public Property createProperty(Principal principal, Property property) {
        return null;
    }

    @Override
    public Property updateProperty(Principal principal, int propertyId, Property updatedProperty) {
        return null;
    }
}
