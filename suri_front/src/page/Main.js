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
        type:{
            prev:false,
            non_prev:false, 
        },
        subject:{},
        professor:{
            hwang:false,
            kim:false,
            gyuzzzang:false
        },
        pb_list:null
    }

    get_subject_data = () => {
        console.log(api.defaults)
        api.get('problem/subjectList')
        .then(res => {
            let subject_list = new Object()
            res.data.map(subject => subject_list[subject['name']]=false)
            this.setState({subject:subject_list, subject_data:res.data})
        })
        .catch(err => console.log(api.defaults.headers)
        )
    }
    get_subject_data = () => {
        console.log(api.defaults)
        api.get('problem/subjectList')
        .then(res => {
            this.setState({subject_data:res.data})
            let subject_list = new Object()
            res.data.map(subject => subject_list[subject['name']]=false)
            this.setState({subject:subject_list})
        })
        .catch(err => console.log(api.defaults.headers)
        )
    }
    
    get_subject_name = (code) => {
        const subject = this.state.subject_data.find(subject => subject['code'] === code)
        return subject['name']
    }
    get_subject_code = (n) => {
        console.log(n)
        const subject = this.state.subject_data.find(subject => subject['name'] === n)
        return subject['code']
    } 

    get_problem_list_data = () =>{
        //TO DO: get으로 하는 방법?
        console.log(this.state.type, this.state.subject, this.state.professor)
        let type = []
        let subject = []
        let professor =[]
        for(var key in this.state.type){
            if(this.state.type[key]) type.push(key)
        }
        if (type.length === 0 || type.length === 2) type = ""
        else if(type[0] == 'prev') type = "1"
        else type = "0"

        for(var key in this.state.subject){
            if(this.state.subject[key]) subject.push(this.get_subject_code(key))
        }
        for(var key in this.state.professor){
            if(this.state.professor[key]) professor.push(key)
        }
        console.log(type, subject, professor)
        api
        .get('/problem/list',
        {
            params:{        
                page:this.state.now_page,
                size:20,
                sort:"registerdate",
                order:"desc",
                // filter: {
                //     type: type,
                //     subject: subject,
                //     professor:professor
                // }
            }
        })
        .then(res => {
            console.log(res.data)
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
        console.log(title,filter_element)
        var filter
        if(title === 'TYPE') {
            filter_element = (filter_element === '기출' ? 'prev' : 'non_prev')
            filter = this.state.type
            filter[filter_element] = !filter[filter_element]
            this.setState({type:filter})
        }
        else if(title === 'SUBJECT') {
            filter = this.state.subject
            filter[filter_element] = !filter[filter_element]
            this.setState({subject:filter})
        }
        else if(title === 'PROFESSOR') {
            filter = this.state.professor
            filter[filter_element] = !filter[filter_element]
            this.setState({professor:filter})
        }
    }

    render()
    {
        if(this.state.subject_data === null){
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
                        <nav id="sidebar" className="col-md-3 ">
                            <div className="sidebar-header">
                            </div>
                            <Filter filter_list={['기출','예제']} title={'TYPE'} setFilter={this.set_filter}/>
                            <Filter filter_list={['컴퓨터개론','객체지향프로그래밍','논리회로및실습']} title={'SUBJECT'} setFilter={this.set_filter}/>
                            <Filter filter_list={['hwang','kim','gyuzzzang']} title={'PROFESSOR'} setFilter={this.set_filter}/>
                            <div className="row">
                                <button className="p-2 col-10 mx-auto border-0 bg-light rounded rounded-pill shadow-sm my-4" onClick={this.get_problem_list_data}>조건 검색</button>
                            </div>
                        </nav>
                        <div className="col-md-9" id="content">                            
                            <div className="row ">
                                <button className="p-2 col-10 mx-auto border-0 bg-light rounded rounded-pill shadow-sm mb-4" onClick={()=>this.props.history.push('../makePB')}>+</button>
                            </div>   
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