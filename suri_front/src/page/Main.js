import React, { Component } from 'react'
import api from '../util/API'
import Filter from '../component/problem_filter/Filter'
import SubHeader from '../component/SubHeader'
import {Link} from 'react-router-dom'

class Main extends Component{
    state={
        subject_data:null,
        now_page:0,
        total_page: 5,
        type:"",
        subject:[],
        professor:[],
        pb_list:null
    }
    get_problem_list_data_for_test = () => {
        let problem=[
            {
                id:"1", 
                title:"첫 번째 문제 예제", 
                subject:"수미방", 
                uploader:"카와잇규짱", 
                professor:"김민호",
                score:3
            },
            {
                id:"2", 
                title:"디비에 빨리 문제 테이블 만들쟈", 
                subject:"데이터베이스설계", 
                uploader:"엄대장", 
                professor:"홍의경",
                score:5
            },
            {
                id:"3", 
                title:"오늘 야식은 치킨이닭", 
                subject:"웹정보시스템", 
                uploader:"문초코", 
                professor:"황혜수",
                score:1
            },
        ]

        this.setState({pb_list:problem})
    }
    get_subject_data = () => {
        console.log(api.defaults)
        api.get('problem/subjectList')
        .then(res => {
            this.setState({subject_data:res.data})
        })
        .catch(err => console.log(api.defaults.headers)
        )

    }
    get_subject_name = (code) => {
        const subject = this.state.subject_data.find(subject => subject['code'] === code)
        return subject['name']
    }
    get_problem_list_data = () =>{
        //TO DO: get으로 하는 방법?
        api
        .post('/problem/list',
        {
            page:this.state.now_page,
            size:20,
            sort:"registerdate",
            order:"desc",
            filter: {
                type: this.state.type,
                subject: this.state.subject,
                professor:this.state.professor
            }
        })
        .then(res => {
            if(res.status!=200){
                alert("문제 불러오기 실패")
                console.log(res)
                return null
            }
            else{
                //console.log(res)
                this.setState({
                    total_page:res.data.totalPages,
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
                <Link to={{pathname: '/problem', data : {id: problem.id}}} className="list-group-item list-group-item-action">
                    <div className="row">
                    <div className="col-1">{problem.id}</div>
                    <div className="col-3">{problem.title}</div>
                    <div className="col-3">{this.get_subject_name(problem.subject)}</div>
                    <div className="col-2">{problem.professor}</div>
                    <div className="col-2">{problem.uploader}</div>
                    <div className="col-1">{problem.score}</div>
                    </div>
                </Link>
            )
        })

        return problem_list
    }
    set_filter = (title, filter_element) => {
        var filter = this.state[title]
        filter[filter_element] = !filter[filter_element]
        if(title === 'subject') this.setState({subject:filter})
        else if(title === 'professor') this.setState({professor:filter})
        else if(title === 'type') this.setState({type:filter})
    }
    

    render(){
        if(this.state.subject_data===null){
            this.get_subject_data()
            return null
        }
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
                        <nav id="sidebar" className="col-md-4 ">
                            <div className="sidebar-header">
                            </div>
                            <Filter filter_list={['기출','예제']} title={'type'} setFilter={this.set_filter}/>
                            <Filter filter_list={['컴퓨터개론','객체지향프로그래밍','논리회로및실습']} title={'subject'} setFilter={this.set_filter}/>
                        </nav>
                        <div className="col-md-8" id="content">
                            <nav className="navbar ml-auto navbar-expand-sm">
                                <ul class="navbar-nav">
                                    <li class="nav-item">
                                    <a class="nav-link" onClick={()=>this.props.history.push('../makePB')}>+</a>
                                    </li>
                                    <li class="nav-item">
                                    <a class="nav-link" href="#">nav 2</a>
                                    </li>

                                </ul>
                            </nav>
                            <div>
                                <ul class="list-group list-group-flush">
                                <div className="list-group-item">
                                    <div className="row">
                                    <div className="col-1">#</div>
                                    <div className="col-3">제목</div>
                                    <div className="col-3">과목</div>
                                    <div className="col-2">교수</div>
                                    <div className="col-2">작성자</div>
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