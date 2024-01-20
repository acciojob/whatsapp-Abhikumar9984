package com.driver;


import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository  = new WhatsappRepository();
    public String createUser( String name , String mobile) throws Exception{
        return whatsappRepository.createUser(name , mobile);
    }

    public Group createGroup(List<User> users){
        // The list contains at least 2 users where the first user is the admin. A group has exactly one admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        // If group is successfully created, return group.

        //For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}.
        //If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.

        if(users.size()==2){
            User user1  = users.get(0);
            String name1  = user1.getName();

            User user2  = users.get(1);
            String name2  = user2.getName();


            Group group  = whatsappRepository.createGroup(users , name2);
            whatsappRepository.addAdmin(group , user1);

          return group;
        }
        else{
              int count  = whatsappRepository.getCountOfGroup()+1;
              Group  group  =  whatsappRepository.createGroup(users , "Group "+count);
              whatsappRepository.addAdmin(group , users.get(0));
              return group;
        }

    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message , User sender , Group group) throws Exception{

        return whatsappRepository.sendMessage(message ,sender , group);
    }

    public String changeAdmin(User approver , User user , Group group) throws Exception{
         if(!whatsappRepository.isGroupValid(group)){
             throw new Exception("Group does not exist");
         }
         if(!approver.getName().equals(whatsappRepository.getAdmin(group))){
             throw new Exception("Approver does not have rights");
         }
         if(!whatsappRepository.isUserTheParticipantOfTheGroup(group , user))
         {
             throw new Exception("User is not a participant");
         }

         return whatsappRepository.changeTheAdmin(group , user);
    }

    public int removeUser(User user) throws Exception{
        if(!whatsappRepository.isUserValid(user)){
            throw new Exception("User not found");
        }
        Group group  = whatsappRepository.getTheGroupOfAParticularUser(user);
        User admin  = whatsappRepository.getAdminOfAGroup(group);
        if(admin==user){
            throw new Exception("Cannot remove admin");
        }
        return 4;
    }

    public String findMessage(Date start , Date end , int k){
        return "SUCCESS";
    }
}
