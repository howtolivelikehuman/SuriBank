import React, { Component } from 'react';
import api from '../util/API'
import SubHeader from '../component/SubHeader'
import ProblemElement from '../component/make_pb/ProblemElement'

class makePB extends Component{
    state={
        id:null,
        subject_data:null,
        pb_data:{
            title:null,
            subject:null,
            type:null,
            professor:null,
            question:null,
            answer:null,
        },
        q_img:[],
        a_img:[]
    }
    set_a_img = (a_img) => {
        this.setState({a_img:a_img})
    }   
    set_q_img = (q_img) => {
        this.setState({q_img:q_img})
    }

    get_user_id = () => {
        api.get(`/user/1`)
        .then(res => {
            this.setState({id: res.data.id})
        })
    }
    get_subject_data = () => {
        api.get('problem/subjectList')
        .then(res => {
            //console.log(res.data)
            this.setState({subject_data:res.data})
        })
        .catch(err => console.log(err))
    }

    make_problem_handler = () => {
        const formData_q = new FormData()
        const formData_a = new FormData()
        let pb_data = new Object()
        console.log(this.state.q_img)

        //make form data (from state.(q_,a_)img)
        for (let i = 0; i < this.state.q_img.length; i++) {
            formData_q.append(`images_q[${i}]`, this.state.q_img[i])
        }
        for (let i = 0; i < this.state.a_img.length; i++) {
            formData_a.append(`images_a[${i}]`, this.state.a_img[i])
        }

        for(var key in this.state.pb_data){
            console.log(document.getElementsByName(key)[0])
            pb_data[key] = document.getElementsByName(key)[0].value
        }
        pb_data['uploader_id']=this.state.id

        //file 객체로 넘기는 부분
        pb_data['q_img']=this.state.q_img
        pb_data['a_img']=this.state.a_img
        //form data로 넘기는 부분 
        //pb_data['q_img']=formData_q
        //pb_data['a_img']=formData_a
        console.log(pb_data)
        api.put('/problem/add',pb_data)
        .then(res => {
            console.log(res)
            if(res.status==200){
                alert('문제가 등록되었습니다!')
            }
        })
    }

    render(){
        if(this.state.id===null) {
            this.get_user_id()
            return null
        }
        else if(this.state.subject_data === null){
            this.get_subject_data()
            return null
        }
        else{
            let pb_input = []
            for(var key in this.state.pb_data){
                pb_input.push(<ProblemElement k={key} subject_data={this.state.subject_data} set_q_img={this.set_q_img} set_a_img={this.set_a_img}></ProblemElement>)
            }
            return(
                <div className="container-fluid">
                    <SubHeader/>
                    <div className="container mb-5 mx-10">
                        <h3>문제 작성</h3>
                        <hr className="my-3"/>
                        {pb_input}
                        <button className="btn btn-primary w-100" onClick={()=>this.make_problem_handler()}>작성 완료</button>
                    </div>
                </div>
            )
        }
    }
}

export default makePB