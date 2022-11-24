import { Bar} from "react-chartjs-2";
import { registerables, Chart } from 'chart.js';
import React from "react";
import { Link } from 'react-router-dom'
import "../../../../styles/ContentCreator/AnalyticsPage.css";
import loading from "../../../../images/loading.gif";

function AnalyticsPerVersion({ applications }) {
    Chart.register(...registerables)
    return (
        <div>
            {applications.length !== 0 ?
                applications.map(app =>
                    <>
                        <div className={"app-downloads"}>
                            <Link to={`/creator/1/myApps/${app?.name}`}>
                                <img src={app?.icon} />
                            </Link>
                            <p>{app?.name}</p>
                            <div className={"downloads-graph"}>
                                <h3 className={"graph-title"}>DOWNLOADS</h3>
                                <div className={"graph-numbers"}>
                                    <h4 className={"number"}>{app?.totalDownloads}</h4>
                                    <h5 className={"raise"}>+7%</h5>
                                </div>
                                <div className={"graph"}>
                                    <Bar data={{
                                        labels: app?.versions.map((data) => data.number),
                                        datasets: [
                                            {
                                                backgroundColor: 'rgb(130, 192, 250)',
                                                label: "DOWNLOADS",
                                                data: app?.versions.map((data1) => data1.totalDownloads),
                                            }]
                                    }}
                                          options={{
                                              maintainAspectRatio: true,
                                              tension: 0,
                                          }}
                                    />
                                </div>
                            </div>
                            <div className={"downloads-graph"}>
                                <h3 className={"graph-title"}>REVIEWS</h3>
                                <div className={"graph-numbers"}>
                                    <h4 className={"number"}>{app?.totalReviews}</h4>
                                    <h5 className={"raise"}>+7%</h5>
                                </div>
                                <div className={"graph"}>
                                    <Bar data={{
                                        labels: app?.versions.map((data) => data.number),
                                        datasets: [
                                            {
                                                backgroundColor: 'rgb(130, 192, 250)',
                                                label: "REVIEWS",
                                                data: app?.versions.map((data1) => data1.totalReviews),
                                            }]
                                    }}
                                          options={{
                                              maintainAspectRatio: true,
                                              tension: 0,
                                          }}
                                    />
                                </div>
                            </div>
                        </div>
                        <hr />
                    </>
                ) : (
                    <img className={"loading-apps"} src={loading}/>
                )
            }
        </div>
    )
}
export default AnalyticsPerVersion;