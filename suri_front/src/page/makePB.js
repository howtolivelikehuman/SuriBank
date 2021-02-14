import React, { Component } from 'react';
import Modal from 'react-modal'
import api from '../util/API'
import SubHeader from '../component/SubHeader'
import ProblemElement from '../component/make_pb/ProblemElement'

class makePB extends Component{
    state={
        id:null,
        pb_data:{
            title:null,
            subject:null,
            type:null,
            professor:null,
            question:null,
            answer:null,
        },
        img:null
    }
    set_img = (img_file) => {
        this.setState({img:img_file})
    }

    get_user_id = () => {
        api.get(`/user/1`)
        .then(res => {
            console.log(typeof(res.data.id))
            this.setState({id: res.data.id})
        })
    }

    make_problem_handler = () => {
        const formData = new FormData()
        let pb_data = new Object()
        console.log(this.state.img)
        formData.append('file',this.state.img)
        for(var key in this.state.pb_data){
            console.log(document.getElementsByName(key)[0])
            pb_data[key] = document.getElementsByName(key)[0].value
        }
        pb_data['uploader_id']=this.state.id

        console.log(pb_data)
        api.post('/problem/add',pb_data)
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
        else{
            let pb_input = []
            for(var key in this.state.pb_data){
                console.log(typeof(key))
                pb_input.push(<ProblemElement k={key} set_img={this.set_img}></ProblemElement>)
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