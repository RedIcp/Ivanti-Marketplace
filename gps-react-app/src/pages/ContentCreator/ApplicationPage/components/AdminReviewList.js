import React, {useState, useEffect} from 'react'
import {FaStar} from 'react-icons/fa'
import Popup from "./Popup";
import AddReplyForm from "./AddReplyForm";
import axios from "axios";

function AdminReviewList({reviews, setUpdate}) {
    const [reply, setReply] = useState(null);
    const [updateList, setUpdateList] = useState(true);
    const [openPopup, setOpenPopup] = useState(false)

    useEffect(() => {
        setUpdate(prev=>!prev)
    }, [updateList])

    const handleDelete = async (reviewID) => {
        const updateReply = {
            id: reviewID,
            reply: null
        }
        const response = await axios.put(`http://localhost:8080/reply`, updateReply);
        setUpdate(prev => !prev)
        console.log(response.status)
    }

    return (
        <div>
            <div className={"app-reviews"}>
                <Popup openPopup={openPopup}><AddReplyForm review={reply} setOpenPopup={setOpenPopup}
                                                           setUpdate={setUpdateList}/></Popup>
                {reviews?.map(review =>
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

                        {review?.reply ?
                            <>
                                <br/>
                                <b>Content-creator:</b>
                                <hr/>
                                <div className={"card-title"}>
                                    <p className={"text"}>{review?.reply?.title}</p>
                                </div>
                                <p className={"card-description"}>{review.reply?.description}</p>
                                <div className={'review-controls'}>
                                    <button className={"reply-button"} onClick={() => {
                                        setReply({
                                            id: review?.id,
                                            reply: review?.reply
                                        });
                                        setOpenPopup(true)
                                    }}>Update
                                    </button>
                                    <button className={"reply-button"}
                                            onClick=
                                                {() => {
                                                    handleDelete(review.id)
                                                }}>Delete
                                    </button>
                                </div>
                            </> : <button className={"reply-button"}
                                          onClick={() => {
                                              setReply({
                                                  id: review?.id,
                                                  reply: review?.reply
                                              });
                                              setOpenPopup(true)
                                          }}>Reply
                            </button>}
                    </div>
                )}
            </div>
        </div>
    )
}

export default AdminReviewList;