import React from 'react'
import api from '../util/API'

class SolveList extends React.Component{
    state = {
        solveList:[],
        page:0,
        size:5,
        sort:"",
        order:""
    }
    
    get_solve_list = () => {
        const {page, size, sort, order} = this.state
        api.get(`/solve/list/problem/${this.props.problemId}`,
        {
            params:{
                page,
                size,
                sort,
                order,
            }

        })
        .then(res => {
            this.setState({solveList:res.data.solvedInfo})
        })
    }

    componentDidMount(){
        this.get_solve_list()
    }

    render(){
        return(
            <div>
                here
            </div>
        )
    }
}

const SolveView = () =>{
    return(
        <div>

        </div>
    )
}

export default SolveList