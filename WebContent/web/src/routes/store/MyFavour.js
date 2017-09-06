import React from 'react';
import NavBar from '../../components/NavBar'
import { Flex } from 'antd-mobile'
import './CooperativeStore.less'
import { myPoints } from '../../services/user'
class MyFavour extends React.Component {
    constructor(props) {
        super(props)
        this.state = {

        }
    }

    componentDidMount() {

    }
    render() {
        return <div className='store-detail my-favour'>
            <NavBar title="我的优惠" />
            <Flex className="coupon" direction="column" align="start">
                <Flex style={{ height: '1.16rem', background: '#fff', width: '100%' }}>
                    <Flex className="money" direction="column">
                        <div style={{ fontSize: '.4rem' }}><span style={{ fontSize: '.18rem' }}>￥</span>25</div>
                        <div style={{ color: '#8c8c8c', fontSize: '.22rem'}}>代金券</div>
                    </Flex>
                    <div className="parting-line"></div>
                    <Flex style={{ flex: 'auto' }}>
                        <Flex direction="column" align="start">
                            <div style={{ fontSize: '.3rem',lineHeight:'.45rem', marginLeft: '.2rem' ,marginBottom:'.05rem'}}>洗车抵用券</div>
                            <div style={{ fontSize: '.2rem', lineHeight: '.25rem', marginLeft: '.2rem' }}>洗车项目时可直接抵扣</div>
                        </Flex>
                        <div className="use-button" style={{width:'1rem',height:'.32rem',border:'1px  solid #ee5e5e',fontSize:'.22rem',lineHeight:'.32rem',borderRadius:'.05rem',color:'#ee5e5e',textAlign:'center'}}>
                            立即使用 
                    </div>
                    </Flex>
                </Flex>
                <div className="coupon-info">
                    <span className="phone">限客户手机号：13951775978</span>
                    <span className="time">截止日期：2018.11.11</span>
                </div>
            </Flex>
        </div>
    }
}
export default MyFavour