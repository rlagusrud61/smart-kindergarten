import "./Groups.scss"
import {SigmaContainer, useSigma} from "react-sigma-v2";
import "react-sigma-v2/lib/react-sigma-v2.css";
import getNodeProgramImage from "sigma/rendering/webgl/programs/node.image";
import {NodeDisplayData, PartialButFor, PlainObject} from "sigma/types";
import {Settings} from "sigma/settings";
import {useEffect} from "react";
import SpringSupervisor from "./layout-spring";

const MyCustomGraph = () => {
    const sigma = useSigma();
    const graph = sigma.getGraph();


    useEffect(() => {

        graph.addNode("A", {label: "Someone", color: "#FF0", size: 40, image: "img/kidlmao.jpg"});
        graph.addNode("B", {label: "Michiel", color: "#00F", size: 40, image: "img/michiel.png"});
        graph.addNode("C", {label: "C", color: "#00F", size: 40, image: "img/kidlmao.jpg"});
        graph.addNode("D", {label: "D", color: "#00F", size: 40, image: "img/kidlmao.jpg"});
        graph.addEdge("A", "B", {color: "#999", size: 5});

        graph.nodes().forEach((node, i) => {
            const angle = (i * 2 * Math.PI) / graph.order;
            graph.setNodeAttribute(node, "x", 100 * Math.cos(angle));
            graph.setNodeAttribute(node, "y", 100 * Math.sin(angle));
        });

        // Create the spring layout and start it
        const layout = new SpringSupervisor(graph, { isNodeFixed: (n) => graph.getNodeAttribute(n, "highlighted") });
        layout.start();

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

        // sigma.on("clickStage", ({event}: { event: { x: number; y: number } }) => {
        //     // Sigma (ie. graph) and screen (viewport) coordinates are not the same.
        //     // So we need to translate the screen x & y coordinates to the graph one by calling the sigma helper `viewportToGraph`
        //     const coordForGraph = sigma.viewportToGraph({x: event.x, y: event.y});
        //
        //     // We create a new node
        //     const node = {
        //         ...coordForGraph,
        //         size: 10,
        //         color: "blue",
        //     };
        //
        //     // Searching the two closest nodes to auto-create an edge to it
        //     const closestNodes = graph
        //         .nodes()
        //         .map((nodeId) => {
        //             const attrs = graph.getNodeAttributes(nodeId);
        //             const distance = Math.pow(node.x - attrs.x, 2) + Math.pow(node.y - attrs.y, 2);
        //             return {nodeId, distance};
        //         })
        //         .sort((a, b) => a.distance - b.distance)
        //         .slice(0, 2);
        //
        //     // We register the new node into graphology instance
        //     // @ts-ignore
        //     const id = crypto.randomUUID();
        //     graph.addNode(id, node);
        //
        //     // We create the edges
        //     closestNodes.forEach((e) => graph.addEdge(id, e.nodeId));
        // });

        return () => {
            sigma.kill()
        }
    }, []);

    return null;
};

/**
 * Custom label renderer
 */
export default function drawLabel(
    context: CanvasRenderingContext2D,
    data: PartialButFor<NodeDisplayData, "x" | "y" | "size" | "label" | "color">,
    settings: Settings,
): void {
    if (!data.label) return;

    const size = settings.labelSize,
        font = settings.labelFont,
        weight = settings.labelWeight;

    context.font = `${weight} ${size}px ${font}`;
    const width = context.measureText(data.label).width + 8;

    context.fillStyle = "#ffffffcc";
    context.fillRect(data.x + data.size, data.y + size / 3 - 15, width, 20);

    context.fillStyle = "#000";
    context.fillText(data.label, data.x + data.size + 3, data.y + size / 3);
}


const TEXT_COLOR = "#000000";

/**
 * This function draw in the input canvas 2D context a rectangle.
 * It only deals with tracing the path, and does not fill or stroke.
 */
export function drawRoundRect(
    ctx: CanvasRenderingContext2D,
    x: number,
    y: number,
    width: number,
    height: number,
    radius: number,
): void {
    ctx.beginPath();
    ctx.moveTo(x + radius, y);
    ctx.lineTo(x + width - radius, y);
    ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
    ctx.lineTo(x + width, y + height - radius);
    ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
    ctx.lineTo(x + radius, y + height);
    ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
    ctx.lineTo(x, y + radius);
    ctx.quadraticCurveTo(x, y, x + radius, y);
    ctx.closePath();
}

/**
 * Custom hover renderer
 */
export function drawHover(context: CanvasRenderingContext2D, data: PlainObject, settings: PlainObject) {
    const size = settings.labelSize;
    const font = settings.labelFont;
    const weight = settings.labelWeight;
    const subLabelSize = size - 2;

    const label = data.label;
    const subLabel = data.tag !== "unknown" ? data.tag : "";
    const clusterLabel = data.clusterLabel;

    // Then we draw the label background
    context.beginPath();
    context.fillStyle = "#fff";
    context.shadowOffsetX = 0;
    context.shadowOffsetY = 2;
    context.shadowBlur = 8;
    context.shadowColor = "#000";

    context.font = `${weight} ${size}px ${font}`;
    const labelWidth = context.measureText(label).width;
    context.font = `${weight} ${subLabelSize}px ${font}`;
    const subLabelWidth = subLabel ? context.measureText(subLabel).width : 0;
    context.font = `${weight} ${subLabelSize}px ${font}`;
    const clusterLabelWidth = clusterLabel ? context.measureText(clusterLabel).width : 0;

    const textWidth = Math.max(labelWidth, subLabelWidth, clusterLabelWidth);

    const x = Math.round(data.x);
    const y = Math.round(data.y);
    const w = Math.round(textWidth + size / 2 + data.size + 3);
    const hLabel = Math.round(size / 2 + 4);
    const hSubLabel = subLabel ? Math.round(subLabelSize / 2 + 9) : 0;
    const hClusterLabel = Math.round(subLabelSize / 2 + 9);

    drawRoundRect(context, x, y - hSubLabel - 12, w, hClusterLabel + hLabel + hSubLabel + 12, 5);
    context.closePath();
    context.fill();

    context.shadowOffsetX = 0;
    context.shadowOffsetY = 0;
    context.shadowBlur = 0;

    // And finally we draw the labels
    context.fillStyle = TEXT_COLOR;
    context.font = `${weight} ${size}px ${font}`;
    context.fillText(label, data.x + data.size + 3, data.y + size / 3);

    if (subLabel) {
        context.fillStyle = TEXT_COLOR;
        context.font = `${weight} ${subLabelSize}px ${font}`;
        context.fillText(subLabel, data.x + data.size + 3, data.y - (2 * size) / 3 - 2);
    }

    context.fillStyle = data.color;
    context.font = `${weight} ${subLabelSize}px ${font}`;
    context.fillText(clusterLabel, data.x + data.size + 3, data.y + size / 3 + 3 + subLabelSize);
}

export function Groups() {

    return (
        <main role="main" className="proximity-container">
            <SigmaContainer graphOptions={{type: "directed"}}
                            initialSettings={{
                                nodeProgramClasses: {image: getNodeProgramImage()},
                                labelRenderer: drawLabel,
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

