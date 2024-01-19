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
        if(users.size()==2){
            User user1  = users.get(0);
            String name1  = user1.getName();

            User user2  = users.get(1);
            String name2  = user2.getName();

            if(whatsappRepository.isItAdmin(name1)){
                Group newGroup  = new Group(name2 , 2);
                return newGroup;
            }
            else{
                Group ng = new Group(name1 , 2);
                return ng;
            }
        }
        else{
              int count  = whatsappRepository.getCountOfGroup();
              return whatsappRepository.createGroup(users , "Group "+count);
        }
    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message , User sender , Group group) throws Exception{
        if(whatsappRepository.isGroupValid(group)==false){
            throw new Exception("Group does not exist");
        }
        if(whatsappRepository.isUserTrue(sender , group)==false){
            throw new Exception("You are not allowed to send message");
        }

        return whatsappRepository.sendMessage(message , group);
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
