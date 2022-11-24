import React, {useState, useEffect, createFactory} from 'react'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faAdd} from '@fortawesome/free-solid-svg-icons'
import {FaStar} from 'react-icons/fa'
import Popup from "./Popup";
import AddReviewForm from "./AddReviewForm";
import axios from "axios";
import useAuth from "../../../../hooks/useAuth";

function ReviewList({ownReview, reviews, app, setUpdate, creator}) {
    const [openPopup, setOpenPopup] = useState(false)
    const [updateList, setUpdateList] = useState(true);
    const {auth} = useAuth();

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/review/${id}`)
            .then(res => {
                console.log(res.status);
                setUpdate(prev=>!prev)
            })
            .catch(err => {
                console.log(err.message);
            });
    }

    useEffect(() => {
        setUpdate(prev=>!prev)
    }, [updateList])

    return (
        <div>
            <div className={"app-reviews"}>
                {
                    auth?.accessToken ?
                        <div>
                            <Popup openPopup={openPopup}><AddReviewForm review={ownReview?ownReview:null} setOpenPopup={setOpenPopup}
                                                                        app={app} setUpdate={setUpdateList} creator={creator}/></Popup>
                            {
                                !ownReview ?
                                    <div className={"add-review-card"} onClick={() => {
                                        setOpenPopup(true)
                                    }}><FontAwesomeIcon icon={faAdd}/></div> :
                                    <div className={"review-card"}>
                                        <div className={"card-title"}>
                                            <p className={"text"}>{ownReview?.title}</p>
                                        </div>
                                        <div className={"card-stars"}>
                                            <p className={"stars"}>
                                                {[...Array(5)].map((star, i) => {
                                                    const ratingValue = i + 1;
                                                    return (
                                                        <label>
                                                            <FaStar
                                                                color={ratingValue <= ownReview?.rating ? "#4F4746" : "#e4e5e9"}
                                                                size={15}
                                                            />
                                                        </label>
                                                    )
                                                })}
                                            </p>
                                            <p className={"nickname"}>{ownReview?.customerName}</p>
                                        </div>
                                        <p className={"card-description"}>{ownReview?.description}</p>
                                        <div className={'review-controls'}>
                                            <button className={"reply-button"} onClick={() => {
                                                setOpenPopup(true)
                                            }}>Update
                                            </button>
                                            <button className={"reply-button"}
                                                    onClick={() => handleDelete(ownReview?.id)}>Delete
                                            </button>
                                        </div>
                                    </div>
                            }
                        </div> : <div></div>
                }

                {reviews?.map(review => review?.id != ownReview?.id ?
                    <div className={"review-card"}>
                        <div className={"card-title"}>
                            <p className={"text"}>{review.title}</p>
                            <p className={"date"}>{review.timePassed}</p>
                        </div>
                        <div className={"card-stars"}>
                            <p className={"stars"}>
                                {[...Array(5)].map((star, i) => {
                                    const ratingValue = i + 1;
                                    return (
                                        <label>
                                            <FaStar
                                                color={ratingValue <= review.rating ? "#4F4746" : "#e4e5e9"}
                                                size={15}
                                            />
                                        </label>
                                    )
                                })}
                            </p>
                            <p className={"nickname"}>{review.customerName}</p>
                        </div>
                        <p className={"card-description"}>{review.description}</p>
                        <p>{review.reply ?
                            <>
                                <br/>
                                <b>Content-creator:</b>
                                <hr/>
                                <div className={"card-title"}>
                                    <p className={"text"}>{review?.reply?.title}</p>
                                </div>
                                <p className={"card-description"}>{review.reply?.description}</p>
                            </> : <p></p>}
                        </p>
                    </div> : <div></div>
                )}
            </div>
        </div>
    )
}

export default ReviewList;