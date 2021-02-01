import { Component } from "react"
import Modal from 'react-modal'
import api from "./API"

class EditInfo extends Component{
    state = {
        data:this.props.data
    }
    set_value = () => { 
        for(var key in this.state.data){
            //console.log(document.getElementsByName(key))
            if(document.getElementsByName(key) != null && document.getElementsByName(key).length) {
                document.getElementsByName(key)[0] = this.state.data[key]
            }
        }
    }
    
    input_handler = (e) => {
        const{name, value}=e.target
        this.props.setData(name, value)
    }
    edit_handler = () => {
        //console.log(this.state.data)
        var id = '1' //test용
        api.put(`/user/${id}`, this.state.data)
        .then(res => {
            console.log(res);
            if(res.status==200){
                alert('수정 완료되었습니다!')
                this.props.closeModal()
            }
            else{
                alert('다시 입력해주세요')
            }
        })
    }
    render(){
        //console.log(this.state.data)
        if(document.getElementsByName('email').length == 0) {
            this.set_value()
        }
        return(
            <Modal  className="bg-light modal-dialog modal-sm" ariaHideApp={false} isOpen={this.props.modalOpen} onRequestClose={()=>this.props.closeModal()}>
                <div>
                    <div className="modal-content bg-light signupModal">
                    <div className="modal-header">
                        <h4 className="modal-title">회원 정보 수정</h4>
                        <span className="close" onClick={()=>this.props.closeModal()}>
                        &times;
                        </span>
                        </div>
                        <div className="modal-body" style={{'max-height': 'calc(100vh - 210px)', 'overflow-y': 'auto'}}>
                            <div className="row my-3">         
                                <label className="col-sm-5">아이디</label>
                                <input name="id" className="form-control mod_input input_id mx-3" type="text" placeholder={this.props.data['id']} readOnly/>    
                            </div>
                            <div className="row my-3">
                                <label className="col-sm-5">비밀번호</label>
                                <input name="password" className="form-control mx-3 mod_input input_password" type="password" onChange={this.input_handler}/>   
                            </div>
                            <div className="row my-3">   
                                <label className="col-sm-5">이름</label>
                                <input name="name" className="form-control mx-3 mod_input input_name" type="text" onChange={this.input_handler}/>
                            </div> 
                            <div className="row my-3">
                                <label className="col-sm-5">닉네임</label>
                                <input name="nickname" className="form-control mx-3  mod_input input_nickname" type="text" onChange={this.input_handler}/>
                            </div>
                            <div className="row my-3">
                                <label className="col-sm-5">전공</label>
                                <select labelId="simple-select-label" name="major" onChange={this.input_handler}>
                                    <option value={"컴퓨터과학부"}>컴퓨터과학부</option>
                                    <option value={"경영학부"}>경영학부</option>
                                    <option value={"화학공학과"}>화학공학과</option>
                                    <option value={"철학과"}>철학과</option>
                                </select>
                            </div>
                            <div className="row my-3">
                                <label className="col-sm-5">이메일</label>
                                <input name="email" className="form-control mx-3  mod_input input_email" type="email" onChange={this.input_handler}/>
                            </div>
                        </div>

                        <div className="modal-footer">
                            <button className="btn btn-secondary" onClick={()=>this.edit_handler()}>수정 완료</button>
                        </div>
                    </div>
                </div>
            </Modal>
        )
        
    }
}

export default EditInfo