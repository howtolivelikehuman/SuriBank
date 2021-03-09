import React from 'react'
import ReactStars from "react-rating-stars-component";

const ProblemScore = ({rate,set_rate}) => {
    return(
        <ReactStars
            count={5}
            value={rate}
            onChange={rate => set_rate(rate)}
            size={24}
            isHalf={true}
            emptyIcon={<i className="far fa-star"></i>}
            halfIcon={<i className="fa fa-star-half-alt"></i>}
            fullIcon={<i className="fa fa-star"></i>}
            activeColor="#ffd700"
        />
    )
    
}

export default ProblemScore