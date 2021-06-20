# TCP/IP Chat

###Protocol

1. Message **"/auth login password"** needs for authentication any user
2. Auth-messages:
    1. Message **"/auth_success login"** needs for say to user that authentication is success.
    2. Message **"/auth_failed"** says to user that authentication is not success. 
       After that server send some info about error.
       
3. Message **"/registration login password"** needs for registration user. 
4. Registration-messages have the same meaning as auth-messages:    
    1. **"/registration_success login"**
    2. **"/registration_failed"**
    
5. **"/list"** - server sends list with all online users.
6. **"/delete_account"** - server deletes an account of current user.
7. **"@username message"** - sending the message to user "username".