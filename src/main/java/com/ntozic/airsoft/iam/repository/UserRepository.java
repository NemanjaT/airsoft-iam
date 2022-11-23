package com.ntozic.airsoft.iam.repository;

import com.ntozic.airsoft.iam.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{email: '?0'}")
    Optional<User> findByEmail(String email);

    @Query("{reference:  '?0'}")
    Optional<User> findByReference(String reference);

    @Query("{reference:  {$in:  ?0}}")
    List<User> findByReferenceIn(List<String> references);
}
