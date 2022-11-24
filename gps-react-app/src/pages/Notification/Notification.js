import React, { useEffect, useState } from 'react'
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import useAuth from "../../hooks/useAuth";

let stompClient = null;
const Notification = () => {

    const { auth } = useAuth();
  
    const username = auth?.decoded?.sub;   

    useEffect(() => {
        connect()
    }, []);
  
    const [userData, setUserData] = useState({
            username: username,
            CreatorUsername: "",
            connected: false           
    });

        useEffect(() => {
            console.log(userData);
        }, [userData]);

       const connect = () => { 
            let Sock = new SockJS('http://localhost:8080/ws');
            stompClient = over(Sock);
            stompClient.connect({}, onConnected, onError);
        }

        const onConnected = () => {
            setUserData({...userData, "connected": true});
            stompClient.subscribe('/notifications/messages', onPrivateNotification);
        }

       

        const onPrivateNotification = (payload) => {
            const payloadData = JSON.parse(payload.body);            
            setUserData({...userData, "connected": true});   

            if (username === payloadData.creatorName){
                alert(payloadData.senderName + " leaved a review for your app " + payloadData.appId +  " : " + payloadData.message);
                console.log("PAYLOAD", payload)            
            }
        }

        const onError = (err) => {
            console.log("ERROR", err);
        }

        return (null)
      
    }

export default Notification
