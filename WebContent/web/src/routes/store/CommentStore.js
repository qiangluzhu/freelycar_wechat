import React from 'react';
import { Flex } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'
import CommentStar from '../../components/CommentStar'
import { orderComment } from '../../services/orders'

class CommentStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            nowStar: 0,
            comment: ''
        }
    }

    setScore = (number) => {
        this.setState({
            nowStar: number
        })
    }


    comment() {
        orderComment({
            consumOrderId: this.props.params.consumerOrderId,
            comment: this.state.comment,
            stars: this.state.nowStar
        })
    }
    render() {
        return <div className="body-bac">
            <NavBar title="合作门店" />
            <div className="comment-store">
                <Flex className="comment-store-title">
                    <div className="comment-store-avatar"><img src={window.localStorage.getItem('imgUrl')} alt="" /></div>
                    <span className="title">{window.localStorage.getItem('storeName')}</span>
                </Flex>
                <Flex className="comment" justify="center">
                    <CommentStar setScore={this.setScore} nowStar={this.state.nowStar} />
                </Flex>
                <textarea placeholder="亲，说出你的心里话，分享给大家吧(至少输入8个字)" className="comment-text" onChange={(e) => { this.setState({ comment: e.target.value }) }}>
                </textarea>
            </div>
            <div className="comment-commit-btn" onClick={() => { this.comment() }}>提交评价</div>

        </div>
    }

}
export default CommentStore