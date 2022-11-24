import { useState, useEffect } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose } from '@fortawesome/free-solid-svg-icons'
import ReviewForm from "../../../../styles/ReviewForm.css"
import { FaStar } from 'react-icons/fa'
import axios from "axios";
import useAuth from "../../../../hooks/useAuth";
import SockJS from "sockjs-client";
import {over} from "stompjs";


function AddReviewForm({ review, setOpenPopup, app, setUpdate, creator }) {
    const url = "http://localhost:8080/review";
    const {auth} = useAuth();
    let username = auth?.decoded?.sub;

    const [hover, setHover] = useState(0);
    const [validRating, setValidRating] = useState(false);
    const [rating, setRating] = useState(review != null ? review.rating : 0);

    const [validTitle, setValidTitle] = useState(false);
    const [title, setTitle] = useState(review != null ? review.title : "");

    const [validDescription, setValidDescription] = useState(false);
    const [description, setDescription] = useState(review != null ? review.description : "");

    useEffect(() => {
        if(review!=null){
            setTitle(review?.title)
            setDescription(review?.description)
            setRating(review?.rating)
        }
    },[])

    useEffect(() => {
        setValidTitle(title?.length > 0);
    }, [title])

    useEffect(() => {
        setValidDescription(description?.length > 0);
    }, [description])

    useEffect(() => {
        setValidRating(rating > 0);
    }, [rating])

    const handleSubmit = () => {

        if(review!=null){
            axios.put(url, {
                id: review?.id,
                rating: rating,
                title: title,
                description: description
            })
                .then(res => {
                    console.log(res.data);
                    setUpdate(prev=>!prev);
                });
            setOpenPopup(false)
        }
        else
        axios.post(url, {
            customerName: username,
            applicationName: app,
            rating: rating,
            title: title,
            description: description
        })
            .then(res => {
                console.log(res.data);
                setUpdate(prev=>!prev);
            });
            sendNotification() //websockets added
        setOpenPopup(false)
    }

    //Websockets => sends notification
    let Sock = new SockJS('http://localhost:8080/ws');
    let stompClient =  over(Sock);
   
    const [userData, setUserData] = useState({
            username: username,          
            connected: true        
    });

        useEffect(() => {
            console.log(userData);
        }, [userData]);
 

        const sendNotification = () => { 
            if (stompClient) {
                var notificationBody =
                {
                    senderName: userData.username,
                    message: title,
                    appId: app,
                    creatorName: creator 
                }
                stompClient.send('/app/message', {}, JSON.stringify(notificationBody)); //sends the notification to the CreatorId (if connected)
                setUserData({...userData, "message": "", "AppId": ""});
            }
        }
    //

    return (
        <>
            <section>
                <div className="review-header">
                    <h1>Submit Review</h1><div onClick={() => { setOpenPopup(false) }}><FontAwesomeIcon className="close" icon={faClose} color="red" /></div>
                </div>
                <p className={"stars"}>
                    {[...Array(5)].map((star, i) => {
                        const ratingValue = i + 1;
                        return (
                            <label>
                                <input
                                    type="radio"
                                    name="rating"
                                    value={ratingValue}
                                    onClick={() => { setRating(ratingValue) }}
                                />
                                <FaStar
                                    className="star"
                                    color={ratingValue <= (hover || rating) ? "#4F4746" : "#e4e5e9"}
                                    size={30}
                                    onMouseEnter={() => { setHover(ratingValue) }}
                                    onMouseLeave={() => { setHover(0) }}
                                />
                            </label>
                        )
                    })}
                </p>
                <div className="form">
                    <label htmlFor="title"> Title: </label>
                    <input
                        type="text"
                        id="title"
                        autoComplete="off"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                    <label htmlFor="description"> Description: </label>
                    <textarea
                        rows={5}
                        type="text"
                        id="description"
                        autoComplete="off"
                        onChange={(e) => setDescription(e.target.value)}
                        value={description}
                        required
                    />
                    <button onClick={handleSubmit} disabled={!validDescription || !validTitle || !validRating ? true : false}>Submit</button>
                </div>
            </section>
        </>
    )
}

export default AddReviewForm