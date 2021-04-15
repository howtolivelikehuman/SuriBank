import React from 'react'
import api from '../util/API'
import {Link} from 'react-router-dom'

class SolveList extends React.Component{
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
        const {problemId} = this.props;
        api.get(`/solve/list/problem/${problemId}`,
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
        .catch(e => {
            console.log(e)
        })
    }

    get_solve_list_mock = () => {
       this.setState({ solveList: [
            {
                userName: "test gyuhee",
                userAnswer: "기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 기나긴 답변 ",
                solveDate : "2020-03-11",
            },
            {
                userName: "현식님",
                userAnswer: "문제 풀이입니다!!!",
                solveDate : "2020-03-01", 
            },
            {
                userName: "오잉또잉",
                userAnswer: "어렵습니다 ㅠㅠㅠㅠㅠㅠ 잘 모르겠어요ㅠㅡㅠ",
                solveDate : "2020-03-01", 
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
                userId = {solve.user_id}
                userName = {solve.userName}
                userAnswer = {solve.userAnswer}
                solveDate = {solve.solveDate}
            />
        )

        return(
            <div>
                {solveViewList}
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

const SolveView = ({ userName, userAnswer, solveDate, userId }) =>{
    return(
        <div class="mt-5">
            <div class="row mt-4">
                <Link class="col-5" to={{pathname: '/solveList', data : {userId: userId}}}>
                    <h5>{userName}</h5>
                </Link>
                <div class="col-7"><p class=" text-right text-muted">{solveDate}</p></div>
            </div>
            <div class="row mb-4 border rounded p-3">
                {userAnswer}
            </div>
        </div>
    )
}

export default SolveList