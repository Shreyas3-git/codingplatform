package com.crio.codingplateform.repository;


import com.crio.codingplateform.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

}
