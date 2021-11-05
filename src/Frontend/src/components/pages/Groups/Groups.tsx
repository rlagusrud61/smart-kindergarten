import "./Groups.scss"
import {SigmaContainer, useSigma} from "react-sigma-v2";
import "react-sigma-v2/lib/react-sigma-v2.css";
import getNodeProgramImage from "sigma/rendering/webgl/programs/node.image";
import {Attributes, useEffect, useState} from "react";
import SpringSupervisor from "./layout-spring";
import {BluetoothClient, StudentsClient} from "../../../clients/rest/ApiClient";
import {ImagePathForStudent} from "../../Student/StudentProfile";
import {useHub} from "../../../Hooks/HubHooks";
import {ProximityHub} from "../../../clients/signalr/ProximityHub";
import Graph from "graphology";


const getEdgeKey = (studentId: string, nearbyStudent: string) => `${studentId}---${nearbyStudent}`

const MyCustomGraph = () => {
    const sigma = useSigma();
    const [epicGraph, setEpicGraph] = useState<Graph>();
    // const epicGraph = sigma.getGraph()

    const hub = useHub(ProximityHub, []);

    const updateStudentProximities = (studentId: string, nearbyStudents: string[]) => {
        // We're assuming, for now, that all student nodes already exist

        // Add missing edges
        nearbyStudents.forEach(nearbyStudent => {
            // graph.dropEdge(studentId, nearbyStudent)
            if (!epicGraph?.hasDirectedEdge(studentId, nearbyStudent)) {
                epicGraph?.addDirectedEdgeWithKey(getEdgeKey(studentId, nearbyStudent), studentId, nearbyStudent, {color: "#999", size: 5});
            }
        })

        // Delete old invalid edges
        const node = getStudentNode(studentId);
        if (!node) throw new Error("Invalid node");
        const validKeys = nearbyStudents.map(nearbyStudent => getEdgeKey(studentId, nearbyStudent));
        epicGraph?.directedEdges(node).forEach(dedge => {
            if (!validKeys.includes(dedge)) {
                // This edge is no longer valid, drop it
                epicGraph.dropEdge(dedge);
                // epicGraph.directedEdges().splice(epicGraph.directedEdges().indexOf(dedge),1);
                // epicGraph.filterDirectedEdges(node, e => e !== dedge)
                // epicGraph.updateDirectedEdgeWithKey(dedge, () => {})
                // epicGraph.updateNode(node)

                try{
                    epicGraph.updateDirectedEdge(dedge, (attributes: any) => {
                        return {
                            ...attributes,
                            color: "white" // Cause this library is the worst goddamn shit ever
                        }
                    })
                }catch (e){
                    //dfsjkafsd ;jklafsd
                }


                // console.log(dedge.split('---')[1])
                // epicGraph.updateDirectedEdge(studentId, dedge.split('---')[1], (attributes: any) => {
                //     return {
                //         ...attributes,
                //         color: "white" // Cause this library is the worst goddamn shit ever
                //     }
                // })
            }
        })
    }

    const getStudentNode = (studentId: string) => {
        const node = epicGraph?.findNode(node => node === studentId);
        if (!node) throw new Error("Student node not found");
        return node;
    }

    useEffect(() => {

        const onUpdate = (studentId: string, nearbyStudents: string[]) => {
            updateStudentProximities(studentId, nearbyStudents);
        };

        hub?.onProximityUpdate.addHandler(onUpdate)

        return () => {
            hub?.onProximityUpdate.removeHandler(onUpdate)
        }
    }, [hub])

    useEffect(() => {

        // if (!graph) return;
        const graph = epicGraph ?? sigma.getGraph();
        !epicGraph && setEpicGraph(graph)
        if (!graph) throw new Error("idc")

        // State for drag'n'drop
        let draggedNode: string | null = null;
        let isDragging = false;

        // On mouse down on a node
        //  - we enable the drag mode
        //  - save in the dragged node in the state
        //  - highlight the node
        //  - disable the camera so its state is not updated
        sigma.on("downNode", (e) => {
            isDragging = true;
            draggedNode = e.node;
            graph.setNodeAttribute(draggedNode, "highlighted", true);
            sigma.getCamera().disable();
        });

        // On mouse move, if the drag mode is enabled, we change the position of the draggedNode
        sigma.getMouseCaptor().on("mousemove", (e) => {
            if (!isDragging || !draggedNode) return;

            // Get new position of node
            const pos = sigma.viewportToGraph(e);

            graph.setNodeAttribute(draggedNode, "x", pos.x);
            graph.setNodeAttribute(draggedNode, "y", pos.y);
        });

        // On mouse up, we reset the autoscale and the dragging mode
        sigma.getMouseCaptor().on("mouseup", () => {
            if (draggedNode) {
                graph.removeNodeAttribute(draggedNode, "highlighted");
            }
            isDragging = false;
            draggedNode = null;
            sigma.getCamera().enable();
        });

        // Disable the autoscale at the first down interaction
        sigma.getMouseCaptor().on("mousedown", () => {
            if (!sigma.getCustomBBox()) sigma.setCustomBBox(sigma.getBBox());
        });

        return () => {
            sigma.kill()
        }
    }, [sigma]);

    useEffect(() => {
        if (!epicGraph) return;
        new StudentsClient().students().then(students => {
            students.forEach(student => {
                if (epicGraph.hasNode(student.id)) return;
                epicGraph.addNode(student.id, {
                    label: student.name,
                    color: "#FF0",
                    size: 40,
                    image: ImagePathForStudent(student)
                })
            })

            // Assign x/y positions
            epicGraph.nodes().forEach((node, i) => {
                const angle = (i * 2 * Math.PI) / epicGraph.order;
                epicGraph.setNodeAttribute(node, "x", 100 * Math.cos(angle));
                epicGraph.setNodeAttribute(node, "y", 100 * Math.sin(angle));
            });

            // Add edges
            new BluetoothClient().allProximities().then(proximity => {
                proximity.forEach(pr => {
                    if (!pr.key || !pr.value) return;
                    updateStudentProximities(pr.key, pr.value)
                })
            })

        })

        // graph.addNode("A", {label: "Someone", color: "#FF0", size: 40, image: "img/kidlmao.jpg"});
        // graph.addNode("B", {label: "Michiel", color: "#00F", size: 40, image: "img/michiel.png"});
        // graph.addNode("C", {label: "C", color: "#00F", size: 40, image: "img/kidlmao.jpg"});
        // graph.addNode("D", {label: "D", color: "#00F", size: 40, image: "img/kidlmao.jpg"});
        // graph.addEdge("A", "B", {color: "#999", size: 5});

        // Create the spring layout and start it
        const layout = new SpringSupervisor(epicGraph, {isNodeFixed: (n) => epicGraph.getNodeAttribute(n, "highlighted")});
        layout.start();

        return () => {
            layout.kill();
        }
    }, [epicGraph])


    return null;
};


export function Groups() {

    return (
        <main role="main" className="proximity-container">
            <SigmaContainer graphOptions={{type: "directed"}}
                            initialSettings={{
                                nodeProgramClasses: {image: getNodeProgramImage()},
                                // labelRenderer: drawLabel,
                                defaultNodeType: "image",
                                defaultEdgeType: "arrow",
                                labelDensity: 0.8,
                                labelGridCellSize: 20,
                                stagePadding: 100,
                                // hoverRenderer: drawHover,
                                // labelRenderedSizeThreshold: 15,
                                labelFont: "Nunito, sans-serif",
                                labelWeight: "600",
                                labelSize: 25,
                                zIndex: true,
                            }}
                            className="react-sigma">
                <MyCustomGraph/>
            </SigmaContainer>
        </main>
    );
}

function uuid() {
    throw new Error("Function not implemented.");
}

