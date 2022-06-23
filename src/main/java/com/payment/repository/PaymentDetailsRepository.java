package com.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payment.entity.PaymentDetails;

public interface PaymentDetailsRepository extends MongoRepository<PaymentDetails, String> {

}
