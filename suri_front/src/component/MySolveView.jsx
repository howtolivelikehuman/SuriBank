import React from 'react'
import api from '../util/API'
import EditBtn from './EditBtn'

const MySolveView = ({mySolveData})=> {
    const {user_id, userAnswer, score, solveDate} = mySolveData

    const handleEditBtn = () => {
        const {isEdit} = this.state
        this.setState({isEdit:!isEdit})
    }
    console.log(mySolveData)

    return(
        <div class="mt-5">
            <div class="row mt-4">
                <div class="col" >
                    <h5>{user_id}</h5>
                </div> 
                <div class="col">
                    <div class='row  align-bottom'>
                        <p class="col-10 m-0 text-right text-muted">{solveDate}</p>
                    </div>
                </div>
            </div>
            <div class="row mb-4 border rounded p-3">
                <div class="col-10">{userAnswer}</div>
                <EditBtn onClick = {handleEditBtn}/>
            </div>
        </div>
    )

}

export default MySolveView