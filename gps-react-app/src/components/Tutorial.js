import React from 'react'

const Tutorial = ({ visible, closeTutorial, content }) => {
  return (
    <div className='overlay'
    style={{
        display: visible ? 'block' : 'none'
    }}>
        <div className='tutorial-wrapper'>
            <div className='tutorial-header'>
                <p>{content.title}</p>
                <span className='close-tutorial-btn' onClick={closeTutorial}>x</span>
            </div>
            <div className='tutorial-content'>
                <div className='tutorial-body'>
                    <h4>{content.header}</h4>
                    <p>{content.body}</p>
                </div>
                <div className='tutorial-footer'>
                    <button className='btn-cancel' onClick={closeTutorial}>Close</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Tutorial