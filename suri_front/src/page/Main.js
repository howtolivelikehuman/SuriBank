import React, { Component } from 'react';
import Modal from 'react-modal'
import api from '../util/API'
import SubHeader from '../component/SubHeader'

class Main extends Component{
    state={
        now_page:0,
        total_page: 5,
        type:"ALL",
        subject:"ALL",
        pb_list:null
    }
    get_problem_list_data_for_test = () => {
        let problem=[
            {
                pb_id:"1", 
                pb_title:"첫 번째 문제 예제", 
                subject:"수미방", 
                uploader:"카와잇규짱", 
                score:3
            },
            {
                pb_id:"2", 
                pb_title:"디비에 빨리 문제 테이블 만들쟈", 
                subject:"데이터베이스설계", 
                uploader:"엄대장", 
                score:5
            },
            {
                pb_id:"3", 
                pb_title:"오늘 야식은 치킨이닭", 
                subject:"웹정보시스템", 
                uploader:"문초코", 
                score:1
            },
        ]

        return this.set_problem_list(problem)
    }

    get_problem_list_data = () =>{
        api
        .get(`/problem/list?page=${this.state.now_page}&sort=registerdate,desc&size=1`)
        // {
        //     "filter": {
        //         "type": this.state.type,
        //         "subject": this.state.subject,
        //     },
        //     "pagination": {
        //         "total_pages": this.state.total_page,
        //         "total_elements": 1,
        //         "current_page": this.state.now_page,
        //         "current_elements": 1
        //     }
        // })
        .then(res => {
            if(res.status!=200){
                alert("문제 불러오기 실패")
                console.log(res)
                return null
            }
            else{
                console.log(res)
                this.setState({
                    total_page:res.data.totalPages,
                    now_page:res.data.page,
                    pb_list:res.data.probleminfo
                })
            }
        })
    }
    page_item_click_handler = (i) => {
        if(i>=0 && i<this.state.total_page)
            this.setState({now_page:i},() => {
                this.get_problem_list_data()
            })
    }

    set_problem_list = () => {
        let problem_list = []
        problem_list = this.state.pb_list.map(problem => {
            return(
                <a href="#" className="list-group-item list-group-item-action">
                    <div className="row">
                    <div className="col-1">{problem.id}</div>
                    <div className="col-3">{problem.title}</div>
                    <div className="col-3">{problem.subject}</div>
                    <div className="col-2">{problem.professor}</div>
                    <div className="col-2">{problem.uploader}</div>
                    <div className="col-1">{problem.score}</div>
                    </div>
                </a>
            )
        })

        return problem_list
    }
    

    render(){
        if(this.state.pb_list==null){
            this.get_problem_list_data()
            return null
        }
        else{
            let page=[]
            let problem_list = this.set_problem_list()
            let current_page = this.state.now_page
            var tot_page=this.state.total_page
            for(let i = 0; i<tot_page; i++){
                if(current_page==i)
                    page.push(<li className="page-item active" onClick={()=>this.page_item_click_handler(i)}><a className="page-link" href="javascript:void(0);">{i}</a></li>)
                else 
                    page.push(<li className="page-item" onClick={()=>this.page_item_click_handler(i)}><a className="page-link" href="javascript:void(0);">{i}</a></li>)
            }
            return(
                <div className="container-fluid">
                    <SubHeader />
                    <div className="main_body row">
                        <nav id="sidebar" className="col-md-3 mx-3">
                            <div className="sidebar-header">
                            </div>
                            <div className="filter card">
                                <div className="filter_header card-header">
                                    <button className="btn btn-link row mw-100" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseFilterSubject">
                                        <span className="col-5">과목</span>
                                    </button>
                                </div>
                                <ul className="filter_list card-body filter_subject collapse show" id="collapseFilterSubject">
                                    <li className="checkbox-wrap">
                                        <input type="checkbox" id="test1" data-filter="" data-filter-type=""></input>
                                        <label className="ml-3" for="test1"><span>test1</span></label>
                                    </li>
                                    <li className="checkbox-wrap">
                                        <input type="checkbox" id="test2" data-filter="" data-filter-type=""></input>
                                        <label className="ml-3" for="test2"><span>test2</span></label>
                                    </li>
                                    <li className="checkbox-wrap">
                                        <input type="checkbox" id="test3" data-filter="" data-filter-type=""></input>
                                        <label className="ml-3" for="test3"><span>test3</span></label>
                                    </li>
                                </ul>
                            </div>
                            <div className="filter card">
                                <div className="filter_header card-header">
                                    <button className="btn btn-link row mw-100" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseFilterSubject">
                                        <span className="col-5">문제 유형</span>
                                    </button>
                                </div>
                                <ul className="filter_list card-body filter_subject collapse show" id="collapseFilterSubject">
                                    <li className="checkbox-wrap">
                                        <input type="checkbox" id="test1" data-filter="" data-filter-type=""></input>
                                        <label className="ml-3" for="test1"><span>기출</span></label>
                                    </li>
                                    <li className="checkbox-wrap">
                                        <input type="checkbox" id="test2" data-filter="" data-filter-type=""></input>
                                        <label className="ml-3" for="test2"><span>기출 X</span></label>
                                    </li>
                                </ul>
                            </div>
                        </nav>
                        <div className="col-md-8" id="content">
                            <nav className="navbar navbar-expand-sm navbar-light bg-light">
                                <ul class="navbar-nav">
                                    <li class="nav-item">
                                    <a class="nav-link" href="#">nav 1</a>
                                    </li>
                                    <li class="nav-item">
                                    <a class="nav-link" href="#">nav 2</a>
                                    </li>
                                    <li class="nav-item">
                                    <a class="nav-link" href="#">nav 3</a>
                                    </li>                                
                                    <li class="nav-item">
                                    <a class="nav-link" href="#">nav 4</a>
                                    </li>
                                </ul>
                            </nav>
                            <div>
                                <ul class="list-group list-group-flush">
                                <div className="list-group-item">
                                    <div className="row">
                                    <div className="col-1">#</div>
                                    <div className="col-3">title</div>
                                    <div className="col-3">subject</div>
                                    <div className="col-2">professor</div>
                                    <div className="col-2">uploader</div>
                                    <div className="col-1">score</div>
                                    </div>
                                </div>
                                    {problem_list}
                                </ul>

                            </div>
                            <div className="my-10"> 
                                <ul class="pagination justify-content-center">
                                    <li className="page-item"><a className="page-link" onClick={()=> this.page_item_click_handler(current_page-1)}>Previous</a></li>
                                    {page}
                                    <li className="page-item"><a className="page-link" onClick={()=> this.page_item_click_handler(current_page+1)}>Next</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            )
        }
    }

}



export default Main