import React, { Component } from 'react'
import api from '../util/API'
import Filter from '../component/problem_filter/Filter'
import SubHeader from '../component/SubHeader'
import {Link} from 'react-router-dom'

class Main extends Component{
    state={
        subject_data:null,
        professorData:null,
        now_page:0,
        total_page: 5,
        filters:[],
        type_data:[
            {code:1, name:'기출'},
            {code:0, name:'예제'},
       
        ],
        pb_list:null
    }

    get_subject_data = () => {
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

    getProfessorData = () => {
        api.get('problem/professorList')
        .then(res => {
            this.setState({professorData:res.data})
            let professorList = new Object()
            res.data.map(pf => professorList[pf['name']]=false)
            this.setState({professor:professorList})
        })
        .catch(err => console.log(err))
    }

    get_professor_name = code => {
        const {professorData} = this.state
        const professor = professorData.find(p => p['code'] === code)
        return professor['name']
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
        const filters = this.state.filters.join();
        api
        .get('/problem/list',
        {
            params:{        
                page:this.state.now_page,
                size:20,
                sort:"registerdate",
                order:"desc",
                filter: filters,
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
                console.log(res)
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
        const {pb_list} =this.state
        problem_list = pb_list? pb_list.map(problem => {
            return(
                <Link to={{pathname: '/problem', data : {id: problem.id}}} className="list-group-item list-group-item-action">
                    <div className="row">
                    <div className="col-1">{problem.id}</div>
                    <div className="col-3">{problem.title}</div>
                    <div className="col-3">{this.get_subject_name(problem.subject)}</div>
                    <div className="col-2">{this.get_professor_name(problem.professor)}</div>
                    <div className="col-2">{problem.uploader}</div>
                    <div className="col-1">{problem.score}</div>
                    </div>
                </Link>
            )
        }):[]

        return problem_list
    }

    set_filter = (title, filter_element) => {
        const {filters} = this.state
        const element =`${title}:${filter_element}`
        const removeFilters = filters.filter(i => i != element)
        if(removeFilters.length<filters.length){
            this.setState({filters: removeFilters})
        }
        else {
            filters.push(element)
            this.setState({filters: filters})
        }
    }
    componentDidMount(){
        this.get_subject_data()
        this.get_problem_list_data()
        this.getProfessorData()
    }
    
    render()
    {
        let page=[]
        let problem_list = this.set_problem_list()
        let current_page = this.state.now_page
        let tot_page=this.state.total_page
        const {professorData, subject_data, type_data} = this.state
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
                        <Filter filter_list={type_data} title={'TYPE'} setFilter={this.set_filter}/>
                        <Filter filter_list={subject_data} title={'SUBJECT'} setFilter={this.set_filter}/>
                        <Filter filter_list={professorData} title={'PROFESSOR'} setFilter={this.set_filter}/>
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



export default Main