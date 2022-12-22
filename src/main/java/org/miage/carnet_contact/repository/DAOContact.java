package org.miage.carnet_contact.repository;


import org.miage.carnet_contact.exception.ContactNotFoundExceprion;
import org.miage.carnet_contact.model.Contact;
import org.miage.carnet_contact.util.JPAutile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class DAOContact implements IDAOContact {
    
    
    
    /**
     * Rajoute un contact dans la base de donnees.

     * @param detailContact
     * @return  le nouveau contact
     */
    @Override
    public void saveContact( Contact detailContact) {

        //1: obtenir une connexion et un EntityManager, en passant par la classe JpaUtil
        EntityManager em=JPAutile.getEmf().createEntityManager();

        // 2 : Ouverture transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 3 : Persistance Objet/Relationnel : création d'un enregistrement en base
        em.persist(detailContact);

        // 4 : Fermeture transaction
        tx.commit();
        // 5 : Fermeture de l'EntityManager et de unité de travail JPA
        em.close();

    }


    @Override
    public Optional<Contact>  findcontactById(long id) {
        //1: obtenir une connexion et un EntityManager, en passant par la classe JpaUtil
        EntityManager em=JPAutile.getEmf().createEntityManager();

        // 2 : Ouverture transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 3 : recuperation de l'objet
        Contact contact = em.find(Contact.class,id);
        // 4 : Fermeture transaction
        tx.commit();
        // 5 : Fermeture de l'EntityManager et de unité de travail JPA
        em.close();

        return Optional.ofNullable(contact);
    }

    @Override
    public Optional<List<Contact>>findAll() {
        //Lors d'un appel à createNativeQuery(requete, type de l'entité retournée)
        //il faut que le select mentionne toutes les colonnes/attributs de l'entité
        final String nativeQuery="SELECT id_contact, firstName, lastName, email,id_address FROM contact";

        EntityManager em=JPAutile.getEmf().createEntityManager();
        //Version avec position
        List<Contact> contacts= em.createNativeQuery(nativeQuery,Contact.class).getResultList();

        em.close();

        return Optional.ofNullable(contacts);
    }

    public Optional<List<Contact>> findByFirstNameAndLastName(String firstName, String lastName) {

        EntityManager em=JPAutile.getEmf().createEntityManager();

        TypedQuery<Contact> query = em.createNamedQuery("Contact.findContactByFirstNameAndLastName", Contact.class);
        query.setParameter("firstname", firstName);
        query.setParameter("lastname", lastName);

        List<Contact> contacts = query.getResultList();

        em.close();

        return Optional.ofNullable(contacts);
    }


    @Override
    public Optional<List<Contact>> findContactByFirstName(String firstname) {

        EntityManager em=JPAutile.getEmf().createEntityManager();

        TypedQuery<Contact> query = em.createNamedQuery("Contact.findContactByFirstName", Contact.class);
        query.setParameter("firstname", firstname);

        List<Contact> contacts = query.getResultList();

        em.close();

        return Optional.ofNullable(contacts);
    }

    @Override
    public Optional<List<Contact>> findContactByLastName(String lastname) {
        EntityManager em=JPAutile.getEmf().createEntityManager();

        TypedQuery<Contact> query = em.createNamedQuery("Contact.findContactBylastName", Contact.class);
        query.setParameter("lastName", lastname);

        List<Contact> contacts = query.getResultList();

        em.close();

        return Optional.ofNullable(contacts);
    }

    @Override
    public Optional<List<Contact>> findContactByEmail(String email) {

        EntityManager em=JPAutile.getEmf().createEntityManager();
        //Version avec position
        String requete = "SELECT c FROM Contact c WHERE c.email=?1";
        TypedQuery<Contact>  query = em.createQuery(requete, Contact.class);
        query.setParameter(1, email);
        List<Contact> contacts = query.getResultList();

        em.close();
        return Optional.ofNullable(contacts);
    }


    @Override
    public void deleteContact(Long id ) {
        //1: obtenir une connexion et un EntityManager, en passant par la classe JpaUtil
        EntityManager em=JPAutile.getEmf().createEntityManager();

        // 2 : Ouverture transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 3 : recuperation de l'objet
        Contact contact = em.find(Contact.class,id);

        if(contact!=null){
            //suppression des relations
            contact.getPhoneNumber().forEach(phoneNumber -> phoneNumber.setContact(null));
            contact.setAdresse(null);

            em.remove(contact);
        }else {
            throw new ContactNotFoundExceprion(id);
        }

        // 4 : Fermeture transaction
        tx.commit();
        // 5 : Fermeture de l'EntityManager et de unité de travail JPA
        em.close();
    }


    @Override
    public void UpdateContact(Long id, Contact contactModify) {

        //1: obtenir une connexion et un EntityManager, en passant par la classe JpaUtil
        EntityManager em=JPAutile.getEmf().createEntityManager();

        // 2 : Ouverture transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Contact contact = em.find(Contact.class,id);

        if(contact!=null){
            contact.setFirstName(contactModify.getFirstName());
            contact.setLastName(contactModify.getLastName());
            contact.setEmail(contactModify.getEmail());

        }
        else{
            throw new ContactNotFoundExceprion(id);
        }

        // 4 : Fermeture transaction
        tx.commit();
        // 5 : Fermeture de l'EntityManager et de unité de travail JPA
        em.close();
    }



}
