import React from 'react'
import {Link} from 'react-router-dom'

import api from '../util/API'

class SolveListByUser extends React.Component{
    state = {
        solveList:[],
        totalPage:0,
        page:0,
        size:2,
        sort:"",
        order:""
    }
    
    page_item_click_handler = (i) => {
        if(i>=0 && i<this.state.totalPage)
            this.setState({page:i},() => {
                this.get_solve_list()
            })
    }

    get_solve_list = () => {
        const {page, size, sort, order} = this.state
        api.get(`/solve/list/user/${this.props.userId}`,
        {
            params:{
                page,
                size,
                sort,
                order,
            }
        })
        .then(res => {
            console.log(res)
            this.setState({solveList:res.data.solvedInfo})
        })
    }

    get_solve_list_mock = () => {
       this.setState({ solveList: [
            {
                problemId: 124435,
                problemName: '문제 제목 1'
            },
            {
                problemId: 1245,
                problemName: '문제 제목 2'
            },
            {
                problemId: 1111,
                problemName: '문제 제목 3'
            },
        ]})
    }

    componentDidMount(){
        this.get_solve_list()
    }

    render(){
        const {  page, totalPage, solveList } = this.state
        let pageView = []

        for(let i = 0; i < totalPage; i++){
            if(page === i)
                pageView.push(<li className="page-item active" onClick={()=>this.page_item_click_handler(i)}><a className="page-link" href="javascript:void(0);">{i}</a></li>)
            else 
                pageView.push(<li className="page-item" onClick={()=>this.page_item_click_handler(i)}><a className="page-link" href="javascript:void(0);">{i}</a></li>)
        }


        const solveViewList = solveList.map(solve => 
            <SolveView 
                problemId = {solve.problem_id}
                problemName = {solve.problemName}
            />
        )

        return(
            <div class="container">
                <div>
                    <ul class="list-group list-group-flush">
                    <div className="list-group-item">
                        <div className="row">
                        <div className="col">문제 번호</div>
                        <div className="col">문제 제목</div>
                        </div>
                    </div>
                        {solveViewList}
                    </ul>

                </div>
                <div className="my-10"> 
                    <ul class="pagination justify-content-center">
                        <li className="page-item">
                            <a className="page-link" onClick={()=> this.page_item_click_handler(page-1)}>
                                Previous
                            </a>
                        </li>
                        {pageView}
                        <li className="page-item">
                            <a className="page-link" onClick={()=> this.page_item_click_handler(page+1)}>
                                Next
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        )
    }
}

const SolveView = ({ problemId, problemName }) =>{
    return(
        <Link to={{pathname: '/problem', data : {id: problemId}}} className="list-group-item list-group-item-action">
            <div className="row">
            <div className="col">{problemId}</div>
            <div className="col">{problemName}</div>

            </div>
        </Link>
    )
}

export default SolveListByUser