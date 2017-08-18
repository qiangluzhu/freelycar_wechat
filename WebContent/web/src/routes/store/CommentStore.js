import React from 'react';
import { Flex } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'
import CommentStar from '../../components/CommentStar'
class CommentStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            nowStar:0
        }
    }

    setScore=(number)=>{
        this.setState({
            nowStar:number
        })
    }
    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        return <div>
            <NavBar title="合作门店" />
            <div className="comment-store">

            </div>
            <CommentStar setScore={this.setScore} nowStar={this.state.nowStar}/>
        </div>
    }

}
export default CommentStore