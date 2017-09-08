import React from 'react';
import { Flex, ListView } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'
import { storeList } from '../../services/store'
const NUM_ROWS = 100;
let pageIndex = 0;
let index = 20;
class CooperativeStore extends React.Component {

    constructor(props) {
        super(props)
        const getSectionData = (dataBlob, sectionID) => dataBlob[sectionID];
        const getRowData = (dataBlob, sectionID, rowID) => dataBlob[rowID];
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        });

        this.state = {
            dataSource: dataSource.cloneWithRows({}),
            isLoading: true,
            hasMore: true,
            pageIndex: 1,
            data: []
        }
    }

    componentDidMount() {
        setTimeout(() => {
            this.rData = this.genData();
            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(this.rData),
                isLoading: false,
            });
        }, 600);
    }

    genData = (pIndex = 0) => {
        const dataBlob = {};
        for (let i = 0; i < NUM_ROWS; i++) {
            const ii = (pIndex * NUM_ROWS) + i;
            dataBlob[`${ii}`] = `row - ${ii}`;
        }
        return dataBlob;

        // storeList({
        //     page: 1,
        //     number: 10
        // }).then((res) => {
        //     console.log(res)
        // })

        // return ['11', '22','33','44','55']
    }

    onEndReached = (event) => {
        console.log('到底部啦')
        // load new data
        // hasMore: from backend data, indicates whether it is the last page, here is false
        if (this.state.isLoading && !this.state.hasMore) {
            return;
        }
        console.log('reach end', event);
        this.setState({ isLoading: true });
        setTimeout(() => {
            storeList({
                page: 1,
                number: 10
            }).then((res) => {
                console.log(res)
                this.rData = { ...this.rData, ...this.genData(++pageIndex) };
                this.setState({
                    pageIndex: this.state.pageIndex + 1,
                    dataSource: this.state.dataSource.cloneWithRows(this.rData),
                    isLoading: false,
                    data: ['1', '2']
                })
            })
        }, 1000);
    }

    render() {
        const row = (rowData, sectionID, rowID) => {
            if (index < 0) {
                index = this.state.data.length - 1;
            }
            const obj = this.state.data[index--];
            return (
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
            );
        };


        return <div className="body-bac">
            <NavBar title="合作门店" />
            <ListView
                dataSource={this.state.dataSource}
                renderBodyComponent={() => <MyBody />}
                onEndReached={() => this.onEndReached()}
                onEndReachedThreshold={10}
                renderRow={row}
                initialListSize={1}
                pageSize={4}
                onScroll={() => { console.log('scroll'); }}
                scrollRenderAheadDistance={500}
                scrollEventThrottle={200}
                renderFooter={() => (<div style={{ padding: '.2rem', textAlign: 'center' }}>
                    {this.state.isLoading ? 'Loading...' : 'Loaded'}
                </div>)}
                style={{
                    height:`${document.documentElement.clientHeight}`,
                    overflow: 'auto',
                }}
            >

            </ListView>
        </div>
    }

}


const MyBody = (props) => {
    return (
        <div >{props.children}</div>
    );
}
export default CooperativeStore