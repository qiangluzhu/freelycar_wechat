import React from 'react';
import { Flex } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'

class CooperativeStore extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false
        }
    }

    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        return <div>
            <NavBar title="合作门店" />
            <div>
                <Flex className="cooperative-store-list">
                    <Flex className="picture">
                        <img src="" alt="" />
                    </Flex>
                    <Flex direction="column" align="start" justify="between" style={{ height: '1.6rem', width: '5.24rem' }}>
                        <div className="store-name">
                            小易爱车   <span style={{ fontSize: '.18rem', color: '#e42f2f', marginLeft: '.14rem' }}>5.0分</span>
                        </div>
                        <Flex className="address" style={{ width: "100%" }}>
                            <div className="address-icon"></div>
                            <p className="info-font">南京市苏宁诺富特酒店B2</p>
                        </Flex>
                        <Flex className="time" align="end" style={{ width: "100%" }}>
                            <div>
                                <Flex className="info-font">
                                    <div className="time-icon"></div>
                                    营业时间：8:20-20:00
                            </Flex>
                                <div className="info-identify">
                                    <span className="identification">免费安全监测</span>
                                    <span className="identification">下雨保</span>
                                </div>
                            </div>
                        </Flex>
                    </Flex>
                </Flex>

                <Flex className="cooperative-store-list">
                    <Flex className="picture">
                        <img src="" alt="" />
                    </Flex>
                    <Flex direction="column" align="start" justify="between" style={{ height: '1.6rem', width: '5.24rem' }}>
                        <div className="store-name">
                            小易爱车   <span style={{ fontSize: '.18rem', color: '#e42f2f', marginLeft: '.14rem' }}>5.0分</span>
                        </div>
                        <Flex className="address" style={{ width: "100%" }}>
                            <div className="address-icon"></div>
                            <p className="info-font">南京市苏宁诺富特酒店B2</p>
                        </Flex>
                        <Flex className="time" align="end" style={{ width: "100%" }}>
                            <div>
                                <Flex className="info-font">
                                    <div className="time-icon"></div>
                                    营业时间：8:20-20:00
                                </Flex>
                                <div className="info-identify">
                                    <span className="identification">免费安全监测</span>
                                    <span className="identification">下雨保</span>
                                </div>
                            </div>
                        </Flex>
                    </Flex>
                </Flex>
            </div>
        </div>
    }

}
export default CooperativeStore