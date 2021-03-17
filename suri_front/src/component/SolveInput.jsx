import React from 'react'

class SolveInput extends React.Component{
    render(){
        return(
            <div className="solve_element my-3">
                <div>답안 입력</div>
                <div className="value my-2">
                    <textarea 
                        rows="5" 
                        className="form-control mod_input" 
                        type="text" 
                        value={this.props.solveInput}
                        onChange={e=>this.props.setSolveInput(e.target.value)}
                    />    
                </div> 
            </div>
        )
    }
}

export default SolveInput