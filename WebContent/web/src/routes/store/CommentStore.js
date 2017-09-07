import React from 'react';
import { Flex } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'
import CommentStar from '../../components/CommentStar'
class CommentStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            nowStar: 0
        }
    }

    setScore = (number) => {
        this.setState({
            nowStar: number
        })
    }
    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        return <div className="body-bac">
            <NavBar title="合作门店" />
            <div className="comment-store">
                <Flex className="comment-store-title">
                    <div className="comment-store-avatar"><img src="" alt="" /></div>
                    <span className="title">小易爱车</span>
                </Flex>
                <Flex className="comment" justify="center">
                    <CommentStar setScore={this.setScore} nowStar={this.state.nowStar} />
                </Flex>
                <textarea placeholder="亲，说出你的心里话，分享给大家吧(至少输入8个字)" className="comment-text">
                </textarea>
            </div>
            <div className="comment-commit-btn">提交评价</div>

        </div>
    }

}
export default CommentStore