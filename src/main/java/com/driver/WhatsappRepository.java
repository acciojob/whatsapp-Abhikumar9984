package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private HashMap<Integer , String> message;
    private HashMap<String , User> user;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
        this.user  = new HashMap<>();
        this.message  = new HashMap<>();
    }

    public boolean getMobile(String userMobile){
        if(userMobile.contains(userMobile)){
            return true;
        }
        else
            return false;
    }

    public String  createUser(String name , String mobile) throws Exception{
        if(!user.containsKey(mobile)) {
            User newUser = new User(name , mobile);
            user.put(mobile , newUser);
            return "SUCCESS";
        }
        else{
             throw new Exception("User already exists");
        }
    }
    public void addAdmin(Group group , User user){
        adminMap.put(group , user);
    }
    public boolean isItAdmin(String name){
        for(Group group : adminMap.keySet()){
            User user  = adminMap.get(group);
            String s  = user.getName();
            if(s.equals(name)) return true;
        }
        return false;
    }

    public int getCountOfGroup(){
        int count  = 0;
        for(Group group : groupUserMap.keySet()){
            List<User> temp  = groupUserMap.get(group);
            if(temp.size()>2)
                count++;
        }
        return count;
    }

    public Group createGroup(List<User> users ,String name){
        Group group  = new Group(name , user.size());
        groupUserMap.put(group , users);

        return group;
    }

    public int createMessage(String content){
        int size  = message.size();
        message.put(size+1 , content);

        return size+1;
    }

    public boolean isGroupValid(Group group){
        if(groupUserMap.containsKey(group)) return true;
        return false;
    }

    public boolean isUserTrue(User user , Group group){
        List<User> temp  = groupUserMap.get(group);
        for(User u : temp){
            if(user==u)
                return true;
        }
        return false;
    }
    public int sendMessage(Message message ,User sender , Group group) throws Exception{
        if(groupUserMap.containsKey(group) == false){
             throw new Exception("Group does not exist");
        }
        List<User> groupList  = groupUserMap.get(group);
        if(groupList.contains(sender)==false){
            throw new Exception("You are not allowed to send message");
        }
        List<Message> temp  = groupMessageMap.get(group);
        temp.add(message);
        groupMessageMap.put(group , temp);
        return temp.size();
    }

    public boolean isUserValid(User user){
        String name  = user.getName();
        for(Group g : groupUserMap.keySet()){
            List<User> temp  = groupUserMap.get(g);
            for(User u : temp){
                String name1  = u.getName();
                if(name1.equals(name)) return true;
            }
        }
        return false;
    }

    public String getAdmin(Group group){
        User user  = adminMap.get(group);
        return user.getName();
    }

    public boolean isUserTheParticipantOfTheGroup(Group group , User user){
        List<User> temp  = groupUserMap.get(group);

        for(User u : temp){
            if(u==user) return true;
        }

        return false;
    }

    public String changeTheAdmin(Group group , User user){
        adminMap.put(group , user);
        return "SUCCESS";
    }

    public Group getTheGroupOfAParticularUser(User user){
        for(Group g : groupUserMap.keySet()){
            List<User> temp  = groupUserMap.get(g);
            for(User u : temp){
                if(user==u) return g;
            }
        }
        return null;
    }

    public User getAdminOfAGroup(Group group){
        return adminMap.get(group);
    }

}
