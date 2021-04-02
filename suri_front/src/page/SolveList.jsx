import React, { Component } from 'react'
import api from '../util/API'
import SubHeader from '../component/SubHeader'
import SolveListByUser from '../component/SolveListByUser'

class SolveList extends Component{

    render(){
        const {userId} = this.props.location.data
        //document
        return(
            <div>
                <SubHeader/>
                <SolveListByUser userId={userId}/>
            </div>
        )
        
    }
}

export default SolveList