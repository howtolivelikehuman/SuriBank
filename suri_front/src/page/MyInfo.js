import React, { Component } from 'react'
import api from '../util/API'
import SubHeader from '../component/SubHeader'
import InfoElement from '../component/InfoElement'
class MyInfo extends Component{
    state={
        data:null,
        no:"1", //test
        modalOpen:false,
        editable_list: [
            'name', 'password', 'nickname', 'major'
        ],
        edit_status:false
    }

    getMyInfo(){
        api.get(`/user/`)
        .then(res => {
            this.setState({data: res.data})
            console.log(res)
        })
        .catch(e => console.log(e))
    }

    setData = (key, value) => {
        var data = this.state.data
        data[key] = value
        this.setState({data:data})
    }

    set_edit_version = () => {
        this.setState({edit_status:true})
        for(var i of this.state.editable_list){
            const now = document.getElementsByName(i)[0]
            if(i==='major') now.disabled = false
            else now.readOnly = false
        }
    }

    edit_handler = () => {
        let data = new Object()
        for(var i of this.state.editable_list){
            var now = document.getElementsByName(i)[0].value
            if(now==null || now=="") continue
            data[i] = now
        }
        console.log(data)

        api.put(`/user/${this.state.no}`, data )
        .then(res => {
            console.log(res);
            if(res.status==200){
                alert('수정 완료되었습니다!')
                this.setState({edit_status:false})
                //window.location.href=""
            }
            else{
                alert('다시 입력해주세요')
            }
        })
    }

    render(){
        let MyInfoList=[]
        if(this.state.data == null) {
            this.getMyInfo()
            return null
        }
        else {
            for(var key in this.state.data){
                MyInfoList.push(<InfoElement k={key} v={this.state.data[key]}></InfoElement>)
            }
            MyInfoList.splice(2, 0, <InfoElement k="password" v=""></InfoElement>)        
            //console.log(this.state.data)
            let EditButton = null
            if(this.state.edit_status)
                EditButton =
                <div>
                    <hr className="my-3"/>
                    <button className="btn btn-secondary w-100" id="edit_btn" onClick={()=>this.edit_handler()}>수정 완료</button>
                </div>
        
        //document.getElementById('editBtn').addEventListener('click', this.open_modify_modal)

        return(
            <div>
                <SubHeader/>
                <div className="container">
                    <div className="card mb-5">
                        <div className="card-header">
                            <div className="row">
                                <h2 className="col-11">My Information</h2>
                                <button className="col-1 btn btn-link" id="editBtn" onClick={()=>this.set_edit_version()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="my-3 bi bi-pencil" viewBox="0 0 16 16">
                                        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div className="card-body px-5">
                            {MyInfoList}
                            {EditButton}
                        </div>
                    </div>
                </div>
                
            </div>
        )
        }
    }
}

export default MyInfo