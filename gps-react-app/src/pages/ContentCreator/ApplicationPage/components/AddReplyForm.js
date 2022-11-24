import {useState, useEffect} from "react";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faClose} from '@fortawesome/free-solid-svg-icons'
import ReviewForm from "../../../../styles/ReviewForm.css"
import axios from "axios";

function AddReplyForm({review, setOpenPopup, setUpdate}) {
    const [validTitle, setValidTitle] = useState(false);
    const [title, setTitle] = useState(review != null ? review?.reply?.title : "");

    const [validDescription, setValidDescription] = useState(false);
    const [description, setDescription] = useState(review != null ? review.reply?.description : "");

    useEffect(() => {
        if (review?.reply) {
            setTitle(review?.title)
            setDescription(review?.description)
        }
    }, [])

    useEffect(() => {
        setValidTitle(title?.length > 0);
        console.log(review)
    }, [title])

    useEffect(() => {
        setValidDescription(description?.length > 0);
    }, [description])


    const handleSubmit = () => {
        axios.put(`http://localhost:8080/reply`, {
            id: review?.id,
            reply:{
                title: title,
                description: description
            }
        })
            .then(res => {
                console.log(res.data);
                setUpdate(prev => !prev)
            });
        setOpenPopup(false)
    }

    return (
        <>
            <section>
                <div className="review-header">
                    <h1>Submit Reply</h1>
                    <div onClick={() => {
                        setOpenPopup(false)
                    }}><FontAwesomeIcon className="close" icon={faClose} color="red"/></div>
                </div>
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
                    <button onClick={handleSubmit}
                            disabled={!validDescription || !validTitle ? true : false}>Submit
                    </button>
                </div>
            </section>
        </>
    )
}

export default AddReplyForm