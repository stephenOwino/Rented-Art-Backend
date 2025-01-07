package com.stephenowino.Rented_Art_Backend.Service;


import com.stephenowino.Rented_Art_Backend.Repository.ArtistRepo;
import com.stephenowino.Rented_Art_Backend.Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

        @Autowired
        private ArtistRepo artistRepository;

        @Autowired
        private RenterRepo renterRepository;
}

//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        public User findByUsername(String username) {
//                User user = artistRepository.findByUsername(username);
//                return (user != null) ? user : renterRepository.findByUsername(username);
//        }
//
//        public User saveUser(User user) {
//                user.setPassword(passwordEncoder.encode(user.getPassword()));
//                if (user instanceof com.rentart.model.Artist) {
//                        return artistRepository.save((com.rentart.model.Artist) user);
//                } else {
//                        return renterRepository.save((com.rentart.model.Renter) user);
//                }
//        }
//}

