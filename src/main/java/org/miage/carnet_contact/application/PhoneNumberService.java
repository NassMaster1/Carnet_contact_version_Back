package org.miage.carnet_contact.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miage.carnet_contact.model.PhoneNumber;
import org.miage.carnet_contact.repository.DAOPhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhoneNumberService {

    private final DAOPhoneNumber PhoneNumberRepository;

    @Transactional
    public void addPhoneNumberToContact(Long id, PhoneNumber phoneNumber){

        log.info("Try to add phone number to contact with id ", id);
        //TO DO ADD VALIDATOR
        PhoneNumberRepository.addPhoneNumberToContact(id,phoneNumber);

        log.info("✅ phone number added to id_contact {} .", id);
    }


    @Transactional
    public void deletePhoneNumberToContact(Long id_contact, Long id_phoneNumber){

        log.info("Try to delete phone number to contact with id ", id_phoneNumber);
        //TO DO ADD VALIDATOR

        PhoneNumberRepository.DeletePhoneNumberToContact(id_contact,id_phoneNumber);

        log.info("✅ phone number deleted in id_contact {} .", id_contact);
    }



}
