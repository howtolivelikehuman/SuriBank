import React, { Component } from 'react';
import api from '../util/API'
import SubHeader from '../component/SubHeader'
import { IoTimeSharp } from 'react-icons/io5';
import ProblemScore from '../component/ProblemScore'
class Problem extends Component{
    state={
        data:null,
        subject_list:null,
        rate:5
    }
    post_score = () => {
        let score = new FormData()
        score.append('score',this.state.rate)
        api.post(`problem/score/${this.props.location.data.id}`, score )
        .then(res => {
            if(res.status===200) 
                alert('별점이 반영되었습니다!')
        })
    }
    get_pb_data = () => {
        api.get(`problem/${this.props.location.data.id}`)
        .then(res => {
            this.setState({data:res.data})
        })
    }
    set_rate=rate=>{
        this.setState({rate:rate})
    }
    get_pb_data_for_test =()=>{
        let problem=
            {
                "uploader":"카와잇규짱",
                "title": "리액트 뭐냐고 어렵다고",
                "subject": "리액트",
                "type" : "예제",
                "professor": "엄교수",
                "question" :"이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!",
                "answer" : "새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!" ,
                "registerdate":"2021-2-14",
                "score": 4.5,
                "hit": 12
            }

        this.setState({data:problem})
    }
    view_answer_handler = () => {
        let isVisible = document.getElementsByName('answer')[0].className
        if(isVisible === "invisible") document.getElementsByName('answer')[0].className = 'visible'
        else document.getElementsByName('answer')[0].className = 'invisible'
    }
    get_subject_list = () => {
        api.get('/problem/subjectList')
        .then(res => {
            //console.log(res)
            this.setState({subject_list:res.data})
        })
    }
    get_subject_name = (code) => {
        const subject = this.state.subject_list.find(subject => subject['code'] === code)
        return subject['name']
    }

    render(){
        if(this.state.data===null || this.state.subject_list===null){
            this.get_pb_data()
            this.get_subject_list()
            return null
        }

        else{
            let problem =[]
            let value
            for(var key in this.state.data){
                if (key==='title'||key ==='question' || key === 'answer' || key === 'id') continue
                if (key==='subject'){
                    value=this.get_subject_name(this.state.data[key])
                }
                else value=this.state.data[key]
                problem.push(
                    <div className="row info_element mb-3">
                        <div className="col-sm-5 key">
                            {key}
                        </div>
                        <div className="col-sm-7 value">
                            {value}
                        </div>
                    </div>
                )
            }
            return(
                <div>
                    <SubHeader/>
                    <div className="container">
                        <div className="card mb-5">
                            <div className="card-header">
                                <div className="row">
                                    <h2 className="col-11">{this.state.data['title']}</h2>
                                </div>
                            </div>
                            <div className="card-body px-5">
                                {problem}
                                <div className="my-3">
                                    <div className="key">question</div>
                                    <div className="value my-2">
                                        {this.state.data['question']}
                                    </div>
                                </div>
                                <div name="answer" className="invisible">
                                    <div className="key">answer</div>
                                    <div className="value my-2">
                                        {this.state.data['answer']}
                                    </div>
                                </div>
                                <button type="button" className="btn btn-secondary" onClick={()=>this.view_answer_handler()}>view answer</button>
                            </div>
                        </div>
                        <ProblemScore rate={this.state.rate} set_rate={this.set_rate}/>
                        <div className="row"><button onClick={this.post_score} className="btn btn-primary mx-3 mw-100">별점주기</button></div>
                    </div>
                    
                </div>

            )
        }
    }
}

export default Problem