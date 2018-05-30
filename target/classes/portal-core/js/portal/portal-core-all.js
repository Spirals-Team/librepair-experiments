/**
 * Panel extension for graphing points with a 1D attribute in 3D space
 *
 * 3D display is dependent on Three JS. Point scaling requires D3
 */
Ext.define('portal.charts.3DScatterPlot', {
    extend: 'Ext.panel.Panel',

    alias: 'widget.threedscatterplot',

    data : null,
    d3 : null, //D3 elements (graphs, lines etc).
    threeJs : null, //Three JS elements
    innerId: null, //Internal ID for rendering three js
    useCanvasRenderer: false, 


    /**
     * Adds the following config
     * {
     *   data - Object[] - Optional - Data to intially plot in this widget. No data will be plotted if this is missing.
     *   pointSize - Number - Optional - Size of the data points in pixels (Unscaled each axis is 50 pixels wide) - Default - 10
     *   allowSelection - Booelan - Optional - True if points can be selected by clicking with the mouse. Default - false
     *
     *   xAttr - String - Optional - The name of the attribute to plot on the x axis (default - 'x')
     *   xLabel - String - Optional - The name of the x axis to show on the plot (default - 'X')
     *   xDomain - Number[] - Optional - The fixed range of values [min, max] to plot on the x axis. Defaults to data extents
     *   xHideValueLabel - Boolean - Optional - If set, hide the numerical min/max values displayed on the x axis (default - false)
     *
     *   yAttr - String - Optional - The name of the attribute to plot on the y axis (default - 'y')
     *   yLabel - String - Optional - The name of the y axis to show on the plot (default - 'Y')
     *   yDomain - Number[] - Optional - The fixed range of values [min, max] to plot on the y axis. Defaults to data extents
     *   yHideValueLabel - Boolean - Optional - If set, hide the numerical min/max values displayed on the y axis (default - false)
     *
     *   zAttr - String - Optional - The name of the attribute to plot on the z axis (default - 'z')
     *   zLabel - String - Optional - The name of the z axis to show on the plot (default - 'Z')
     *   zDomain - Number[] - Optional - The fixed range of values [min, max] to plot on the z axis. Defaults to data extents
     *   zHideValueLabel - Boolean - Optional - If set, hide the numerical min/max values displayed on the z axis (default - false)
     *
     *   valueAttr - String - Optional - The name of the attribute that controls the color value (default - 'value')
     *   valueLabel - String - Optional - The label of the attribute that controls the color value (default - 'Value')
     *   valueDomain - Number[] - Optional - The fixed range of values [min, max] to control color scale. Defaults to data extents
     *   valueScale - String - Optional - (not compatible with valueRenderer) How will the color scale be defined for the default rainbow plot - choose from ['linear', 'log'] (default - 'linear')
     *   valueRenderer - function(value) - Optional - (not compatible with valueScale) Given a value, return a 16 bit integer representing an RGB value in the form 0xffffff
     * }
     *
     * Adds the following events
     * {
     *  select : function(this, dataItem) - Fired when a scatter point is clicked
     *  deselect : function(this) - Fired when a scatter point deselected
     * }
     *
     */
    constructor : function(config) {
        this.d3 = null;
        this.threeJs = null;
        this.innerId = Ext.id();
        this.data = config.data ? config.data : null;
        this.pointSize = config.pointSize ? config.pointSize : 10;
        this.allowSelection = config.allowSelection ? true : false;

        this.xAttr = config.xAttr ? config.xAttr : 'x';
        this.xLabel = config.xLabel ? config.xLabel : 'X';
        this.xDomain = config.xDomain ? config.xDomain : null;
        this.xHideValueLabel = config.xHideValueLabel ? true : false;
        this.yAttr = config.yAttr ? config.yAttr : 'y';
        this.yLabel = config.yLabel ? config.yLabel : 'Y';
        this.yDomain = config.yDomain ? config.yDomain : null;
        this.yHideValueLabel = config.xHideValueLabel ? true : false;
        this.zAttr = config.zAttr ? config.zAttr : 'z';
        this.zLabel = config.zLabel ? config.zLabel : 'Z';
        this.zDomain = config.zDomain ? config.zDomain : null;
        this.zHideValueLabel = config.xHideValueLabel ? true : false;
        this.valueAttr = config.valueAttr ? config.valueAttr : 'value';
        this.valueLabel = config.valueLabel ? config.valueLabel : 'Value';
        this.valueDomain = config.valueDomain ? config.valueDomain : null;
        this.valueScale = config.valueScale ? config.valueScale : 'linear';
        this.valueRenderer = config.valueRenderer ? config.valueRenderer : null;

        Ext.apply(config, {
            html : Ext.util.Format.format('<div id="{0}" style="width:100%;height:100%;"></div>', this.innerId)
        });

        this.callParent(arguments);

        //Lower IE version require a different renderer entirely
        //Force the loading of these new dependencies if we need to...
        if (Ext.isIE10m) {
            this.useCanvasRenderer = true;
            this.loadingDeps = false;
            this.loadingDepsCallback = null;
            
            if (!THREE.CanvasRenderer) {
                this.loadingDeps = true;
                Ext.Loader.loadScript({
                    url:'portal-core/js/threejs/renderers/CanvasRenderer.js',
                    scope: this,
                    onLoad:function() {
                        Ext.Loader.loadScript({
                            url:'portal-core/js/threejs/renderers/Projector.js',
                            scope: this,
                            onLoad:function() {
                                this.loadingDeps = false;
                                if (Ext.isFunction(this.loadingDepsCallback)) {
                                    this.loadingDepsCallback();
                                } else {
                                }
                            }
                        });
                    } 
                });
            }
        }

        this.on('render', this._afterRender, this);
        this.on('resize', this._onResize, this);
    },

    /**
     * Initialise three JS elements.
     */
    _afterRender : function() {
        if (this.loadingDeps) {
            this.loadingDepsCallback = this._afterRender;
            return;
        }
        
        this.threeJs = {
            camera : null,
            controls : null,
            scene : null,
            renderer : null,
            width : null,
            height : null
        };

        this.threeJs.scene = new THREE.Scene();

        var el = this.getEl();
        this.threeJs.width = el.getWidth();
        this.threeJs.height = el.getHeight();

        if (this.allowSelection) {
            el.on('mousedown', this._handleMouseDown, this);
            el.on('mouseup', this._handleMouseUp, this);

            this.threeJs.raycaster = new THREE.Raycaster();
            this.threeJs.raycaster.params.PointCloud.threshold = this.pointSize / 3;
        }

        var container = document.getElementById(this.innerId);
        
        this.threeJs.camera = new THREE.PerspectiveCamera(60, this.threeJs.width / this.threeJs.height, 1, 10000);
        this.threeJs.camera.position.z = 180;
        this.threeJs.camera.position.y = 18;
        this.threeJs.scene.add(this.threeJs.camera);

        this.threeJs.controls = new THREE.OrbitControls( this.threeJs.camera, container);
        this.threeJs.controls.damping = 0.2;
        this.threeJs.controls.target = new THREE.Vector3(0, 0, 0);
        this.threeJs.controls.addEventListener('change', Ext.bind(this._renderThreeJs, this));

        // renderer
        if (Ext.isIE10m) { 
            //IE 10 and lower don't have WebGL support
            this.threeJs.renderer = new THREE.CanvasRenderer({antialias : false});
        } else {
            this.threeJs.renderer = new THREE.WebGLRenderer({antialias : false});
        }
        this.threeJs.renderer.setClearColor(0xffffff, 1);
        this.threeJs.renderer.setSize(this.threeJs.width, this.threeJs.height);

        container.appendChild(this.threeJs.renderer.domElement);

        // Need a perpetual animation loop for updating the user controls
        var me = this;
        var animate = function() {
            requestAnimationFrame(animate);
            me.threeJs.controls.update();
        };
        animate();

        if (this.data) {
            this.plot(this.data);
        }

        this._renderThreeJs();
    },

    /**
     * Renders the current state of the three JS camera/scene
     */
    _renderThreeJs : function() {
        this.threeJs.renderer.render(this.threeJs.scene, this.threeJs.camera);
    },

    /**
     * Update camera aspect ratio and renderer size
     */
    _onResize : function(me, width, height) {
        if (!this.threeJs) {
            return;
        }

        var el = this.getEl();
        this.threeJs.width = el.getWidth();
        this.threeJs.height = el.getHeight();
        this.threeJs.camera.aspect = this.threeJs.width / this.threeJs.height;
        this.threeJs.camera.updateProjectionMatrix();

        this.threeJs.renderer.setSize(this.threeJs.width, this.threeJs.height);

        this._renderThreeJs();
    },

    /**
     * Utility for turning a click event on dom element target
     * into an X/Y offset relative to that element
     *
     * From:
     * http://stackoverflow.com/questions/55677/how-do-i-get-the-coordinates-of-a-mouse-click-on-a-canvas-element
     */
    _relMouseCoords : function(event, target) {
        var totalOffsetX = 0;
        var totalOffsetY = 0;
        var canvasX = 0;
        var canvasY = 0;
        var currentElement = target;

        do {
            totalOffsetX += currentElement.offsetLeft - currentElement.scrollLeft;
            totalOffsetY += currentElement.offsetTop - currentElement.scrollTop;
        } while (currentElement = currentElement.offsetParent)

        canvasX = event.pageX - totalOffsetX;
        canvasY = event.pageY - totalOffsetY;

        return {
            x : canvasX,
            y : canvasY
        };
    },

    _handleMouseDown : function(e, t) {
        this._md = this._relMouseCoords(e.browserEvent, t);
    },

    _handleMouseUp : function(e, t) {
        var xy = this._relMouseCoords(e.browserEvent, t);
        var rawX = xy.x;
        var rawY = xy.y;

        // If the mouse has moved too far, dont count this as a click
        if (Math.abs(this._md.x - rawX) + Math.abs(this._md.y - rawY) > 10) {
            return;
        }

        // The X/Y needs to be scale independent
        var x = ( rawX / this.threeJs.width ) * 2 - 1;
        var y = - ( rawY / this.threeJs.height ) * 2 + 1;

        // Otherwise cast a ray and see what we intersect
        var mouse3D = new THREE.Vector3(x, y, 0.5).unproject(this.threeJs.camera);
        var direction = mouse3D.clone()
            .sub(this.threeJs.camera.position)
            .normalize();

        this.threeJs.raycaster.ray.set(this.threeJs.camera.position, direction);
        var intersections = this.threeJs.raycaster.intersectObject(this.threeJs.pointCloud);

        if (intersections.length > 0) {
            this._handlePointSelect(intersections[0].index, intersections[0].point);
        } else {
            this._clearPointSelect();
        }
    },

    _handlePointSelect : function(index, point) {
        var dataItem = this.data[index];
        var color = this.threeJs.pointCloud.geometry.colors[index];

        if (!this.threeJs.selectionMesh) {
            var selectionBox = new THREE.SphereGeometry(this.pointSize * 0.8, 8, 8);
            var selectionMaterial = new THREE.MeshBasicMaterial( { color: color, opacity: 1.0, transparent: false } );
            this.threeJs.selectionMesh = new THREE.Mesh( selectionBox, selectionMaterial );
        } else {
            this.threeJs.selectionMesh.material.color = color;
        }

        this.threeJs.selectionMesh.position.set(
                this.d3.xScale(dataItem[this.xAttr]),
                this.d3.yScale(dataItem[this.yAttr]),
                this.d3.zScale(dataItem[this.zAttr]));
        this.threeJs.scene.add(this.threeJs.selectionMesh);
        this._renderThreeJs();
        this.fireEvent('select', this, dataItem);
    },

    _clearPointSelect : function() {
        if (this.threeJs.selectionMesh) {
            this.threeJs.scene.remove(this.threeJs.selectionMesh);
            this.threeJs.selectionMesh = null;
            this._renderThreeJs();
        }

        this.fireEvent('deselect', this);
    },

    /**
     * Clear the entire contents of the scatter plot
     */
    clearPlot : function() {
        if (!this.threeJs) {
            return;
        }

        for (var i = this.threeJs.scene.children.length - 1; i >= 0; i--) {
            this.threeJs.scene.remove(this.threeJs.scene.children[i]);
        }
        this.d3 = {};
        this.data = null;
    },

    /**
     * Update the scatter plot with the specified data
     *
     * Adapted from http://bl.ocks.org/phil-pedruco/9852362
     *
     * @param data Object[] of objects containing x,y,z attributes and a "plot" attribute
     */
    plot : function(data) {
        var me = this;

        function v(x, y, z) {
            return new THREE.Vector3(x, y, z);
        }

        function createTextCanvas(text, color, font, size) {
            size = size || 16;
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            var fontStr = (size + 'px ') + (font || 'Arial');
            ctx.font = fontStr;
            var w = ctx.measureText(text).width;
            var h = Math.ceil(size);
            canvas.width = w;
            canvas.height = h;
            ctx.font = fontStr;
            ctx.fillStyle = color || 'black';
            ctx.fillText(text, 0, Math.ceil(size * 0.8));
            return canvas;
        }

        function createText2D(text, color, font, size, segW,
                segH) {
            var canvas = createTextCanvas(text, color, font, size);
            var plane = new THREE.PlaneGeometry(canvas.width, canvas.height, segW, segH);
            var tex = new THREE.Texture(canvas);
            tex.needsUpdate = true;
            var planeMat = new THREE.MeshBasicMaterial({
                map : tex,
                color : 0xffffff,
                transparent : true
            });

            // This is how we view the reversed text from behind
            // see:
            // http://stackoverflow.com/questions/20406729/three-js-double-sided-plane-one-side-reversed
            var backPlane = plane.clone();
            plane.merge(backPlane, new THREE.Matrix4().makeRotationY(Math.PI), 1);

            var mesh = new THREE.Mesh(plane, planeMat);
            mesh.scale.set(0.5, 0.5, 0.5);
            mesh.material.side = THREE.FrontSide;
            return mesh;
        }

        this.clearPlot();
        this.data = data;

        this.d3.xExtent = this.xDomain ? this.xDomain : d3.extent(data, function(d) {return d[me.xAttr];});
        this.d3.yExtent = this.yDomain ? this.yDomain : d3.extent(data, function(d) {return d[me.yAttr];});
        this.d3.zExtent = this.zDomain ? this.zDomain : d3.extent(data, function(d) {return d[me.zAttr];});
        this.d3.valueExtent = this.valueDomain ? this.valueDomain : d3.extent(data, function(d) {return d[me.valueAttr];});

        var format = d3.format("+.3f");
        var vpts = {
            xMax : this.d3.xExtent[1],
            xCen : (this.d3.xExtent[1] + this.d3.xExtent[0]) / 2,
            xMin : this.d3.xExtent[0],
            yMax : this.d3.yExtent[1],
            yCen : (this.d3.yExtent[1] + this.d3.yExtent[0]) / 2,
            yMin : this.d3.yExtent[0],
            zMax : this.d3.zExtent[1],
            zCen : (this.d3.zExtent[1] + this.d3.zExtent[0]) / 2,
            zMin : this.d3.zExtent[0]
        };

        var xScale, yScale, zScale, valueScale;

        xScale = this.d3.xScale = d3.scale.linear()
            .domain(this.d3.xExtent)
            .range([ -50, 50 ]);
        yScale = this.d3.yScale = d3.scale.linear()
            .domain(this.d3.yExtent)
            .range([ -50, 50 ]);
        zScale = this.d3.zScale = d3.scale.linear()
            .domain(this.d3.zExtent)
            .range([ -50, 50 ]);

        if (this.valueScale === 'linear') {
            valueScale = this.d3.valueScale = d3.scale.linear()
        } else if (this.valueScale === 'log') {
            valueScale = this.d3.valueScale = d3.scale.log()
        } else {
            throw 'Invalid valueScale: ' + this.valueScale;
        }
        valueScale.domain(this.d3.valueExtent).range([ 0, 1]);

        // Build our axes
        var lineGeo = new THREE.Geometry();
        lineGeo.vertices.push(
            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMin)), v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMin)),
            v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zMin)), v(xScale(vpts.xMin), yScale(vpts.yMax), zScale(vpts.zMin)),
            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMin)),

            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zCen)), v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zCen)),
            v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zCen)), v(xScale(vpts.xMin), yScale(vpts.yMax), zScale(vpts.zCen)),
            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zCen)),

            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMax)), v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMax)),
            v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zMax)), v(xScale(vpts.xMin), yScale(vpts.yMax), zScale(vpts.zMax)),
            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMax)),

            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMin)), v(xScale(vpts.xMin), yScale(vpts.yMax), zScale(vpts.zMin)),
            v(xScale(vpts.xMin), yScale(vpts.yMax), zScale(vpts.zMax)), v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMax)),
            v(xScale(vpts.xMin), yScale(vpts.yMin), zScale(vpts.zMax)), v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zMax)),

            v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zMin)), v(xScale(vpts.xCen), yScale(vpts.yMax), zScale(vpts.zMin)),
            v(xScale(vpts.xCen), yScale(vpts.yMax), zScale(vpts.zMax)), v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zMax)),
            v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zMax)), v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMax)),

            v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMin)), v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zMin)),
            v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zMax)), v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMax)),
            v(xScale(vpts.xMax), yScale(vpts.yMin), zScale(vpts.zMax)),

            v(xScale(vpts.xMax), yScale(vpts.yCen), zScale(vpts.zMax)), v(xScale(vpts.xMax), yScale(vpts.yCen), zScale(vpts.zMin)),
            v(xScale(vpts.xMin), yScale(vpts.yCen), zScale(vpts.zMin)), v(xScale(vpts.xMin), yScale(vpts.yCen), zScale(vpts.zMax)),
            v(xScale(vpts.xMax), yScale(vpts.yCen), zScale(vpts.zMax)),

            v(xScale(vpts.xCen), yScale(vpts.yCen), zScale(vpts.zMax)), v(xScale(vpts.xCen), yScale(vpts.yCen), zScale(vpts.zMin)),
            v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zMin)), v(xScale(vpts.xCen), yScale(vpts.yMin), zScale(vpts.zCen)),
            v(xScale(vpts.xCen), yScale(vpts.yMax), zScale(vpts.zCen)), v(xScale(vpts.xMax), yScale(vpts.yMax), zScale(vpts.zCen)),
            v(xScale(vpts.xMax), yScale(vpts.yCen), zScale(vpts.zCen)), v(xScale(vpts.xMin), yScale(vpts.yCen), zScale(vpts.zCen))
        );
        var lineMat = new THREE.LineBasicMaterial({
            color : 0x000000,
            lineWidth : 1
        });
        var line = new THREE.Line(lineGeo, lineMat);
        line.type = THREE.Lines;
        this.threeJs.scene.add(line);

        var titleX = createText2D('-' + this.xLabel);
        titleX.position.x = xScale(vpts.xMin) - (this.xHideValueLabel ? 12 : 12);
        titleX.position.y = 5;
        this.threeJs.scene.add(titleX);

        if (!this.xHideValueLabel) {
            var valueX = createText2D(format(this.d3.xExtent[0]));
            valueX.position.x = xScale(vpts.xMin) - 12;
            valueX.position.y = -5;
            this.threeJs.scene.add(valueX);
        }

        var titleX = createText2D((this.xHideValueLabel ? '+' : '') + this.xLabel);
        titleX.position.x = xScale(vpts.xMax) + (this.xHideValueLabel ? 12 : 12);
        titleX.position.y = 5;
        this.threeJs.scene.add(titleX);

        if (!this.xHideValueLabel) {
            var valueX = createText2D(format(this.d3.xExtent[1]));
            valueX.position.x = xScale(vpts.xMax) + 12;
            valueX.position.y = -5;
            this.threeJs.scene.add(valueX);
        }

        var titleY = createText2D('-' + this.yLabel);
        titleY.position.y = yScale(vpts.yMin) - (this.yHideValueLabel ? 5 : 5);
        this.threeJs.scene.add(titleY);

        if (!this.yHideValueLabel) {
            var valueY = createText2D(format(this.d3.yExtent[0]));
            valueY.position.y = yScale(vpts.yMin) - 15;
            this.threeJs.scene.add(valueY);
        }

        var titleY = createText2D((this.yHideValueLabel ? '+' : '') + this.yLabel);
        titleY.position.y = yScale(vpts.yMax) + (this.yHideValueLabel ? 7 : 15);
        this.threeJs.scene.add(titleY);

        if (!this.yHideValueLabel) {
            var valueY = createText2D(format(this.d3.yExtent[1]));
            valueY.position.y = yScale(vpts.yMax) + 5;
            this.threeJs.scene.add(valueY);
        }

        var titleZ = createText2D('-' + this.zLabel + (this.zHideValueLabel ? '' : ' ' + format(this.d3.zExtent[0])));
        titleZ.position.z = zScale(vpts.zMin) + 2;
        this.threeJs.scene.add(titleZ);

        var titleZ = createText2D((this.zHideValueLabel ? '+' : '') + this.zLabel + ' ' + (this.zHideValueLabel ? '' : ' ' + format(this.d3.zExtent[1])));
        titleZ.position.z = zScale(vpts.zMax) + 2;
        this.threeJs.scene.add(titleZ);

        //THREEJS currently does not support canvas renderer with PointCloud's. The vertices will not render
        //Rather than workaround this - we just print a warning message.
        if (this.useCanvasRenderer) {
            var warningText = createText2D('IE 10 and below currently not supported...', '#ff0000');
            warningText.position.z = 60;
            warningText.position.y = 20;
            this.threeJs.scene.add(warningText);
        }
        
        // Build our scatter plot points
        var mat = new THREE.PointCloudMaterial({
            vertexColors : true,
            size : this.pointSize
        });

        var pointCount = data.length;
        var pointGeo = new THREE.Geometry();
        for (var i = 0; i < pointCount; i++) {
            var x = xScale(data[i][this.xAttr]);
            var y = yScale(data[i][this.yAttr]);
            var z = zScale(data[i][this.zAttr]);
            var rawValue = data[i][this.valueAttr];
            var color;

            if (this.valueRenderer) {
                color = new THREE.Color(this.valueRenderer(rawValue));
            } else {
                var scaledValue = valueScale(rawValue); //Scale to HSL rainbow from 0 - 240
                var hue = (1 - scaledValue) * 180 / 255;
                color = new THREE.Color().setHSL(hue, 1.0, 0.5);
            }

            pointGeo.vertices.push(v(x, y, z));
            pointGeo.colors.push(color);
        }

        this.threeJs.pointCloud = new THREE.PointCloud(pointGeo, mat);
        this.threeJs.scene.add(this.threeJs.pointCloud);

        this._renderThreeJs();
    }
});/**
 * Abstract child class that children of a RecordPanel should extend.
 * 
 * Provides basic functionality for expanding/collapsing this and any potential
 * child classes of this class.
 */
Ext.define('portal.widgets.panel.recordpanel.AbstractChild', {
    extend: 'Ext.panel.Panel',

    config: {
        /**
         * This will need to be set consistently across all RecordPanelAbstractChild
         * for a given "generation"
         * 
         * If true - this abstract child will act as a top level group which means:
         *              Any sibling expanding/collapsing will not affect this panel's expansion
         *              Any child expanding in a sibling will collapse any children of this child
         *              
         * If false - this abstract child will not affect it's siblings expand/collapse state if its child expands
         *              
         */
        groupMode: false
    },
    
    onChildExpand : function(thisPanel, rowPanel) {
        if (thisPanel.getGroupMode()) {
            thisPanel.ownerCt.suspendLayouts();
            thisPanel.ownerCt.items.each(function(sibling) {
                if (sibling instanceof portal.widgets.panel.recordpanel.AbstractChild && sibling.getId() !== thisPanel.getId()) {
                    sibling.collapseChildren();
                }
            });
            thisPanel.ownerCt.resumeLayouts();
        }
    },

    /**
     * Collapses all children panels of this group
     */
    collapseChildren : function() {
        this.items.each(function(item) {
            if (item instanceof portal.widgets.panel.recordpanel.AbstractChild) {
                if (item.getCollapsed() === false) {
                    item.collapse();
                }
            }
        }, this);
    },

    initComponent : function() {
        this.callParent(arguments);

        this.items.each(function(item) {
            if (item instanceof portal.widgets.panel.recordpanel.AbstractChild) {
                item.on('beforeexpand', function(item) {
                    this.fireEvent('childexpand', this, item);
                }, this);
            }
        }, this);

        this.on({
            childexpand : this.onChildExpand,
            scope : this
        });
    }
});
/**
 * An extension to the ExtJS accordian layout. The original layout
 * would always flick back to an above/below panel when the current
 * panel is collapsed. This extension allows a "default" panel to
 * be reselected instead
 */
Ext.define('portal.widgets.layout.AccordianDefault', {
    extend : 'Ext.layout.container.Accordion',
    alias : 'layout.accordiondefault',
    type : 'accordiondefault',

    defaultQuery : null,

    /**
     * Adds the following config:
     * 
     * defaultId : String - itemId of the panel to always open when the current panel is collapsed 
     */
    constructor : function(config) {
        this.defaultQuery = '#' + config.defaultId;
        this.callParent(arguments);
    },
    
    /**
     * Stops all future animations running until resumeAnimations is called
     * 
     * This function is not re-entrant
     */
    suspendAnimations: function() {
        this.animate = false;
        this.originalAnimatePolicy = this.animatePolicy;
        this.animatePolicy = null;
    },
    
    /**
     * 
     * Resumes all future animations. 
     * 
     * This function is not re-entrant
     */
    resumeAnimations: function() {
        if (Ext.isObject(this.originalAnimatePolicy)) {
            this.animate = true;
            this.animatePolicy = this.originalAnimatePolicy;
            this.originalAnimatePolicy = null;
        }
    },

    /**
     * Override the default implementation with one that chooses the next expand
     * target using our "defaultQuery"
     * 
     * This was overridden from the Ext 5.1.1 implementation. Future Ext versions
     * may require this to be reworked
     */
    onBeforeComponentCollapse : function(comp) {
        var me = this, owner = me.owner, toExpand, expanded, previousValue;

        if (me.owner.items.getCount() === 1) {
            // do not allow collapse if there is only one item
            return false;
        }

        if (!me.processing) {
            me.processing = true;
            previousValue = owner.deferLayouts;
            owner.deferLayouts = true;
            //portal-core change
            toExpand = comp.previousSibling(me.defaultQuery)
                    || comp.nextSibling(me.defaultQuery);
            //end portal-core change

            // If we are allowing multi, and the "toCollapse" component is NOT
            // the only expanded Component,
            // then ask the box layout to collapse it to its header.
            if (me.multi) {
                expanded = me.getExpanded();

                // If the collapsing Panel is the only expanded one, expand the
                // following Component.
                // All this is handling fill: true, so there must be at least
                // one expanded,
                if (expanded.length === 1) {
                    toExpand.expand();
                }

            } else if (toExpand) {
                toExpand.expand();
            }
            owner.deferLayouts = previousValue;
            me.processing = false;
        }
    }
});
/**
 * This class exposes static methods for manipulating the map's layerStore.
 * We call this store the "Active" layer store to help differentiate it from the other layer stores
 * in the application, for example the stores backing the Known Layers and Custom Layers panels. 
 * 
 * ActiveLayerManager does not store any state and isn't meant to be instantiated, it is just 
 * a Singleton with static utility methods.
 * 
 * Because a lot of the components have the activeLayerStore wired in to their config
 * they automatically update their UI when the store is changed. 
 * 
 * It is recommended that this class be the ONLY mechanism for changing the active layer store
 * purely because it makes it easy to maintain and debug if changes are localised.
 */
Ext.define('portal.map.openlayers.ActiveLayerManager', {
    singleton : true,
    alternateClassName: ['ActiveLayerManager'],
    constructor : function(config) {
        this.initConfig(config);        
    },    

    /** adds the given layer to the active layer store */
    addLayer : function(layer) {        
        if (layer) {
            var map = layer.get('renderer').map;
            var activeLayerStore = ActiveLayerManager.getActiveLayerStore(map);
            activeLayerStore.suspendEvents(true);
            // The insert() used below will trigger removal events and an event driven clean up operation.
            // So we set a flag to block it.
            if (activeLayerStore.getCount()>0) {
                activeLayerStore.addingLayer=true;
            }
            activeLayerStore.insert(0,layer);
            activeLayerStore.resumeEvents();
            this.saveApplicationState(map);
        }
    },
    
    /** adds an array of layers to the active layer store */
    addLayers : function(layers) {
        if (layers && layers.length > 0) {
            var map = layers[0].get('renderer').map;
            var activeLayerStore = ActiveLayerManager.getActiveLayerStore(map);
            for (var i = 0; i < layers.length; i++) {
                activeLayerStore.suspendEvents(true);
                activeLayerStore.add(layers[i]);    
                activeLayerStore.resumeEvents();
            }
        }
    },
    
    /** removes the given layer from the active layer store */
    removeLayer : function(layer) {
        if (layer) {
            var map = layer.get('renderer').map;
            var activeLayerStore = ActiveLayerManager.getActiveLayerStore(map);
            activeLayerStore.suspendEvents(true);
            activeLayerStore.remove(layer);
            layer.removeDataFromMap();
            activeLayerStore.resumeEvents();
            this.saveApplicationState(map);
        }
    },    
    
    /** removes all layers from the active layer store */
    removeAllLayers : function(map) {
        var activeLayerStore = ActiveLayerManager.getActiveLayerStore(map);
        activeLayerStore.suspendEvents(true);
        activeLayerStore.each (function (layer) {        
            layer.removeDataFromMap();
        });
        activeLayerStore.removeAll();
        activeLayerStore.resumeEvents();
        this.saveApplicationState(map);
    },
    
    /** updates the order of the layers in the store and in the map */
    updateLayerOrder : function(map, layer) {
        layer.reRenderLayerDisplay();
        this.saveApplicationState(map);
        
    },   
    
    /** Gets the active layer store. 
     * If configured with a storeId of 'activeLayerStore' we can look it up from the ExtJS store manager. 
     * If not we should be able to get it from the optional map parameter. 
     * The store manager method is faster although this is normally a one-off interaction
     * coming from the UI so it is really just a few nanoseconds difference.
     * 
     * This method could return null or undefined if the storeId is not configured and the map passed in is null
     * (or the map doesn't have a configured layerStore in which case we have bigger issues - check your Main-UI).
     */
    getActiveLayerStore : function(map) {
        var activeLayerStore = Ext.StoreMgr.lookup("activeLayerStore");
        if (!activeLayerStore && map) {
            activeLayerStore = map.layerStore;
        }
        return activeLayerStore;
    },
    
    /** Saves the map's state using a MapStateSerializer.
     * 
     *  The interesting state includes:
     *   - the map zoom level
     *   - the current map bounds (or rather the center point)
     *   - the currently active layers
     */
    saveApplicationState : function(map) {
        if (map) {
            
            if(typeof(Storage) !== "undefined") {
                var mss = Ext.create('portal.util.permalink.MapStateSerializer');
                
                mss.addMapState(map);
                mss.addLayers(map);                
                
                mss.serialize(function(state, version) {
                    localStorage.setItem("portalStorageApplicationState", state);
                    localStorage.setItem("portalStorageDefaultBaseLayer", map.map.baseLayer.name);
                });
            }
        }
    }
});
/**
 * An Ext.tab.Panel extension that can be manipulated BEFORE
 * the panel is actually rendered. It will behave (programmatically)
 * the same as a rendered tab panel.
 *
 * This means You should be able to add/remove tabs, set active tabs etc.
 * Attempting to do this on a regular tab panel BEFORE it has been
 * rendered will result in all sorts of undefined behaviour.
 */
Ext.define('portal.widgets.tab.ActivePreRenderTabPanel', {
    extend : 'Ext.tab.Panel',
    alias : 'widget.activeprerendertabpanel',

    //this exists to workaround issues with adding tabs to a non rendered tab panel
    //The strategy is that before the panel is rendered, all child tabs are added to this list instead
    //Upon the first render, this list will be used to populate the newly rendered tab panel.
    beforeRenderedTabs : [],

    //This is for setting the active tab AFTER rendering
    beforeRenderedActiveTab : '',

    constructor : function(config) {
        this.callParent(arguments);

        this.on('render', function(cmp) {
            cmp.loadBeforeRenderedTabs();
        });
    },

    /**
     * Our workaround is to record all tabs as they added/removed to this panel pre render. When
     * the render occurs, apply all the changes to the newly rendered panel.
     */
    loadBeforeRenderedTabs : function() {
        this.removeAll(true);
        this.add(this.beforeRenderedTabs);
        this.beforeRenderedTabs = [];
        if (this.beforeRenderedActiveTab) {
            this.setActiveTab(this.beforeRenderedActiveTab);
        }
    },

    /**
     * Wraps the normal add function, intercepts adds on non rendered panels
     */
    add : function(items) {
        if (this.rendered) {
            return this.callParent(arguments);
        }

        if (Ext.isArray(items)) {
            this.beforeRenderedTabs = this.beforeRenderedTabs.concat(items);
        } else {
            this.beforeRenderedTabs.push(items);
        }

        return items;
    },

    /**
     * Wraps the normal getComponent function, intercepts gets on non rendered panels.
     */
    getComponent : function(comp) {
        if (this.rendered) {
            return this.callParent(arguments);
        }

        var compId = null;
        if (Ext.isString(comp)) {
            compId = comp;
        } else if (Ext.isObject(comp) && comp.getItemId) {
            compId = comp.getItemId();
        } else {
            compId = comp.itemId;
        }

        for (var i = 0; i < this.beforeRenderedTabs.length; i++) {
            if (compId === this.beforeRenderedTabs[i].itemId) {
                return this.beforeRenderedTabs[i];
            }
        }

        return null;
    },

    /**
     * Wraps the normal remove function, intercepts remove before tab render
     */
    remove : function(comp, autoDestroy) {
        if (this.rendered) {
            return this.callParent(arguments);
        }

        var beforeRenderedCmp = this.getComponent(comp);
        if (beforeRenderedCmp) {
            var beforeLen = this.beforeRenderedTabs.length;
            this.beforeRenderedTabs = Ext.Array.remove(this.beforeRenderedTabs, comp);

            var destroy = autoDestroy || this.autoDestroy || beforeRenderedCmp.autoDestroy;
            if (destroy && beforeRenderedCmp.rendered) {
                beforeRenderedCmp.destroy();
            }
        }

        return beforeRenderedCmp;
    },

    /**
     * Wraps the removeAll function
     */
    removeAll : function(autoDestroy) {
        if (this.rendered) {
            return this.callParent(arguments);
        }

        for (var i = this.beforeRenderedTabs.length - 1; i >= 0; i--) {
            this.remove(this.beforeRenderedTabs[i], autoDestroy);
        }

        return [];
    },

    /**
     * Wraps the normal setActiveTab function.
     */
    setActiveTab : function(tabName) {
        if (this.rendered) {
            return this.callParent(arguments);
        }

        if (this.getComponent(tabName)) {
            this.beforeRenderedActiveTab = tabName;
            return true;
        }

        return false;
    }
});/**
 * This is a portal core specialisation/alternative to Ext.Ajax.request that specialises
 * in receiving JSON responses in the form:
 * 
 *  {
 *    success : Boolean - Whether the request was successful (as reported by the server)
 *    data : (Optional) Object/Array - Any response information from the server
 *    message : (Optional) String - Any extra human readable information reported by the server
 *    debugInfo : (Optional) Object - Any extra debug information pertaining to the request and its processing
 *  }
 *  
 *  Any other types of response formats should be made through Ext.Ajax.request as per normal
 */
Ext.define('portal.util.Ajax', {
    singleton: true,
    
    /**
     * Handles all parsing logic and error conditions for a boolean success flag and a XMLHttpRequest object
     * 
     * @param success Boolean - whether the remote connection was successful
     * @param response XMLHttpRequest - response object
     * @param callback function(success, data, message, debugInfo)
     *               success - Boolean - true if the connection succeeded AND the server reported success. false otherwise
     *               data - Array/Object - contents of the data response (if any)
     *               message - String - String message returned by server if connection succeeded, otherwise a generic HTTP error message
     *               debugInfo - Object - debug object returned by server (if any)
     *               response - Object - Ajax response object
     */
    parseResponse: function(success, response, callback) {
        if (!success) {
            var message = response.status ? 
                            response.status + ': ' + response.statusText :
                            'Network Error: Cannot connect to server.';

            callback(false, undefined, message, undefined, response);
            return;
        }
        
        var responseObj = null;
        try {
            responseObj = Ext.JSON.decode(response.responseText);
        } catch(err) {
            console.log('ERROR parsing Ajax response:', err);
            callback(false, undefined, undefined, undefined, response);
            return;
        }
        
        try {
            callback(responseObj.success === true, responseObj.data, responseObj.msg, responseObj.debugInfo, response);
        } catch(err) {
            console.log('ERROR calling user callback:', err);
            return;
        }
    },
    
    /**
     * Exactly the same as Ext.Ajax.request with the following changes/additions:
     * 
     *  callback - function(success, data, message, debugInfo)
     *               success - Boolean - true if the connection succeeded AND the server reported success. false otherwise
     *               data - Array/Object - contents of the data response (if any)
     *               message - String - String message returned by server if connection succeeded, otherwise a generic HTTP error message
     *               debugInfo - Object - debug object returned by server (if any)
     *               response - Object - Ajax response object
     *               
     *  success - function(data, message, debugInfo) - This will be called IFF the connection succeeds AND the response object indicates success
     *               data - Array/Object - contents of the data response (if any)
     *               message - String - String message returned by server if connection succeeded, otherwise a generic HTTP error message
     *               debugInfo - Object - debug object returned by server (if any)
     *               response - Object - Ajax response object
     *               
     *  failure - function(message, debugInfo) - This will be called if the connection fail OR the response object indicates failure
     *               message - String - String message returned by server if connection succeeded, otherwise a generic HTTP error message
     *               debugInfo - Object - debug object returned by server (if any)
     *               response - Object - Ajax response object
     */
    request: function(cfg) {
        //We do all injection via callback and then offload back 
        //to the user defined success/failure/callback functions
        var userCallbacks = {
            success: cfg.success ? Ext.bind(cfg.success, cfg.scope) : undefined,
            failure: cfg.failure ? Ext.bind(cfg.failure, cfg.scope) : undefined,
            callback: cfg.callback ? Ext.bind(cfg.callback, cfg.scope) : undefined
        };
        delete cfg.failure;
        delete cfg.success;
        delete cfg.scope;
        
        var scope = cfg.scope;
        cfg.callback = Ext.bind(function(options, success, response, userCallbacks) {
            //Because we need to parse the response before we can workout whether to call success/failure callbacks
            //We need to go another level deeper with our wrapping functions. This final callback will decide what user callbacks
            //to fire and in what order depending on the results of the parseResponse function.
            portal.util.Ajax.parseResponse(success, response, Ext.bind(function(success, data, message, debugInfo, response, userCallbacks) {
                if (userCallbacks.callback) {
                    userCallbacks.callback(success, data, message, debugInfo, response);
                }
                
                if (success) {
                    if (userCallbacks.success) {
                        userCallbacks.success(data, message, debugInfo, response);
                    }
                } else {
                    if (userCallbacks.failure) {
                        userCallbacks.failure(message, debugInfo, response);
                    }
                }
            }, undefined, [userCallbacks], true));
        }, undefined, [userCallbacks], true);
        
        return Ext.Ajax.request(cfg);
    }
});/**
*
*  Base64 encode / decode
*  http://www.webtoolkit.info/
*
**/
Ext.define('portal.util.Base64', {

    statics : {
        // private property
        _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

        // public method for encoding
        encode : function (input) {
            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;

            input = portal.util.Base64._utf8_encode(input);

            while (i < input.length) {

                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output +
                portal.util.Base64._keyStr.charAt(enc1) + portal.util.Base64._keyStr.charAt(enc2) +
                portal.util.Base64._keyStr.charAt(enc3) + portal.util.Base64._keyStr.charAt(enc4);

            }

            return output;
        },

        // public method for decoding
        decode : function (input) {
            var output = "";
            var chr1, chr2, chr3;
            var enc1, enc2, enc3, enc4;
            var i = 0;

            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            while (i < input.length) {

                enc1 = portal.util.Base64._keyStr.indexOf(input.charAt(i++));
                enc2 = portal.util.Base64._keyStr.indexOf(input.charAt(i++));
                enc3 = portal.util.Base64._keyStr.indexOf(input.charAt(i++));
                enc4 = portal.util.Base64._keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }

            }

            output = portal.util.Base64._utf8_decode(output);

            return output;

        },

        // private method for UTF-8 encoding
        _utf8_encode : function (string) {
            string = string.replace(/\r\n/g,"\n");
            var utftext = "";

            for (var n = 0; n < string.length; n++) {

                var c = string.charCodeAt(n);

                if (c < 128) {
                    utftext += String.fromCharCode(c);
                }
                else if((c > 127) && (c < 2048)) {
                    utftext += String.fromCharCode((c >> 6) | 192);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
                else {
                    utftext += String.fromCharCode((c >> 12) | 224);
                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                    utftext += String.fromCharCode((c & 63) | 128);
                }

            }

            return utftext;
        },

        // private method for UTF-8 decoding
        _utf8_decode : function (utftext) {
            var string = "";
            var i = 0;
            var c = c1 = c2 = 0;

            while ( i < utftext.length ) {

                c = utftext.charCodeAt(i);

                if (c < 128) {
                    string += String.fromCharCode(c);
                    i++;
                }
                else if((c > 191) && (c < 224)) {
                    c2 = utftext.charCodeAt(i+1);
                    string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                    i += 2;
                }
                else {
                    c2 = utftext.charCodeAt(i+1);
                    c3 = utftext.charCodeAt(i+2);
                    string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                    i += 3;
                }

            }

            return string;
        }
    }

});
/**
 * An abstract Ext.Panel extension forming the base
 * for all Querier components to derive from
 */
Ext.define('portal.layer.legend.BaseComponent', {
    extend: 'Ext.Panel',
    alias : 'widget.legendbasecomponent'
});/**
 * An abstract Ext.Panel extension forming the base
 * for all Querier components to derive from
 */
Ext.define('portal.layer.querier.BaseComponent', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.querierbasecomponent',

    /**
     * The title to be used in the event of this base component being rendered
     * inside some form of tab control (or any other parent container requiring a title).
     */
    tabTitle : 'Summary',

    constructor : function(cfg) {
        if (cfg.tabTitle) {
            this.tabTitle = cfg.tabTitle;
        }
        this.callParent(arguments);
    }
});/**
 * Panel extension for graphing a D3 (d3.org) chart. This class
 * is intended to be extended and plot overridden.
 * 
 * This class provides a wrapper around a chart that will handle 
 * all resizing and aspect ratio setup.
 */
Ext.define('portal.charts.BaseD3Chart', {
    extend: 'Ext.panel.Panel',

    d3svg : null, //D3 SVG element. Will always be set after render
    d3 : null, //D3 elements (graphs, lines etc). Can be null
    targetWidth: null,
    targetHeight : null,


    /**
     * Adds the following config
     * {
     *  initialPlotData - Object - [Optional] - Passed to the plot function after render if specified (otherwise nothing will initially render)
     *  preserveAspectRatio - boolean - Should the graph preserve a 4x2 aspect ratio or should it stretch. Default false
     *  targetWidth - Number - (Only useful if preserveAspectRatio is set) - The target width to use in aspect ratio
     *  targetHeight - Number - (Only useful if preserveAspectRatio is set)  - The target height to use in aspect ratio
     *  svgClass - String - [Optional] - CSS Class to apply to underlying SVG element (defaults to nothing)
     * }
     *
     */
    constructor : function(config) {

        this.initialPlotData = config.initialPlotData ? config.initialPlotData : null;
        this.d3svg = null;
        this.d3 = null;
        this.preserveAspectRatio = config.preserveAspectRatio ? true : false;
        this.targetWidth = config.targetWidth ? config.targetWidth : 800;
        this.targetHeight = config.targetHeight ? config.targetHeight : 400;

        this.innerId = Ext.id();

        Ext.apply(config, {
            html : Ext.util.Format.format('<div id="{0}" style="width:100%;height:100%;"></div>', this.innerId)
        });

        this.callParent(arguments);

        this.on('render', this._afterRender, this);
        this.on('resize', this._onResize, this);
    },

    /**
     * Removes any chart elements. Optionally displays a message
     */
    clearPlot : function(message) {
        if (!this.d3svg) {
            return;
        }

        this.d3svg.select('*').remove();
        this.d3svg.select('.title').remove();
        this.d3 = null;

        this.maskClear();

        if (message) {
            this.d3svg.append("text")
            .attr('x', this.targetWidth/2)
            .attr('y', (this.targetHeight / 2) - 20)
            .attr("width", this.targetWidth)
            .attr("class", "error-text")
            .style("text-anchor", "middle")
            .style("font-size", "32px")
            .text(message)
        }

    },

    /**
     * Set a loading mask for this chart with the specified message
     */
    mask : function(message) {
        if (this._loadMask) {
            this.maskClear();
        }

        this._loadMask = new Ext.LoadMask({
            msg:message,
            target : this
            });
        this._loadMask.show();
    },

    /**
     * Clear any loading mask (or error message) from this panel.
     */
    maskClear : function() {

        this.d3svg.select('.error-text').remove();

        if (this._loadMask) {
            this._loadMask.hide();
            this._loadMask = null;
        }
    },

    /**
     * Will plot the specified data
     * 
     * function(data)
     * data - Object -  
     */
    plot : Ext.util.UnimplementedFunction,


    _afterRender : function() {
        //Load our D3 Instance
        var container = d3.select("#" + this.innerId);
        this.d3svg = container.append("svg")
            .attr("preserveAspectRatio", this.preserveAspectRatio ? "xMidYMid" : "none")
            .attr("viewBox",  Ext.util.Format.format("0 0 {0} {1}", this.targetWidth, this.targetHeight));
        
        if (this.svgClass) {
            this.d3svg.attr("class", this.svgClass)
        }

        //Force a resize
        this._onResize(container.node().offsetWidth, container.node().offsetHeight);

        //Load our data
        if (this.initialPlotData) {
            this.plot(this.initialPlotData);
        }
    },

    _onResize : function(me, width, height) {
        this.d3svg
            .attr("width", width)
            .attr("height", height);
    }


});/**
 * Abstract base class for all Parser factories to inherit from.
 */
Ext.define('portal.layer.querier.wfs.factories.BaseFactory', {
    extend : 'Ext.util.Observable',

    //Namespace Constants
    XMLNS_ER : 'urn:cgi:xmlns:GGIC:EarthResource:1.1',
    XMLNS_ER_2 : 'http://xmlns.earthresourceml.org/EarthResource/2.0',
    XMLNS_ERL : 'http://xmlns.earthresourceml.org/earthresourceml-lite/1.0',
    XMLNS_GSML_2 : 'urn:cgi:xmlns:CGI:GeoSciML:2.0',
    XMLNS_GSML_32 : 'http://xmlns.geosciml.org/GeoSciML-Core/3.2',
    XMLNS_GML : 'http://www.opengis.net/gml',
    XMLNS_GML_32 : 'http://www.opengis.net/gml/3.2',
    XMLNS_SA : 'http://www.opengis.net/sampling/1.0',
    XMLNS_OM : 'http://www.opengis.net/om/1.0',
    XMLNS_SWE : 'http://www.opengis.net/swe/1.0.1',
    XMLNS_GSMLP : 'http://xmlns.geosciml.org/geosciml-portrayal/2.0',
    XMLNS_GSMLP_4 : 'http://xmlns.geosciml.org/geosciml-portrayal/4.0',
    XMLNS_MT : 'http://xmlns.geoscience.gov.au/mineraltenementml/1.0',
    XMLNS_MO : 'http://xmlns.geoscience.gov.au/minoccml/1.0',
    XMLNS_RA : 'http://remanentanomalies.csiro.au',
    XMLNS_CAPDF : 'http://capdf.csiro.au/',
    XMLNS_TIMA : 'https://ogc-jdlc.curtin.edu.au/ns/tima', 

    config : {
        //Reference back to portal.layer.querier.wfs.Parser that spawned this factory. Use
        //this reference to parse nodes that your factory cannot handle.
        parser : null
    },

    /**
     * Accepts all Ext.util.Observable configuration options with the following additions
     * {
     *  parser : portal.layer.querier.wfs.Parser - the parser that owns this factory
     * }
     */
    constructor : function(cfg) {
        this.listeners = cfg.listeners;
        this.callParent(arguments);
    },

    /**
     * abstract - Must be overridden by extending classes
     * This function will return true if this factory is capable of generating a
     * GenericParserComponent for the specified DOM node.
     *
     * Otherwise false must be returned
     *
     * function(domNode)
     *
     * domNode - A W3C DOM Node object
     */
    supportsNode : portal.util.UnimplementedFunction,

    /**
     * abstract - Must be overridden by extending classes
     * This function must return a GenericParserComponent that represents
     * domNode.
     *
     * function(domNode, wfsUrl, rootCfg)
     *
     * domNode - A W3C DOM Node object
     * wfsUrl - The URL of the WFS where domNode was sourced from
     * rootCfg - a configuration object to be applied to the root GenericParser.BaseComponent
     */
    parseNode : portal.util.UnimplementedFunction,


    /**
     * Filters an array of DOM nodes based on the value of an xPath for each node
     * @param nodeList an array of DOM nodes
     * @param xPath String based xPath expression
     * @param value String based comparison value
     */
    _filterNodesWithXPath : function(nodeList, xPath, value) {
        var filteredNodes = [];
        for (var i = 0; i < nodeList.length; i++) {
            if (portal.util.xml.SimpleXPath.evaluateXPathString(nodeList[i], xPath) === value) {
                filteredNodes.push(nodeList[i]);
            }
        }
        return filteredNodes;
    },

    /**
     * Makes a HTML string containing an Anchor element with the specified content.
     * The anchor element will be configured to open a WFS Popup window on click that gets
     * data from the specified URL
     */
    _makeWfsUriPopupHtml : function(uri, content, qtip) {
        return Ext.util.Format.format('<a href="#" qtip="{2}" onclick="var w=window.open(\'wfsFeaturePopup.do?url={0}\',\'AboutWin\',\'toolbar=no, menubar=no,location=no,resizable=yes,scrollbars=yes,statusbar=no,height=450,width=820\');w.focus();return false;">{1}</a>', uri, content, qtip ? qtip : '');
    },

    /**
     * Makes a HTML string containing an Anchor element with the specified content.
     * The anchor element will be configured to open a WFS Popup window on click that gets
     * data from the specified URL
     */
    _makeWFSPopupHtml : function(wfsUrl, typeName, featureId, content, qtip) {
        var url = Ext.util.Format.format('{0}&typeName={1}&featureId={2}', wfsUrl, typeName, featureId);
        return this._makeWfsUriPopupHtml(url, content, qtip);
    },

    /**
     * Makes a HTML string containing an anchor with the specified content.
     * The anchor element will be configured to open another window on click that gets
     * data from the specified URL
     */
    _makeGeneralPopupHtml : function(url, content, qtip) {
        return Ext.util.Format.format('<a href="#" qtip="{2}" onclick="var w=window.open(\'{0}\',\'AboutWin\',\'toolbar=no, menubar=no,location=no,resizable=yes,scrollbars=yes,statusbar=no,height=450,width=820\');w.focus();return false;">{1}</a>',url, content, qtip ? qtip : '');
    },

    /**
     * Makes a HTML string containing an Anchor element with the specified content.
     * The anchor element will be configured to open a RDF Popup window on click that gets
     * data from the specified URI
     */
    _makeVocabPopupHtml : function(conceptUri, content, qtip) {
        var vocabUrl = VOCAB_SERVICE_URL;
        if (vocabUrl[vocabUrl.length - 1] !== '/') {
            vocabUrl += '/';
        }
        vocabUrl += 'getConceptByURI?';

        return Ext.util.Format.format('<a href="#" qtip="{3}" onclick="var w=window.open(\'{0}{1}\',\'AboutWin\',\'toolbar=no, menubar=no,location=no,resizable=yes,scrollbars=yes,statusbar=no,height=450,width=820\');w.focus();return false;">{2}</a>', vocabUrl, conceptUri, content, qtip ? qtip : '');
    },

    /**
     * Makes a URL that when queried will cause the backend to proxy a WFS request to wfsUrl for a type with a specific ID.
     * The resulting XML will be returned.
     *
     * @param wfsUrl String - WFS url to query
     * @param typeName String - WFS type to query
     * @param featureTypeId String - the ID of the type to query
     */
    _makeFeatureRequestUrl : function(wfsUrl, typeName, featureTypeId, optionalParams) {
        var result = portal.util.URL.base + "requestFeature.do" + "?" +
            "serviceUrl=" + wfsUrl + "&typeName=" + typeName +
            "&featureId=" + featureTypeId;
        if(optionalParams){
            for (var i=0; i < optionalParams.length; i++){
                result = result + "&" + optionalParams[i].key + "=" + optionalParams[i].value
            }
        }

        return result;
    },


    /**
     * Parse a wfs request to the spatial server
     * The resulting XML will be returned.
     *
     * @param wfsUrl String - WFS url to query
     * @param typeName String - WFS type to query
     * @param featureTypeId String - the ID of the type to query
     */
    _makeWFSFeatureRequestUrl : function(wfsUrl, typeName, featureTypeId, optionalParams) {

        // VT: ugly hack to convert a wms url to a wfs url. This is ugly because in Openlayer.map getQueryTarget,
        // we hav to iterate through a wms resource to make a GetFeatureInfo request. we did not design it to handle
        // wfs resources. this solution will have to suffice for now until we see the need to redesign the QueryTarget object.
        // I thought of adding wfsonlineresource but that would just add more confusion. so far we only require the wfs url. if the
        // need arise we can then refactor.
        wfsUrl=wfsUrl.substring(0,wfsUrl.indexOf("?"));
        if(wfsUrl.substring((wfsUrl.length -3),wfsUrl.length).toLowerCase() == "wms"){
            wfsUrl=wfsUrl.substring(0,(wfsUrl.length - 3));
        }
        wfsUrl = wfsUrl + "wfs";

        var result = wfsUrl + "?service=WFS&version=1.1.0&request=GetFeature&typeName=" + typeName +
            "&featureId=" + featureTypeId;
        if(optionalParams){
            for (var i=0; i < optionalParams.length; i++){
                result = result + "&" + optionalParams[i].key + "=" + optionalParams[i].value
            }
        }

        return result;
    },

    /**
     * Decomposes a 'normal' URL in the form http://url.com/long/path/name to just its prefix + hostname http://url.com
     * @param url The url to decompose
     */
    _getBaseUrl : function(url) {
        var splitUrl = url.split('://'); //this should split us into 2 parts
        return splitUrl[0] + '://' + splitUrl[1].slice(0, splitUrl[1].indexOf('/'));
    }
});/**
 * Abstract base class for all Known Layer Parser factories to inherit from.
 */
Ext.define('portal.layer.querier.wfs.knownlayerfactories.BaseFactory', {
    extend : 'Ext.util.Observable',

    /**
     * Accepts all Ext.util.Observable configuration options with the following additions
     * {
     *
     * }
     */
    constructor : function(cfg) {
        this.listeners = cfg.listeners;
        this.callParent(arguments);
    },

    /**
     * abstract - to be overridden to return a boolean indicating whether this factory can
     * generate GenericParser.BaseComponent objects representing ancillary information about
     * a particular feature in a known layer
     *
     * function(knownLayer)
     *
     */
    supportsKnownLayer : portal.util.UnimplementedFunction,

    /**
     * abstract - Must be overridden by extending classes
     * This function must return an portal.layer.querier.BaseComponent object that represents
     * ancillary information about the specified feature.
     *
     * function(featureId, parentKnownLayer, parentOnlineResource, rootCfg, parentLayer)
     *
     * featureId - A string representing some form of unique ID
     * parentKnownLayer - The knownLayer that the feature belongs to (cannot be null)
     * parentOnlineResource - The online resource (belonging to parentCSWRecord) that featureId is derived from
     * rootCfg - a configuration object to be applied to the root GenericParser.BaseComponent
     * parentLayer - The portal.layer.Layer representing the layer generated from parentKnownLayer
     */
    parseKnownLayerFeature : portal.util.UnimplementedFunction,


    /**
     * Decomposes a 'normal' URL in the form http://url.com/long/path/name to just its prefix + hostname http://url.com
     * @param url The url to decompose
     */
    getBaseUrl : function(url) {
        var splitUrl = url.split('://'); //this should split us into 2 parts
        return splitUrl[0] + '://' + splitUrl[1].slice(0, splitUrl[1].indexOf('/'));
    }
});/**
 * The abstract base filter form for all Portal filter forms to inherit from
 *
 * The 'formloaded' event must be raised and isFormLoaded set to true before the system will
 * start requesting information from the specified filterer. By default this occurs in the constructor
 * if delayedFormLoading is false otherwise it is the responsiblity of the child class to raise/set this
 *
 * This allows forms to make external requests for data when initialising without fear of being
 * required to generate a filter object too early
 *
 *
 */
Ext.define('portal.layer.filterer.BaseFilterForm', {
    extend: 'Ext.form.Panel',

    config : {
        isFormLoaded : false //has the 'formloaded' event fired yet?
    },

    map : null, //an instance of portal.util.gmap.GMapWrapper
    layer : null, //an instance of portal.layer.Layer
    delayedFormLoading : false, //Setting this will indicate that this form will not be immediately available for filtering after creation
                                //due to having to load something from an external source.

    /**
     * Accepts a Ext.form.Panel config as well as
     * {
     *      map : [Required] an instance of portal.util.gmap.GMapWrapper
     *      layer : [Required] an instance of portal.layer.Layer which you wish to filter
     *      delayedFormLoading : If set to false, the form will able to interact with its filterer immediately, if set
     *                           to true then the form will not be interacted with until formloaded is raised
     * }
     */
    constructor : function(config) {
        this.map = config.map;
        this.layer = config.layer;
        this.setIsFormLoaded(false);
        this.delayedFormLoading = config.delayedFormLoading;        
        
        Ext.apply(config, {
            cls : 'filter-panel-color'
        })

        this.callParent(arguments);        
        
        if (!this.delayedFormLoading) {
            this.setIsFormLoaded(true);
            this.fireEvent('formloaded', this);
        }
    },
    
    onDestroy : function() {
        this.callParent();
    },
    
    setLayer : function(layer){
        this.layer = layer;
    },

    /**
     * Write this FilterForm's contents to the specified portal.layer.filterer.Filterer object.
     *
     * The default functionality is to use the internal FormPanel Form values to write
     * to the filterer, child classes can override this method for their own custom functionality.
     *
     * filterer - an instance of portal.layer.filterer.Filterer which will be cleared and then updated with this form's contents
     */
    writeToFilterer : function(filterer) {
        var parameters = this.getForm().getValues();

        //Ensure we preserve the spatial filter (if any).
        var bbox = filterer.getSpatialParam();
        parameters[portal.layer.filterer.Filterer.BBOX_FIELD] = bbox;

        //All other filter params should be overwritten
        filterer.setParameters(parameters, true);
    },

    /**
     * Write portal.layer.filterer.Filterer object and use it to populate the internal form's values (which will
     * update the corresponding GUI elements).
     *
     * The default functionality is to set the internal FormPanel Form values, child classes can override
     * this method with their own custom functionality.
     *
     * filterer - an instance of portal.layer.filterer.Filterer which will be read
     */
    readFromFilterer : function(filterer) {
        var parameters = filterer.getParameters();
        this.getForm().setValues(parameters);
    }    

});/**
 * Abstract class for representing the core functionality that the
 * portal requires from a mapping API.
 */
Ext.define('portal.map.BaseMap', {
    extend : 'Ext.util.Observable',

    /**
     * Instance of portal.layer.LayerStore. Can be null
     */
    layerStore : null,

    /**
     * Layer ID of the latest opened info window
     */
    openedInfoLayerId : null,

    /**
     * An instance of portal.map.BasePrimitiveManager for managing any temporary highlights
     * on the map
     */
    highlightPrimitiveManager : null,

    /**
     * Boolean - Whether this map wrapper has been rendered to a container
     */
    rendered : false,
       

    /**
     * Boolean - whether to allow a data selection widget to appear on the map. Defaults to false
     */
    allowDataSelection : false,

    /**
     * Accepts a config in the form {
     *  container - [Optional] Ext.util.Container that will house the google map instance. If omitted then a call to renderToContainer must be made before this wrapper can be used
     *  layerStore - An instance of portal.layer.LayerStore
     *  allowDataSelection - Boolean - whether to allow a data selection widget to appear on the map. Defaults to false
     * }
     *
     * Adds the following events
     *
     * query : function(portal.map.BaseMap this, portal.layer.querier.QueryTarget[] queryTargets)
     *         Fired whenever the underlying map is clicked and the user is requesting information about one or more layers
     *
     * dataSelect : function(portal.map.BaseMap this, portal.util.BBox region, portal.csw.CSWRecord[] selectedRecords)
     *              Fired whenever the user draws a BBox on the map for data selection purposes. Returns the region requested
     *              along with the list of added CSWRecords that intersect the selected region
     */
    constructor : function(cfg) {
        this.container = cfg.container;
        this.layerStore = cfg.layerStore ? cfg.layerStore : null;

        this.callParent(arguments);

        if (this.container) {
            this.renderToContainer(this.container);
        }

        if (this.layerStore) {
            this.layerStore.on('add', this._onLayerStoreAdd, this);
            this.layerStore.on('remove', this._onLayerStoreRemove, this);

            // give the layerStore an id for fast lookup using ExtJS store manager
            this.layerStore.storeId = 'activeLayerStore';
        }
    },

    /////////////// Unimplemented functions

    /**
     * Utility function for creating an instance of portal.map.primitives.Marker
     *
     * function(id, tooltip, sourceCswRecord, sourceOnlineResource, sourceLayer, point, icon)
     *
     * @param id [Optional] A string based ID that will be used as the gml:id for this marker
     * @param tooltip The text to display when this marker is moused over
     * @param sourceOnlineResource portal.csw.OnlineResource representing where this marker was generated from
     * @param sourceCswRecord portal.csw.CSWRecord representing the owner of sourceOnlineResource
     * @param sourceLayer portal.layer.Layer representing the owner of sourceOnlineResource
     * @param point a portal.map.Point indicating where this marker should be shown
     * @param icon a portal.map.Icon containing display information about how the marker should look
     */
    makeMarker : Ext.util.UnimplementedFunction,

    /**
     * Utility function for creating an instance of portal.map.primitives.Polygon
     *
     * function(id, sourceCswRecord, sourceOnlineResource, sourceLayer, points, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity)
     *
     * @param id A string based ID that will be used as the gml:id for this polygon
     * @param sourceCswRecord portal.csw.CSWRecord representing the owner of sourceOnlineResource
     * @param sourceOnlineResource portal.csw.OnlineResource representing where this polygon was generated from
     * @param sourceLayer portal.layer.Layer representing the owner of sourceOnlineResource
     * @param points an array portal.map.Point objects indicating the bounds of this polygon
     * @param strokeColor [Optional] HTML Style color string '#RRGGBB' for the vertices of the polygon
     * @param strokeWeight [Optional] Width of the stroke in pixels
     * @param strokeOpacity [Optional] A number from 0-1 indicating the opacity of the vertices
     * @param fillColor [Optional] HTML Style color string '#RRGGBB' for the filling of the polygon
     * @param fillOpacity [Optional] A number from 0-1 indicating the opacity of the fill
     *
     */
    makePolygon : Ext.util.UnimplementedFunction,

    /**
     * Utility function for creating an instance of portal.map.primitives.Polyline
     *
     * function(id, sourceCswRecord,sourceOnlineResource, sourceLayer, points, color, weight, opacity)
     *
     * @param id A string based ID that will be used as the gml:id for this line
     * @param sourceCswRecord portal.csw.CSWRecord representing the owner of sourceOnlineResource
     * @param sourceOnlineResource portal.csw.OnlineResource representing where this line was generated from
     * @param sourceLayer portal.layer.Layer representing the owner of sourceOnlineResource
     * @param points an array portal.map.Point objects indicating the bounds of this polygon
     * @param color [Optional] HTML Style color string '#RRGGBB' for the line
     * @param weight [Optional] Width of the stroke in pixels
     * @param opacity [Optional] A number from 0-1 indicating the opacity of the line
     */
    makePolyline : Ext.util.UnimplementedFunction,

    /**
     * Utility function for creating an instance of portal.map.primitives.BasWMSPrimitive
     *
     * function(id, sourceCswRecord, sourceOnlineResource, sourceLayer, wmsUrl, wmsLayer, opacity)
     *
     * @param id A string based ID that will be used as the gml:id for this line
     * @param sourceCswRecord portal.csw.CSWRecord representing the owner of sourceOnlineResource
     * @param sourceOnlineResource portal.csw.OnlineResource representing where this line was generated from
     * @param sourceLayer portal.layer.Layer representing the owner of sourceOnlineResource
     * @param wmsUrl String - URL of the WMS to query
     * @param wmsLayer String - Name of the WMS layer to query
     * @param opacity Number - opacity/transparency in the range [0, 1]
     */
    makeWms : Ext.util.UnimplementedFunction,

    /**
     * Renders this map to the specified Ext.container.Container.
     *
     * Also sets the rendered property.
     *
     * function(container)
     *
     * @param container The container to receive the map
     */
    renderToContainer : Ext.util.UnimplementedFunction,

    /**
     * Returns the currently visible map bounds as a portal.util.BBox object.
     *
     * function()
     */
    getVisibleMapBounds : Ext.util.UnimplementedFunction,

    /**
     * Creates a new empty instance of the portal.map.PrimitiveManager class for use
     * with this map
     *
     * function()
     */
    makePrimitiveManager : Ext.util.UnimplementedFunction,

    /**
     * Opens an info window at a location with the specified content. When the window loads initFunction will be called
     *
     * function(windowLocation, width, height, content, initFunction)
     *
     * width - Number - width of the info window in pixels
     * height - Number - height of the info window in pixels
     * windowLocation - portal.map.Point - where the window will be opened from
     * content - Mixed - A HTML string representing the content of the window OR a Ext.container.Container object OR an Array of the previous types
     * initFunction - [Optional] function(portal.map.BaseMap map, Mixed content) a function that will be called when the info window actually opens
     */
    openInfoWindow : Ext.util.UnimplementedFunction,

    /**
     * Causes the map to scroll/zoom so that the specified bounding box is visible
     *
     * function(bbox)
     *
     * @param bbox an instance of portal.util.BBox
     */
    scrollToBounds : Ext.util.UnimplementedFunction,

    /**
     * Gets the numerical zoom level of the current map as a Number
     *
     * function()
     */
    getZoom : Ext.util.UnimplementedFunction,

    /**
     * Sets the numerical zoom level of the current map
     *
     * function(zoom)
     *
     * @param zoom Number based zoom level
     */
    setZoom : Ext.util.UnimplementedFunction,

    /**
     * Pans the map until the specified point is in the center
     *
     * function(point)
     *
     * @param point portal.map.Point to be centered on
     */
    setCenter : Ext.util.UnimplementedFunction,

    /**
     * Gets the location of the center point on the map as a portal.map.Point
     *
     * function()
     */
    getCenter : Ext.util.UnimplementedFunction,

    /**
     * Gets a portal.map.TileInformation describing a specified spatial point
     *
     * function(point)
     *
     * @param point portal.map.Point to get tile information
     */
    getTileInformationForPoint : Ext.util.UnimplementedFunction,

    /**
     * Returns an portal.map.Size object representing the map size in pixels in the form
     *
     * function()
     */
    getMapSizeInPixels : Ext.util.UnimplementedFunction,

    /**
     * Converts a latitude/longitude into a pixel coordinate based on the
     * on the current viewport
     *
     * returns an object in the form
     * {
     *  x : number - offset in x direction
     *  y : number - offset in y direction
     * }
     *
     * function(point)
     *
     * @param point portal.map.Point location to query
     */
    getPixelFromLatLng : Ext.util.UnimplementedFunction,


    /**
     * closes any info windows associated with the removal of a layer
     * function(layerId) : the id of the layer that is being removed
     *
     */
    closeInfoWindow : Ext.util.UnimplementedFunction,    
    
    ////////////////// Base functionality

    /**
     * Causes the map to highlight the specified bounding box by drawing an overlay
     * over it. The highlight will disappear after a short period of time
     *
     * function(bboxes, delay)
     *
     * @param bboxes an instance of portal.util.BBox or an array of portal.util.BBox objects
     * @param delay [Optional] a delay in ms before the highlight is hidden. Defaults to 2000
     */
    highlightBounds : function(bboxes, delay) {
        //Setup our inputs
        delay = delay ? delay : 2000;
        if (!Ext.isArray(bboxes)) {
            bboxes = [bboxes];
        }

        for (var i = 0; i < bboxes.length; i++) {
            var polygonList = bboxes[i].toPolygon(this, '00FF00', 0, 0.7,'#00FF00', 0.6);
            this.highlightPrimitiveManager.addPrimitives(polygonList);
        }

        //Make the bbox disappear after a short while
        var clearTask = new Ext.util.DelayedTask(Ext.bind(function(){
            this.highlightPrimitiveManager.clearPrimitives();
        }, this));

        clearTask.delay(delay);
    },

    /**
     * Opens a context menu on the map at the specified coordinates
     *
     * @param point portal.map.Point location to open menu
     * @param menu Ext.menu.Menu that will be shown
     */
    showContextMenuAtLatLng : function(point, menu) {
        var pixel = this.getPixelFromLatLng(point);
        menu.showAt(this.container.x + pixel.x, this.container.y + pixel.y);
    },

    /**
     * Returns the list of CSWRecords (based on the internal layerStore) whose
     * registered bounding boxes intersect the specified bounding box
     *
     * @param bbox A portal.util.BBox
     *
     * Returns portal.csw.CSWRecord[]
     */
    getLayersInBBox : function(bbox) {
        var intersectedRecords = [];
        if (!this.layerStore) {
            return intersectedRecords;
        }
        
        for (var layerIdx = 0; layerIdx < this.layerStore.getCount(); layerIdx++) {
            var layer = this.layerStore.getAt(layerIdx);
            var cswRecs = layer.get('cswRecords');
            for (var recIdx = 0; recIdx < cswRecs.length; recIdx++) {
                var cswRecord = cswRecs[recIdx];
                var geoEls = cswRecord.get('geographicElements');
                for (var geoIdx = 0; geoIdx < geoEls.length; geoIdx++) {
                    var bboxToCompare = geoEls[geoIdx];
                    if (bbox.intersects(bboxToCompare)) {
                        intersectedRecords.push(cswRecord);
                        break;
                    }
                }
            }
        }
        return intersectedRecords;
    },

    /**
     * Figure out whether we should automatically render this layer or not
     */
    _onLayerStoreAdd : function(store, layers) {
        for (var i = 0; i < layers.length; i++) {
           
            var newLayer = layers[i];
            //Some layer types should be rendered immediately, others will require the 'Apply Filter' button
            //We trigger the rendering by forcing a write to the filterer object
            if (newLayer.get('deserialized')) {
                //Deserialized layers (read from permalink) will have their
                //filterer already fully configured.
                var filterer = newLayer.get('filterer');
                filterer.setParameters({}); //Trigger an update without chang
            } else if (newLayer.get('renderOnAdd')) {
                //Otherwise we will need to append the filterer with the current visible bounds
                var filterForm = newLayer.get('filterForm');
                var filterer = newLayer.get('filterer');

                //Update the filter with the current map bounds
                filterer.setSpatialParam(this.getVisibleMapBounds(), true);

                filterForm.writeToFilterer(filterer);
            }                       
        }
    },

    /**
     * Remove any rendered data from the map
     */
    _onLayerStoreRemove : function(store, layer) {
        //VT: Since Extjs 5, remove event returns a model[] rather than model
        //VT: We only deal with single removal of layer at this stage.
        var renderer = layer[0].get('renderer');
        if (renderer) {
            // If '.addingLayer' is true, then we are here because a layer was removed as part of a layer insert operation
            // In that case we should not abort the display because it will abort the layer insert operation
            if (store.addingLayer) {
                store.addingLayer = false;
            } else {
                renderer.abortDisplay();
            }
            renderer.removeData();
            this.closeInfoWindow(layer[0].get('id'));
        }
    },
    
    getFeaturesFromKMLString : function  (kmlString) {
        var format = new OpenLayers.Format.KML({
            extractStyles: true,
            extractAttributes: true,          
            internalProjection: new OpenLayers.Projection("EPSG:3857") ,
            externalProjection: new OpenLayers.Projection("EPSG:4326")
        });
        return format.read(kmlString);
    }
});/**
 * Base primitive that all map primitives should extend
 */
Ext.define('portal.map.primitives.BasePrimitive', {

    config : {
        /**
         * an identification string that has no uniqueness requirement. Eg - used for identifying WFS feature gml:id's
         */
        id : '',
        /**
         * portal.layer.Layer - the layer that spawned this primitive. Can be null or empty
         */
        layer : null,
        /**
         * portal.csw.OnlineResource - the OnlineResource that spawned this primitive. Can be null or empty
         */
        onlineResource : null,
        /**
         * portal.csw.CSWRecord - the CSWRecord that spawned this primitive. Can be null or empty.
         */
        cswRecord : null
    },

    /**
     * Accepts the following
     *
     * id : String - an identification string that has no uniqueness requirement. Eg - used for identifying WFS feature gml:id's
     * layer : portal.layer.Layer - the layer that spawned this primitive. Can be null or empty.
     * onlineResource : portal.csw.OnlineResource - the OnlineResource that spawned this primitive. Can be null or empty.
     * cswRecord : portal.csw.CSWRecord - the CSWRecord that spawned this primitive. Can be null or empty.
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        this.setId(cfg.id);
        this.setLayer(cfg.layer);
        this.setOnlineResource(cfg.onlineResource);
        this.setCswRecord(cfg.cswRecord);
    }
});/**
 * PrimitiveManager is a class for managing a set of primitives as a distinct group
 * of primitives that can cleared/modified seperately from other primitives that may
 * already be on the map
 *
 * It supports the following events
 * clear - function(portal.map.BasePrimitiveManager manager) - raised after clearPrimitives is called
 * addprimitive - function(portal.map.BasePrimitiveManager manager, portal.map.primitives.BasePrimitive[] primitives) -
 *                raised whenever addPrimitive is called (after the primitive has been added to the map)
 */
Ext.define('portal.map.BasePrimitiveManager', {
    extend: 'Ext.util.Observable',

    /**
     * portal.map.BaseMap - The map instance that created this primitive manager
     */
    baseMap : null,

    /**
     * {
     *  baseMap : portal.map.BaseMap - The map instance that created this primitive manager
     * }
     */
    constructor : function(config) {
     
        this.baseMap = config.baseMap;
        this.callParent(arguments);
    },

    /**
     * function()
     *
     * Removes all primitives (that are managed by this instance) from the map
     */
    clearPrimitives : Ext.util.UnimplementedFunction,

    /**
     * Adds an array of primitive to the map and this instance
     *
     * function(primitive)
     *
     * @param primitive portal.map.primitives.BasePrimitive[] all primitives to add to the map
     */
    addPrimitives : Ext.util.UnimplementedFunction
});/**
 * An abstract base class to be extended.
 *
 * Represents a grid panel for containing layers
 * that haven't yet been added to the map. Each row
 * will be grouped under a heading, contain links
 * to underlying data sources and have a spatial location
 * that can be viewed by the end user.
 *
 * This class is expected to be extended for usage within
 * the 'Registered Layers', 'Known Layers' and 'Custom Layers'
 * panels in the portal. Support for KnownLayers/CSWRecords and
 * other row types will be injected by implementing the abstract
 * functions of this class
 *
 */
Ext.define('portal.widgets.panel.BaseRecordPanel', {
    extend : 'portal.widgets.panel.CommonBaseRecordPanel',
    alias: 'widget.baserecordpanel',

    constructor : function(cfg) {
        var me = this;
       
        me.listeners = Object.extend(me.listenersHere, cfg.listeners);
        
        var menuItems = [me._getVisibleBoundFilterAction(),me._getActivelayerFilterAction(),
                         me._getDataLayerFilterAction(),me._getImageLayerFilterAction()];

        var dockedItems = null;
        if (cfg.hideSearch !== true) {
            dockedItems = [{
                xtype : 'toolbar',
                dock : 'top',
                portalName : 'search-bar', //used for distinguishing this toolbar
                items : [{
                    xtype : 'label',
                    text : 'Search: '
                },{
                    xtype : 'clientsearchfield',
                    id : 'hh-searchfield-' + cfg.title.replace(' ',''),
                    width : 200,
                    fieldName: 'name',
                    store : cfg.store
                },{
                    xtype : 'button',
                    id : 'hh-filterDisplayedLayer-' + cfg.title.replace(' ',''),
                    text : 'View by',
                    iconCls : 'filter',
                    tooltip: 'Provide more options for filtering layer\'s view',
                    arrowAlign : 'right',
                    menu : menuItems
                   
                }]
            }];
        }
        
        Ext.applyIf(cfg, {
            cls : 'auscope-dark-grid',
            emptyText : '<p class="centeredlabel">No records match the current filter.</p>',
            dockedItems : dockedItems,
            titleField: 'name',
            titleIndex: 2,
            tools: [{
                field: 'active',
                clickHandler: Ext.bind(me._deleteClickHandler, me),
                stopEvent: false,
                tipRenderer: function(value, layer, tip) {
                    if(value) {
                        return 'Click to remove layer from map';
                    } else {
                        return 'Click to anywhere on this row to select drop down menu';
                    }
                },
                iconRenderer: me._deleteRenderer
            },{
                field: ['loading', 'active'],
                stopEvent: true,
                clickHandler: Ext.bind(me._loadingClickHandler, me),
                tipRenderer: Ext.bind(me._loadingTipRenderer, me),
                iconRenderer: Ext.bind(me._loadingRenderer, this)
            },{
                field: 'serviceInformation',
                stopEvent: true,
                clickHandler: Ext.bind(me._serviceInformationClickHandler, me),
                tipRenderer: function(value, record, tip) {
                    if ((record instanceof portal.knownlayer.KnownLayer) && record.containsNagiosFailures()) {
                        return 'One or more of the services used by this layer are reported to be experiencing issues at the moment. Some aspects of this layer may not load/work.';
                    }
                    return 'Click for detailed information about the web services this layer utilises.';
                },
                iconRenderer: Ext.bind(me._serviceInformationRenderer, me)
            },{
                field: 'spatialBoundsRenderer',
                stopEvent: true,
                clickHandler: Ext.bind(me._spatialBoundsClickHandler, me),
                doubleClickHandler: Ext.bind(me._spatialBoundsDoubleClickHandler, me),
                tipRenderer: function(layer, tip) {
                    return 'Click to see the bounds of this layer, double click to pan the map to those bounds';
                },
                iconRenderer: Ext.bind(me._spatialBoundsRenderer, me)
            }],
            lazyLoadChildPanel: true,
            childPanelGenerator: function(record) {                  
                //For every filter panel we generate, also generate a portal.layer.Layer and 
                //attach it to the CSWRecord/KnownLayer
                var newLayer = null;
                if (record instanceof portal.csw.CSWRecord) {                        
                    newLayer = cfg.layerFactory.generateLayerFromCSWRecord(record);                                                     
                } else {
                    newLayer = cfg.layerFactory.generateLayerFromKnownLayer(record);                      
                }
                record.set('layer', newLayer);
                return me._getInlineLayerPanel(newLayer.get('filterForm'));
           }
        });

        me.callParent(arguments);
    },
    
    onDestroy : function() {
        me.callParent();
    },

    _getInlineLayerPanel : function(filterForm){                             
        var me = this;   
        var panel = Ext.create('portal.widgets.panel.FilterPanel', {    
            menuFactory : this.menuFactory,
            filterForm  : filterForm, 
            detachOnRemove : false,
            map         : this.map,
            menuItems : []
        });   
        
        return panel
    },
    
    _getVisibleBoundFilterAction : function(){   
        
        var me = this;
        return new Ext.Action({
            text : 'Visible Bound',
            iconCls : 'visible_eye',
            tooltip: 'Filter the layers based on its bounding box and the map\'s visible bound',
            handler : Ext.bind(me._handleVisibleFilterClick, this)
        })
        
    },
    
    _getActivelayerFilterAction : function(){
        var me = this;
        return new Ext.Action({
            text : 'Active Layer',
            iconCls : 'tick',
            tooltip: 'Display only active layer',
            handler : function(){
//              // TODO: Do I need this but using the new id for rowexpandercontainer?
//              // var rowExpander = me.getPlugin('maingrid_rowexpandercontainer');
//              //                rowExpander.closeAllContainers();          
                
                //function to check if layer is active on map
                var filterFn = function(rec) {
                    return rec.get('active');
                };

                var searchField = this.findParentByType('toolbar').getComponent(1);
                searchField.clearCustomFilter();
                searchField.runCustomFilter('<active layers>', Ext.bind(filterFn, this));
            }
        })
    },
    
    _getDataLayerFilterAction : function(){
        var me = this;
        return new Ext.Action({
            text : 'Data Layer',
            iconCls : 'data',
            tooltip: 'Display layer with data service',
            handler : function(){
//              // TODO: Do I need this but using the new id for rowexpandercontainer?
//              // var rowExpander = me.getPlugin('maingrid_rowexpandercontainer');
//              //                rowExpander.closeAllContainers();          
                
                //function to if layer contains data service
                var filterFn = function(rec) {
                    var onlineResources = me.getOnlineResourcesForRecord(rec)
                    var serviceType = me._getServiceType(onlineResources); 
                    
                    //VT:This part of the code is to keep it inline with the code in _serviceInformationRenderer
                    //VT: for rendering the icon
                    if (serviceType.containsDataService) {
                        return true; //a single data service will label the entire layer as a data layer
                    }else{
                        return false;
                    } 

                };

                var searchField = this.findParentByType('toolbar').getComponent(1);
                searchField.clearCustomFilter();
                searchField.runCustomFilter('<Data Layers>', Ext.bind(filterFn, this));
            }
        })
        
    },
    
    _getImageLayerFilterAction : function(){
        var me = this;
        return new Ext.Action({
            text : 'Portrayal Layer',
            iconCls : 'portrayal',
            tooltip: 'Display layers with image service',
            handler : function(){
//              // TODO: Do I need this but using the new id for rowexpandercontainer?
//              // var rowExpander = me.getPlugin('maingrid_rowexpandercontainer');
//              //                rowExpander.closeAllContainers();          
                
                //function to if layer contains image service
                var filterFn = function(rec) {           
                    var onlineResources = me.getOnlineResourcesForRecord(rec);
                    var serviceType = me._getServiceType(onlineResources);                                                                                             
                    
                    //VT:This part of the code is to keep it inline with the code in _serviceInformationRenderer
                    //VT: for rendering the picture icon
                    if (serviceType.containsDataService) {
                        return false; //a single data service will label the entire layer as a data layer
                    } else if (serviceType.containsImageService) {
                        return true;
                    } else {
                        return false;
                    }
                          
                };

                var searchField = this.findParentByType('toolbar').getComponent(1);
                searchField.clearCustomFilter();
                searchField.runCustomFilter('<Portrayal layers>', Ext.bind(filterFn, this));
            }
        })
    },
    
    /**
     * When the visible fn is clicked, ensure only the visible records pass the filter
     */
    _handleVisibleFilterClick : function(button) {                           
        var currentBounds = this.map.getVisibleMapBounds();

        //Function for testing intersection of a records's spatial bounds
        //against the current visible bounds
        var filterFn = function(rec) {
            var spatialBounds;
            spatialBounds = this.getSpatialBoundsForRecord(rec);
            for (var i = 0; i < spatialBounds.length; i++) {
                if (spatialBounds[i].intersects(currentBounds)) {
                    return true;
                }
            }

            return false;
        };

        var searchField = button.findParentByType('toolbar').getComponent(1);
        searchField.clearCustomFilter();
        searchField.runCustomFilter('<visible layers>', Ext.bind(filterFn, this));      
    },       
 
    // Used in sub-classes
    handleFilterSelectComplete : function(filteredResultPanels){
        var me = this;
        var cswSelectionWindow = new CSWSelectionWindow({
            title : 'CSW Record Selection',
            resultpanels : filteredResultPanels,
            listeners : {
                selectioncomplete : function(csws){  
                    var tabpanel =  Ext.getCmp('auscope-tabs-panel');
                    var customPanel = me.ownerCt.getComponent('org-auscope-custom-record-panel');
                    tabpanel.setActiveTab(customPanel);
                    if(!(csws instanceof Array)){
                        csws = [csws];
                    }
                    for(var i=csws.length-1; i>=0; i--){
                        csws[i].set('customlayer',true);
                        customPanel.getStore().insert(0,csws[i]);
                        customPanel.ensureVisible(0);
                    }
                    
                }
            }
        });
        cswSelectionWindow.show();
    },

    /**
     * When called, will update the visibility of any search bars
     */
    _updateSearchBar : function(visible) {
        var dockedItems = this.getDockedItems();
        var searchBar = null;
        for (var i = 0; i < dockedItems.length; i++) {
            if (dockedItems[i].initialConfig.portalName === 'search-bar') {
                searchBar = dockedItems[i];
            }
        }
        if (!searchBar) {
            return;
        }

        if (visible) {
            searchBar.show();
        } else {
            searchBar.hide();
        }
    },

    _deleteRenderer : function(value, record) {
        if (value) {
            return 'portal-core/img/trash.png';
        } else {
            return 'portal-core/img/play_blue.png';
        }
    },
    
    _deleteClickHandler :  function(value, record) {
        var layer = record.get('layer');
        if(layer && record.get('active')){
            ActiveLayerManager.removeLayer(layer);
            this.menuFactory.layerRemoveHandler(layer);
            this.fireEvent('cellclick',this,undefined,undefined,layer,undefined,undefined);
        }
    },

    /**
     * Renderer for the loading column
     */
    _loadingRenderer : function(value, record) {
        if (value) {
            return 'portal-core/img/loading.gif';
        } else {
            
            if(record.get('active')){
            
                var renderStatus = record.get('layer').get('renderer').renderStatus;
                var listOfStatus=renderStatus.getParameters();                
                var errorCount = this._statusListErrorCount(listOfStatus);
                var sizeOfList = Ext.Object.getSize(listOfStatus);
                if(errorCount > 0 && errorCount == sizeOfList){
                    return 'portal-core/img/warning.png';
                }else if(errorCount > 0 && errorCount < sizeOfList){
                    return 'portal-core/img/exclamation.png';
                }else{
                    return 'portal-core/img/tick.png';
                }
                
            }else{
                return 'portal-core/img/notloading.gif';
            }
        }
    },
    
    _statusListErrorCount : function(listOfStatus){
        var match =["reached","error","did not complete","AJAX","Unable"];
        
        var erroCount = 0;  
        
        for(key in listOfStatus){
            for(var i=0; i< match.length; i++){
                if(listOfStatus[key].indexOf(match[i]) > -1){
                    erroCount++;
                    break;
                }
            }
        }
        return erroCount;
    },
    
  
    /**
     * A renderer for generating the contents of the tooltip that shows when the
     * layer is loading
     */
    _loadingTipRenderer : function(value, record, tip) {
        var layer = record.get('layer');
        if(!layer){//VT:The layer has yet to be created.
            return 'No status has been recorded';
        }
        var renderer = layer.get('renderer');
        var update = function(renderStatus, keys) {
            tip.update(renderStatus.renderHtml());
        };

        //Update our tooltip as the underlying status changes
        renderer.renderStatus.on('change', update, this);
        tip.on('hide', function() {
            renderer.renderStatus.un('change', update); //ensure we remove the handler when the tip closes
        });

        return renderer.renderStatus.renderHtml();
    },
    
    _loadingClickHandler : function(value, record) {
        
        var layer = record.get('layer');
        
        var html = '<p>No Service recorded, Click on Add layer to map</p>';    
        
        if(layer){
            var renderer = layer.get('renderer');
            html =  renderer.renderStatus.renderHtml();
        }        
        var win = Ext.create('Ext.window.Window', {
            title: 'Service Loading Status',
            height: 200,
            width: 500,
            layout: 'fit',
            modal : true,
            items: {  // Let's put an empty grid in just to illustrate fit layout
                xtype: 'panel',
                autoScroll : true,                
                html : html
            }
        });
        
        win.show();
    }
});
/**
 * Abstract base class for serializers to extend and implement
 */
Ext.define('portal.util.permalink.serializers.BaseSerializer', {
    constructor : function() {
        this.callParent(arguments);
    },

    /**
     * This function should return the unique version string describing this serializer
     *
     * function()
     *
     * returns - String which should be unique across all serializer implementations
     */
    getVersion : function() {
        return this.$className;
    },

    /**
     * This function takes the state of the map/layers and generates a String that can be used to
     * regenerate the specified state at a later date
     *
     * function(Object mapState, Object serializedLayers, function(string) callback)
     *
     * mapState - Object conforming to the mapState schema defined in portal.util.permalink.MapStateSerializer
     * serializedLayers - Array of Objects conforming to the serializedLayers schema defined in portal.util.permalink.MapStateSerializer
     * callback - The string encoded state will passed to this function after it has been calculated asynchronously
     *
     * returns - Nothing - Use the callback parameter
     */
    serialize : portal.util.UnimplementedFunction,

    /**
     * This function takes a serialized string representing the state of the map/layers and generates an
     * object representation of that state
     *
     * function(String state, function(Object) callback)
     *
     * state - A Object response from the serializeFunction
     * callback - Will be passed an Object in the form below when deserialisation finishes
     *              {
     *                  mapState - Object conforming to the mapState schema defined in portal.util.permalink.MapStateSerializer
     *                  serializedLayers - Array of Objects conforming to the serializedLayers schema defined in portal.util.permalink.MapStateSerializer
     *              }
     *
     * returns Nothing - Use the callback parameter 
     */
    deserialize : portal.util.UnimplementedFunction,
    
    
    /**
     * This function allows us to override and control what parameters we serialize in each version of the Serializer.
     */
    createSerializedObject : portal.util.UnimplementedFunction,
    
    
    /**
     * This function allows us to override and control what parameters we deserialize in each version of the Serializer.
     */
    createDeSerializedObject : portal.util.UnimplementedFunction
});/**
 * Represents an primitive that sources its imagery directly from
 *
 */
Ext.define('portal.map.primitives.BaseWMSPrimitive', {

    extend : 'portal.map.primitives.BasePrimitive',

    statics : {
        /**
         * Utility function for generating a WMS GetMap request URL
         *
         * @param serviceUrl String - The WMS URL
         * @param layer String - the WMS layer name
         * @param bbox portal.util.BBox - the bounding box to request imagery for
         * @param width Number - The width of the response image in pixels
         * @param height Number - The height of the response image in pixels
         * @param imageFormat [Optional] String - the image MIME type to request - default 'image/png'
         */
        getWmsUrl : function(serviceUrl, layer, bbox, width, height, imageFormat) {

            var bbox_3857=bbox.transform(bbox,'EPSG:3857');

            var bboxString = Ext.util.Format.format('{0},{1},{2},{3}',
                    bbox_3857.westBoundLongitude,
                    bbox_3857.southBoundLatitude,
                    bbox_3857.eastBoundLongitude,
                    bbox_3857.northBoundLatitude);

            var params = {
                'REQUEST' : 'GetMap',
                'SERVICE' : 'WMS',
                'VERSION' : '1.1.1',
                'FORMAT' : imageFormat ? imageFormat : 'image/png',
                'BGCOLOR' : '0xFFFFFF',
                'TRANSPARENT' : 'TRUE',
                'LAYERS' : layer,
                'SRS' :bbox_3857.crs,
                'BBOX' : bboxString,
                'WIDTH' : width,
                'HEIGHT' : height,
                'STYLES' : '' //Some WMS implementations require this
            };

            var queryString = Ext.Object.toQueryString(params);
            return Ext.urlAppend(serviceUrl, queryString);
        },

        /**
         * Utility function for generating a WMS 1.3 GetMap request URL
         *
         * @param serviceUrl String - The WMS URL
         * @param layer String - the WMS layer name
         * @param bbox portal.util.BBox - the bounding box to request imagery for
         * @param width Number - The width of the response image in pixels
         * @param height Number - The height of the response image in pixels
         * @param imageFormat [Optional] String - the image MIME type to request - default 'image/png'
         */
        getWms_130_Url : function(serviceUrl, layer, bbox, width, height, imageFormat) {

            var bbox_3857=bbox.transform(bbox,'EPSG:3857');

            var bboxString = Ext.util.Format.format('{0},{1},{2},{3}',
                    bbox_3857.southBoundLatitude,
                    bbox_3857.westBoundLongitude,
                    bbox_3857.northBoundLatitude,
                    bbox_3857.eastBoundLongitude
                    );

            var params = {
                'REQUEST' : 'GetMap',
                'SERVICE' : 'WMS',
                'VERSION' : '1.3.0',
                'FORMAT' : imageFormat ? imageFormat : 'image/png',
                'BGCOLOR' : '0xFFFFFF',
                'TRANSPARENT' : 'TRUE',
                'LAYERS' : layer,
                'CRS' :bbox_3857.crs,
                'BBOX' : bboxString,
                'WIDTH' : width,
                'HEIGHT' : height,
                'STYLES' : '' //Some WMS implementations require this
            };

            var queryString = Ext.Object.toQueryString(params);
            return Ext.urlAppend(serviceUrl, queryString);
        }
    },

    config : {
        /**
         * String - The WMS URL endpoint to query
         */
        wmsUrl : '',
        /**
         * String - the WMS layer name to query
         */
        wmsLayer : '',
        /**
         * Number - the opacity of the layer in the range [0, 1]
         */
        opacity : 1.0,       

        sld_body : ''

    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * wmsUrl : String - The WMS URL endpoint to query
     * layer : String - the WMS layer name to query
     * opacity : Number - the opacity of the layer in the range [0, 1]
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        this.setWmsUrl(cfg.wmsUrl);
        this.setWmsLayer(cfg.wmsLayer);
        this.setOpacity(cfg.opacity);
        this.setSld_body(cfg.sld_body);
       
    }

});/**
 * Represents a simple Bounding Box
 * {
 * northBoundLatitude : Northern most latitude
 * southBoundLatitude : Southern most latitude
 * eastBoundLongitude : Eastern most longitude (in the range [-180, 180) )
 * westBoundLongitude : Western most longitude (in the range [-180, 180) )
 * crs : Coordinate reference system
 * }
 */
Ext.define('portal.util.BBox', {
    constructor : function(cfg) {
        Ext.apply(this, cfg);
        if (!this.crs) {
            this.crs = 'EPSG:4326';
        }

        this.callParent(arguments);
    },
    
    //VT: when longtitude goes over 180, geoserver process it the other way around the globe.
    statics : {
        datelineCorrection : function(eastBound,epsg){
            eastBound = parseInt(eastBound);
            if(eastBound < -120 && epsg=="EPSG:4326"){
                eastBound = 180 + (180 + eastBound);
            }
            return eastBound
        }
    },

    /**
     * Returns true if the bounding box spans the entire planet
     */
    isGlobal : function() {
        return this.eastBoundLongitude === 180 && this.northBoundLatitude === 90 &&
        this.southBoundLatitude === -90 && this.westBoundLongitude === -180;
    },

    /**
     * Returns a clone of this bounding box
     */
    clone : function() {
        return Ext.create('portal.util.BBox', {
            northBoundLatitude : this.northBoundLatitude,
            southBoundLatitude : this.southBoundLatitude,
            eastBoundLongitude : this.eastBoundLongitude,
            westBoundLongitude : this.westBoundLongitude,
            crs : this.crs
        });
    },

    /**
     * Combines this bounding box with the specified bbox by taking the maxima/minima of both bounding boxes.
     *
     * The 'super' bounding box will be returned as a BBox
     */
    combine : function(bbox) {
        return Ext.create('portal.util.BBox', {
            northBoundLatitude : Math.max(this.northBoundLatitude, bbox.northBoundLatitude),
            southBoundLatitude : Math.min(this.southBoundLatitude, bbox.southBoundLatitude),
            eastBoundLongitude : Math.max(this.eastBoundLongitude, bbox.eastBoundLongitude),
            westBoundLongitude : Math.min(this.westBoundLongitude, bbox.westBoundLongitude),
            crs : this.crs
        });
    },

    /**
     * Returns a list of BBox objects representing the bbox being split into 2 at the
     * specified latitude and longitudes (Will return 1,2 or 4 bbox objects)
     * @param longitude [Optional] The longitude to split at in the range [-180, 180)
     * @param latitude [Optional] The latitude to split at
     * @return
     */
    splitAt : function(longitude, latitude) {
        var splitter = function (left, right, value, splits) {
            var newSplits = [];

            for (var i = 0; i < splits.length; i++) {
                var bbox = splits[i];
                var leftSplit = bbox.clone();
                var rightSplit = bbox.clone();

                //If we split across a range that sees a sign flip
                //ensure the sign across each split rectangle is equal
                var leftSplitValue = value;
                while (leftSplitValue < 0 && leftSplit[left] > 0) {
                    leftSplitValue += 360;
                }
                while (leftSplitValue > 0 && leftSplit[left] < 0) {
                    leftSplitValue -= 360;
                }
                var rightSplitValue = value;
                while (rightSplitValue < 0 && rightSplit[right] > 0) {
                    rightSplitValue += 360;
                }
                while (rightSplitValue > 0 && rightSplit[right] < 0) {
                    rightSplitValue -= 360;
                }

                leftSplit[left] = bbox[left];
                leftSplit[right] = leftSplitValue;
                rightSplit[left] = rightSplitValue;
                rightSplit[right] = bbox[right];

                newSplits.push(leftSplit);
                newSplits.push(rightSplit);
            }

            return newSplits;
        };

        var splits = [this];

        if (longitude !== undefined) {
            splits = splitter('westBoundLongitude', 'eastBoundLongitude', longitude, splits);
        }

        if (latitude !== undefined) {
            splits = splitter('northBoundLatitude', 'southBoundLatitude', latitude, splits);
        }

        return splits;
    },

    /**
     * Recursively splits the specified bbox
     *
     * bbox : The bounding box to split
     * resultList : A list that the results will be appended to
     */
    _splitBboxes : function(bbox, resultList) {

        //SPLIT CASE 1: Polygon crossing meridian
        if (bbox.westBoundLongitude < 0 && bbox.eastBoundLongitude > 0) {
            var splits = bbox.splitAt(0);
            for (var i = 0; i < splits.length; i++) {
                this._splitBboxes(splits[i], resultList);
            }
            return resultList;
        }

        //SPLIT CASE 2: Polygon crossing anti meridian
        if (bbox.westBoundLongitude > 0 && bbox.eastBoundLongitude < 0) {
            var splits = bbox.splitAt(-180);
            for (var i = 0; i < splits.length; i++) {
                this._splitBboxes(splits[i], resultList);
            }
            return resultList;
        }

        //SPLIT CASE 3: Polygon is too wide (Gmap can't handle click events for wide polygons)
        //VT: There has been alot of work that have gone into calculating these bounding box and I am not sure why.
        //it may be due to some legacy map bug but I am unable to replicate it.

//        if (Math.abs(bbox.westBoundLongitude - bbox.eastBoundLongitude) > 60) {
//            var splits = bbox.splitAt((bbox.westBoundLongitude + bbox.eastBoundLongitude) / 2);
//            for (var i = 0; i < splits.length; i++) {
//                this._splitBboxes(splits[i], resultList);
//            }
//            return resultList;
//        }

        //OTHERWISE - bounding box is OK to render
        resultList.push(bbox);
        return resultList;
    },

    /**
     * Converts a portal bbox into an array of portal.map.primitives.Polygon objects as instantiated
     * by the specified portal.map.BaseMap
     *
     * Normally a single polygon is returned but if the polygon wraps around the antimeridian, it will be split
     * around the meridians.
     *
     * @param baseMap A portal.map.BaseMap instance
     * @param strokeColor String The color of the vertices (CSS color string)
     * @param strokeWeight Number The width of the vertices
     * @param strokeOpacity Number The transparency of the vertices [0, 1]
     * @param fillColor String The color of the fill (CSS color string)
     * @param fillOpacity Number The transparency of the fill [0, 1]
     * @param opts Object - additional GMap config options
     * @param id [Optional] String ID of generated polygons
     * @param cswRecord [Optional] portal.csw.CSWRecord parent of these polygons
     * @param sourceOnlineResource [Optional] portal.csw.OnlineResource parent of these polygons
     * @param sourceLayer [Optional] portal.layer.Layer parent of these polygons
     */
    toPolygon : function(baseMap, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity, opts,
                             id, cswRecord, sourceOnlineResource, sourceLayer) {
        var splits = this._splitBboxes(this, []);
        var result = [];

        for (var i = 0; i < splits.length; i++) {
            var splitBbox = splits[i];
            var ne = Ext.create('portal.map.Point', {latitude : splitBbox.northBoundLatitude, longitude : splitBbox.eastBoundLongitude});
            var se = Ext.create('portal.map.Point', {latitude : splitBbox.southBoundLatitude, longitude : splitBbox.eastBoundLongitude});
            var sw = Ext.create('portal.map.Point', {latitude : splitBbox.southBoundLatitude, longitude : splitBbox.westBoundLongitude});
            var nw = Ext.create('portal.map.Point', {latitude : splitBbox.northBoundLatitude, longitude : splitBbox.westBoundLongitude});

            result.push(baseMap.makePolygon(id, cswRecord, sourceOnlineResource, sourceLayer,
                    [sw, nw, ne, se, sw], strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity, opts));
        }

        return result;
    },

    /**
     * Returns true IFF both bboxes share the same CRS and that they intersect one another.
     * Algorithm sourced from - http://tekpool.wordpress.com/2006/10/11/rectangle-intersection-determine-if-two-given-rectangles-intersect-each-other-or-not/
     */
    intersects : function(bbox) {
        if (this.crs !== bbox.crs) {
            return false;
        }

        //If a bbox wraps the international date line such that east is in fact less than west
        //We should split the wrapping bbox at the dateline for an easier comparison
        var bboxEast = bbox.eastBoundLongitude;
        var bboxWest = bbox.westBoundLongitude;
        var thisEast = this.eastBoundLongitude;
        var thisWest = this.westBoundLongitude;
        
        if (bboxEast < bboxWest) {
            var left = Ext.create('portal.util.BBox',{
                westBoundLongitude : bboxWest, 
                eastBoundLongitude : 180, 
                southBoundLatitude : bbox.southBoundLatitude, 
                northBoundLatitude : bbox.northBoundLatitude
            });
            var right = Ext.create('portal.util.BBox',{
                westBoundLongitude : -180, 
                eastBoundLongitude : bboxEast, 
                southBoundLatitude : bbox.southBoundLatitude, 
                northBoundLatitude : bbox.northBoundLatitude
            });
            
            return this.intersects(left) || this.intersects(right);
        }
        if (thisEast < thisWest) {
            var left = Ext.create('portal.util.BBox',{
                westBoundLongitude : thisWest, 
                eastBoundLongitude : 180, 
                southBoundLatitude : this.southBoundLatitude, 
                northBoundLatitude : this.northBoundLatitude
            });
            var right = Ext.create('portal.util.BBox',{
                westBoundLongitude : -180, 
                eastBoundLongitude : thisEast, 
                southBoundLatitude : this.southBoundLatitude, 
                northBoundLatitude : this.northBoundLatitude
            });
            
            return left.intersects(bbox) || right.intersects(bbox);
        }

        return !(bboxWest > thisEast
                || bboxEast < thisWest
                || bbox.southBoundLatitude > this.northBoundLatitude
                || bbox.northBoundLatitude < this.southBoundLatitude);
    },

    /**
     * Returns true of the specified lat/long is contained by this BBox
     */
    contains : function(latitude, longitude) {
        return this.westBoundLongitude <= longitude &&
                this.eastBoundLongitude >= longitude &&
                this.southBoundLatitude <= latitude &&
                this.northBoundLatitude >= latitude;
    },

    /**
     * Returns true IFF this bounding box completely encloses the specified BBBox
     */
    containsBbox : function(bbox) {
        if (this.crs !== bbox.crs) {
            return false;
        }

        return (this.contains(bbox.northBoundLatitude, bbox.westBoundLongitude) &&
               this.contains(bbox.southBoundLatitude, bbox.eastBoundLongitude));
    },

    /**
     * Function for comparing 2 instances of BBox. If the internal fields are all exactly the same then
     * true will be returned, otherwise false.
     */
    equals : function(bbox) {
        return this.eastBoundLongitude === bbox.eastBoundLongitude &&
            this.westBoundLongitude === bbox.westBoundLongitude &&
            this.southBoundLatitude === bbox.southBoundLatitude &&
            this.northBoundLatitude === bbox.northBoundLatitude &&
            this.crs === bbox.crs;
    },

    /**
     * Covert this bounding box to a new transformation
     */
    transform : function(bbox,newCrs) {
        var bounds = new OpenLayers.Bounds(bbox.westBoundLongitude, bbox.southBoundLatitude, bbox.eastBoundLongitude, bbox.northBoundLatitude);
        bounds.transform(this.crs,newCrs);
        var newBbox = Ext.create('portal.util.BBox', {
            eastBoundLongitude : bounds.right,
            westBoundLongitude : bounds.left,
            northBoundLatitude : bounds.top,
            southBoundLatitude : bounds.bottom
        });
        newBbox.crs=newCrs;
        return newBbox;
    }
});
/**
 * An Ext.data.Types extension for portal.util.BBox
 *
 * See http://docs.sencha.com/ext-js/4-0/#!/api/Ext.data.Types
 */
Ext.define('portal.util.BBoxType', {
    singleton: true,
    requires: ['Ext.data.SortTypes',
               'Ext.data.Types']
}, function() {
    Ext.apply(portal.util.BBoxType, {
        convert: function(v, data) {
            if (Ext.isArray(v)) {
                var newArray = [];
                for (var i = 0; i < v.length; i++) {
                    newArray.push(this.convert(v[i]));
                }
                return newArray;
            } else if (v instanceof portal.util.BBox) {
                return v;
            } else if (Ext.isObject(v)) {
                return Ext.create('portal.util.BBox', v);
            }

            return null;
        },
        sortType: Ext.data.SortTypes.none,
        type: 'portal.util.BBox'
    });
});/**
 * Utility functions for downloading files
 */
Ext.define('portal.util.misc.BrowserWindowWithWarning', {

    statics : {
        windowsId : []
    },


    message : '',


    constructor : function(cfg) {
        if(typeof portal.util.misc.BrowserWindowWithWarning.windowsId[cfg.id]=='undefined'){
            portal.util.misc.BrowserWindowWithWarning.windowsId[cfg.id]=true;
        };
        this.id = cfg.id;
        this.message = cfg.message;
    },

    open : function(callback){
        this._warningMessageBox(this.id,callback);

    },

    _warningMessageBox : function(id,callback){
        var checkBoxid="BrowserWindowWithWarning_" + id;
        if(portal.util.misc.BrowserWindowWithWarning.windowsId[id]==true){
            Ext.MessageBox.show({
                title:    'Browse Catalogue',
                msg:      this.message + '<br><br><input type="checkbox" id="' + checkBoxid + '" value="true" checked/>Do not show this message again',
                buttons:  Ext.MessageBox.OK,
                fn: function(btn) {
                    if( btn == 'ok') {
                        if (Ext.get(checkBoxid).dom.checked == true){
                            portal.util.misc.BrowserWindowWithWarning.windowsId[id]=false;
                        }
                        callback()
                    }
                }
            });
        }else{
            callback();
        }
    }



});/**
 * A plugin for an Ext.grid.Panel class that adds dynamic
 * tooltips to individual cells.
 *
 * To use this plugin, assign the following fields to each of the grid's columns
 * {
 *  hasTip : Boolean - whether this column has a tip associated with it (default value false)
 *  tipRenderer : function(Object value, Ext.data.Model record, Ext.grid.Column column, Ext.tip.ToolTip tip) - should return a value to be rendered into a tool tip
 * }
 *
 * Original idea adapted from http://stackoverflow.com/questions/7539006/extjs4-set-tooltip-on-each-column-hover-in-gridpanel
 */
Ext.define('portal.widgets.grid.plugin.CellTips', {
    alias: 'plugin.celltips',

    /**
     * The Ext.grid.Panel this plugin will be applied to.
     */
    _grid : null,

    /**
     * The simple selector string used for discovering the row
     * that triggers a tooltip opening.
     */
    _rowSelector : 'tr.' + Ext.baseCSSPrefix + 'grid-row',

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    init: function(grid) {
        this._grid = grid;
        grid.getView().on('render', this._registerTips, this);
    },

    /**
     * Registers a tooltip to show based on a grid view. The shown tooltip will
     * be generated using _tipRenderer
     */
    _registerTips : function(view) {
        view.tip = Ext.create('Ext.tip.ToolTip', {
            // The overall target element.
            target: view.el,
            // Each grid row causes its own seperate show and hide.
            delegate: view.cellSelector,
            // Moving within the row should not hide the tip.
            trackMouse: true,
            // Render immediately so that tip.body can be referenced prior to the first show.
            renderTo: Ext.getBody(),
            // Allow tooltips to grow to their maximum
            maxWidth: 500,
            listeners: {
                // Change content dynamically depending on which element triggered the show.
                beforeshow: Ext.bind(this._tipRenderer, this, [view], true)
            }
        });
    },

    /**
     * Function for building the contents of a tooltip
     */
    _tipRenderer : function(tip, opt, view) {
        //Firstly we lookup the parent column
        var gridColums = view.getGridColumns();
        var colIndex = tip.triggerElement.cellIndex;
        var column = gridColums[tip.triggerElement.cellIndex];

        if (!column || !column.hasTip) {
            return false;
        }

        //Next we iterate through our parent nodes until we hit the containing tr
        var triggerElement = Ext.fly(tip.triggerElement);
        var parent = triggerElement.findParentNode(this._rowSelector, 20, true);
        if (!parent) {
            return false;
        }

        //We use the parent node to lookup the record and we
        //finally we pass along the 'useful' information to the tipRenderer
        var record = view.getRecord(parent);
        var value = record.get(column.dataIndex);
        if (column.tipRenderer) {
            tip.update(column.tipRenderer(value, record, column, tip));
        } else {
            tip.update(value);
        }

        return true;
     }
});/**
 * Sourced from http://www.learnsomethings.com/2011/09/30/extjs-4-clearable-combobox-ala-twintriggers-example/
 */
Ext.define('portal.widgets.field.ClearableComboBox', {
    extend : 'Ext.form.field.ComboBox',
    alias : 'widget.xcombo',
    triggerTip : 'Click to clear selection.',
    spObj : '',
    spForm : '',
    spExtraParam : '',
    qtip : 'Clearable Combo Box',
    trigger1Class : 'x-form-select-trigger',
    trigger2Class : 'x-form-clear-trigger',
    onRender : function(ct, position) {
        this.callParent(arguments);

        var id = this.getId();
        this.triggerConfig = {
            tag : 'div',
            cls : 'x-form-twin-triggers',
            style : 'display:block;width:46px;',
            cn : [ {
                tag : "img",
                style : Ext.isIE ? 'margin-left:-3;height:19px' : '',
                src : Ext.BLANK_IMAGE_URL,
                id : "trigger1" + id,
                name : "trigger1" + id,
                cls : "x-form-trigger " + this.trigger1Class
            }, {
                tag : "img",
                style : Ext.isIE ? 'margin-left:-6;height:19px' : '',
                src : Ext.BLANK_IMAGE_URL,
                id : "trigger2" + id,
                name : "trigger2" + id,
                cls : "x-form-trigger " + this.trigger2Class
            } ]
        };
        this.triggerEl.replaceWith(this.triggerConfig);
        this.triggerEl.on('mouseup', function(e) {

            if (e.target.name === "trigger1" + id) {
                this.onTriggerClick();
            } else if (e.target.name === "trigger2" + id) {
                this.reset();
                if (this.spObj !== '' && this.spExtraParam !== '') {
                    Ext.getCmp(this.spObj).store.setExtraParam(
                            this.spExtraParam, '');
                    Ext.getCmp(this.spObj).store.load();
                }
                if (this.spForm !== '') {
                    Ext.getCmp(this.spForm).getForm().reset();
                }

                //Raise select event on clear
                this.fireEvent('select', this, []);
            }
        }, this);
        var trigger1 = Ext.get("trigger1" + id);
        var trigger2 = Ext.get("trigger2" + id);
        trigger1.addClsOnOver('x-form-trigger-over');
        trigger2.addClsOnOver('x-form-trigger-over');
    },
    getSubmitData: function(includeEmptyText) {
        var me = this,
            data = null,
            val;
        if (!me.disabled && me.submitValue && !me.isFileUpload()) {
            val = me.getSubmitValue(includeEmptyText);
            if (val !== null) {
                data = {};
                data[me.getName()] = val;
            }
        }
        return data;
    },
    getSubmitValue : function(includeEmptyText) {
        var value = this.callParent(arguments);
        if (value === null && includeEmptyText && this.emptyText) {
            return this.emptyText;
        }
        return value;
    }
});
Ext.define('portal.widgets.field.clearabletextfield', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.clearabletextfield',
    initComponent: function () {  
        
        this.setTriggers({
            clearKey: {
                cls: 'x-form-clear-trigger',
                handler: function() {
                    this.setRawValue('');
                }
            }     
        });
                                       
        this.callParent();
       
    }       
});/**
 * Plugin to apply to Ext.Image components. Adds a "click" event
 * to the image by binding to the rendered DOM click event
 */
Ext.define('portal.widgets.plugins.ClickableImage', {
    extend : 'Ext.plugin.Abstract',
    alias : 'plugin.clickableimage',

    stopEvent : false,

    /**
     * stopEvent - Boolean - if true, the event will be stopped from propogating upwards. Defaults to false
     */
    constructor : function(config) {
        this.stopEvent = !!config.stopEvent;
    },

    init : function(cmp) {
        if (cmp.rendered) {
            this.installEvents(cmp);
        } else {
            cmp.on('afterrender', this.installEvents, this);
        }
    },

    installEvents : function(cmp) {
        var el = cmp.getEl();
        el.on('click', function(e, t, eOpts) {
            if (this.stopEvent) {
                e.stopEvent();
            }
            cmp.fireEvent('click', cmp, e);
        }, this);
        
        el.on('dblclick', function(e, t, eOpts) {
            if (this.stopEvent) {
                e.stopEvent();
            }
            cmp.fireEvent('dblclick', cmp, e);
        }, this);
    }
});
/**
 * A column (similar to the ExtJs Action Column) that allows
 * a grid to subscribe to specific click events for a column.
 *
 * What it also offers is the ability organise its own
 * custom renderers which makes it distinct from the ExtJS
 * Action Column.
 *
 * It is expected that classes define their own renderer when
 * using this column
 */
Ext.define('portal.widgets.grid.column.ClickColumn', {
    extend: 'Ext.grid.column.Column',
    alias: 'widget.clickcolumn',

    constructor: function() {
        this.callParent(arguments);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     */
    processEvent: function(type, view, cell, recordIndex, cellIndex, e) {
        var record = e.record;
        var storeRecordIndex = view.store.indexOf(record);        
        if (type == 'click' || (type == 'keydown' && (e.getKey() === e.ENTER || e.getKey() === e.SPACE))) {
            this.fireEvent('columnclick', this, record, storeRecordIndex, cellIndex,e);
            return this.callParent(arguments);
        } else if (type === 'dblclick') {
            return this.fireEvent('columndblclick', this, record, storeRecordIndex, cellIndex);
        } else {
            return this.callParent(arguments);
        }
    }
});

/**
 * A click control geared towards providing click events for geometry AND map clicks
 */
Ext.ns('portal.map.openlayers');
portal.map.openlayers.ClickControl = OpenLayers.Class(OpenLayers.Control, {
    defaultHandlerOptions: {
        'single': true,
        'double': false,
        'pixelTolerance': 0,
        'stopSingle': false,
        'stopDouble': false
    },

    /**
     * Property: scope
     * {Object} The scope to use with the onBeforeSelect, onSelect, onUnselect
     *     callbacks. If null the scope will be this control.
     */
    scope: null,

    /**
     * APIProperty: geometryTypes
     * {Array(String)} To restrict selecting to a limited set of geometry types,
     *     send a list of strings corresponding to the geometry class names.
     */
    geometryTypes: null,

    /**
     * Property: layer
     * {<OpenLayers.Layer.Vector>} The vector layer with a common renderer
     * root for all layers this control is configured with (if an array of
     * layers was passed to the constructor), or the vector layer the control
     * was configured with (if a single layer was passed to the constructor).
     */
    layer: null,

    /**
     * Property: layers
     * {Array(<OpenLayers.Layer.Vector>)} The layers this control will work on,
     * or null if the control was configured with a single layer
     */
    layers: null,

    /**
     * options:
     * {
     *  map - An instance of an OpenLayers map
     *  trigger - function(Feature, Event)
     * }
     */
    initialize: function(layers, options) {
        this.handlerOptions = OpenLayers.Util.extend(
            {}, this.defaultHandlerOptions
        );
        OpenLayers.Control.prototype.initialize.apply(
            this, [options]
        );
        if(this.scope === null) {
            this.scope = this;
        }
        this.initLayer(layers);

        var callbacks = {
            click: options.trigger
        };
        this.callbacks = OpenLayers.Util.extend(callbacks, this.callbacks);
        this.handlers = {
            feature: new portal.map.openlayers.FeatureWithLocationHandler(
                this, this.layer, this.callbacks,
                {geometryTypes: this.geometryTypes}
            )
        };
    },

    /**
     * Method: initLayer
     * Assign the layer property. If layers is an array, we need to use
     *     a RootContainer.
     *
     * Parameters:
     * layers - {<OpenLayers.Layer.Vector>}, or an array of vector layers.
     */
    initLayer: function(layers) {
        if(OpenLayers.Util.isArray(layers)) {
            this.layers = layers;
            this.layer = new OpenLayers.Layer.Vector.RootContainer(
                this.id + "_container", {
                    layers: layers
                }
            );
        } else {
            this.layer = layers;
        }
    },

    /**
     * Method: activate
     * Activates the control.
     *
     * Returns:
     * {Boolean} The control was effectively activated.
     */
    activate: function () {
        if (!this.active) {
            if(this.layers) {
                this.map.addLayer(this.layer);
            }
            this.handlers.feature.activate();
            if(this.box && this.handlers.box) {
                this.handlers.box.activate();
            }
        }
        return OpenLayers.Control.prototype.activate.apply(
            this, arguments
        );
    }
});/**
 * Static methods for converting clicks on a map into portal.layer.querier.QueryTarget
 * objects that can be tested against various layer's querier instances for more information
 */
Ext.define('portal.map.gmap.ClickController', {

    statics : {
        /**
         * Utility for turning a click on a feature into a single QueryTarget
         */
        _marker : function(marker, overlayLatLng) {

            var basePrim = marker._portalBasePrimitive;
            if (!basePrim) {
                return [];
            }

            var id = basePrim.getId();
            var onlineResource = basePrim.getOnlineResource();
            var layer = basePrim.getLayer();
            var cswRecord = basePrim.getCswRecord();

            return [Ext.create('portal.layer.querier.QueryTarget', {
                id : id,
                lat : overlayLatLng.lat(),
                lng : overlayLatLng.lng(),
                onlineResource : onlineResource,
                layer : layer,
                cswRecord : cswRecord,
                explicit : true
            })];
        },

        /**
         * Utility for turning a click onto a polygon into a set of overlapping QueryTargets
         */
        _polygon : function(polygon, latLng, overlayLatLng, layerStore) {
            var queryTargets = [];
            var point = overlayLatLng ? overlayLatLng : latLng;

            //Look for all polygons intesecting the clicked point
            for (var i = 0; i < layerStore.getCount(); i++) {
                var layer = layerStore.getAt(i);
                var renderer = layer.data.renderer;
                var primitiveManager = renderer.primitiveManager;

                //Do this by diving straight into every renderer's list of polygons
                for (var j = 0; j < primitiveManager.primitiveList.length; j++) {
                    var overlayToTest =  primitiveManager.primitiveList[j];
                    if (overlayToTest instanceof GPolygon &&
                        overlayToTest.Contains(point)) {

                        var basePrim = overlayToTest._portalBasePrimitive;

                        var id = basePrim.getId();
                        var onlineResource = basePrim.getOnlineResource();
                        var layer = basePrim.getLayer();
                        var cswRecord = basePrim.getCswRecord();

                        queryTargets.push(Ext.create('portal.layer.querier.QueryTarget', {
                            id : id,
                            lat : point.lat(),
                            lng : point.lng(),
                            onlineResource : onlineResource,
                            layer : layer,
                            cswRecord : cswRecord,
                            explicit : true
                        }));
                    }
                }
            }

            return queryTargets;
        },

        /**
         * Utility for turning a raw point on the map into a series of QueryTargets
         * for all WMS/WCS layers.
         *
         * They are not explicit clicks because they occur based on the bounding box of the
         * WMS
         */
        _nonExplicit : function(latlng, layerStore) {
            var queryTargets = [];
            //Iterate everything with WMS/WCS - no way around this :(
            for (var i = 0; i < layerStore.getCount(); i++) {
                var layer = layerStore.getAt(i);

                var cswRecords = layer.get('cswRecords');
                for(var j = 0; j < cswRecords.length; j++){
                    var cswRecord = cswRecords[j];

                    //ensure this click lies within this CSW record
                    var containsPoint = false;
                    var geoEls = cswRecord.get('geographicElements');
                    for (var k = 0; k < geoEls.length; k++) {
                        if (geoEls[k] instanceof portal.util.BBox &&
                            geoEls[k].contains(latlng.lat(), latlng.lng())) {
                            containsPoint = true;
                            break;
                        }
                    }

                    //If it doesn't, don't consider this point for examination
                    if (!containsPoint) {
                        continue;
                    }

                    //Finally we don't include WMS query targets if we
                    //have WCS queries for the same record
                    var allResources = cswRecord.get('onlineResources');
                    var wmsResources = portal.csw.OnlineResource.getFilteredFromArray(allResources, portal.csw.OnlineResource.WMS);
                    var wcsResources = portal.csw.OnlineResource.getFilteredFromArray(allResources, portal.csw.OnlineResource.WCS);
                    var resourcesToIterate = [];
                    if (wcsResources.length > 0) {
                        resourcesToIterate = wcsResources;
                    } else {
                        resourcesToIterate = wmsResources;
                    }

                    //Generate our query targets for WMS/WCS layers
                    for (var k = 0; k < resourcesToIterate.length; k++) {
                        var type = resourcesToIterate[k].get('type');
                        if (type === portal.csw.OnlineResource.WMS ||
                            type === portal.csw.OnlineResource.WCS) {
                            queryTargets.push(Ext.create('portal.layer.querier.QueryTarget', {
                                id : '',
                                lat : latlng.lat(),
                                lng : latlng.lng(),
                                cswRecord   : cswRecord,
                                onlineResource : resourcesToIterate[k],
                                layer : layer,
                                explicit : true
                            }));
                        }
                    }
                }
            }

            return queryTargets;
        },

        /**
         * Given a raw click on the map - workout exactly what layer / feature has been interacted
         * with. Return the results as an Array portal.layer.querier.QueryTarget objects
         *
         * This function will normally return a singleton QueryTarget HOWEVER certain circumstances
         * might dictate that multiple items are going to be queried (such as in the case of overlapping polygons).
         *
         * @param overlay An instance of GOverlay (gmap api) - can be null
         * @param latlng An instance of GLatLng (gmap api) - can be null - the actual location clicked
         * @param overlayLatLng An instance of GLatLng (gmap api) - can be null - the actual location of the overlay clicked
         * @param layerStore An instance of portal.layer.LayerStore containing layers to be examined.
         */
        generateQueryTargets : function(overlay, latlng, overlayLatlng, layerStore) {
            if (!overlay) {
                return portal.map.gmap.ClickController._nonExplicit(latlng, layerStore);
            } else if (overlay instanceof GMarker) {
                return portal.map.gmap.ClickController._marker(overlay, overlayLatlng);
            } else if (overlay instanceof GPolygon) {
                return portal.map.gmap.ClickController._polygon(overlay, latlng, overlayLatlng, layerStore);
            } else {
                return []; //unable to handle clicks on other geometry types
            }
        }
    }
});/**
 * A search field that performs local filtering on a store
 * instead of proxying external requests
 */
Ext.define('portal.widgets.field.ClientSearchField', {
    extend : 'Ext.ux.form.SearchField',
    alias : 'widget.clientsearchfield',

    wordListSplitString : ' ', //Will be used to split our searches into a series of words

    initComponent : function() {
        this.callParent(arguments);
        this.store.setRemoteFilter(false);
    },

    /**
     * Disables the text field, leaves any trigger buttons enabled
     */
    _setTextFieldDisabled : function(disabled) {
        var inputFieldEl = Ext.get(this.getInputId());
        inputFieldEl.dom.disabled = disabled;

        //Manual styling because we cannot Ext.Element.mask an input field (it accepts
        //no child nodes)
        if (disabled) {
            inputFieldEl.setStyle('background', '#E5E5E5');
            inputFieldEl.setStyle('color', '#666666');
        } else {
            inputFieldEl.setStyle('background', '#FFFFFF');
            inputFieldEl.setStyle('color', '#000000');
        }
    },

    onClearClick : function(){
        var me = this,
            store = me.store,
            proxy = store.getProxy(),
            val;

        if (this.hasSearch) {
            this.setValue('');

            this.store.clearFilter(false);

            this.hasSearch = false;
            me.triggerCell.item(0).setDisplayed(false);
            me.triggerCell.item(1).setDisplayed(true);

            this._setTextFieldDisabled(false);

            this.updateLayout();
        }
    },

    onSearchClick : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onClearClick();
            return;
        }

        this.store.clearFilter(false);
        //VT: http://www.sencha.com/forum/showthread.php?297797-Ext-5.1.107-Store.filterBy-does-not-pass-record-id-down-to-filterFn
        this.store.filterBy(Ext.bind(this.filterByWord, this, [v.split(this.wordListSplitString)], true));     
        
        //VT:Tracking
        portal.util.PiwikAnalytic.siteSearch(v,this.getId(),this.store.count());

        this.hasSearch = true;
        this.triggerCell.item(0).setDisplayed(true);
        this.updateLayout();
    },

    filterByWord : function(record, wordsToFind) {
        var wordList = record.get(this.fieldName).split(this.wordListSplitString);

        //Function for testing if a list of words contains a particular word (or prefix)
        var containsMatch = function(wordList, word) {
            for (var i = 0; i < wordList.length; i++) {
                var lowerMatchWord = Ext.String.trim(wordList[i].toLowerCase());
                var lowerWord = Ext.String.trim(word.toLowerCase());

                if (lowerMatchWord === lowerWord || lowerMatchWord.indexOf(lowerWord) === 0) {
                    return true;
                }
            }
            return false;
        };

        for (var i = 0; i < wordsToFind.length; i++) {
            if (!containsMatch(wordList, wordsToFind[i])) {
                return false;
            }
        }
        return true;
    },

    /**
     * text : The text to include in the box (to indicate that a custom filter has been run)
     * func : function(record, id) that should return true/false for each record it receives
     */
    runCustomFilter : function(text, func) {
        //Clear any existing filter
        this.onClearClick();

        this.hasSearch = true;
        this.setValue(text);

        this.store.filterBy(func);
        this.triggerCell.item(0).setDisplayed(true);
        this.triggerCell.item(1).setDisplayed(false);

        this._setTextFieldDisabled(true);
        //inputFieldEl.mask();

        //VT:Tracking
        portal.util.PiwikAnalytic.siteSearch(text,this.getId(),this.store.count());

        this.updateLayout();
    },
    
    /**
     * text : The text to include in the box (to indicate that a custom filter has been run)
     * func : function(record, id) that should return true/false for each record it receives
     */
    clearCustomFilter : function() {
        //Clear any existing filter
        this.onClearClick();

        this.hasSearch = false;
        this.setValue("");
      
        this.triggerCell.item(0).setDisplayed(false);
        this.triggerCell.item(1).setDisplayed(true);

        this._setTextFieldDisabled(false);
        //inputFieldEl.mask();


        this.updateLayout();
    }

});
/**
 * Collapsed accordian can be applied to any container using the accordian layout.
 * 
 * This will allow all accordian panels to be closed (default behaviour is that one is always open)
 */
Ext.define('portal.widgets.plugins.CollapsedAccordian', {
    extend : 'Ext.plugin.Abstract',
    alias : 'plugin.collapsedaccordian',

    statics : {
        HIDDEN_ID : 'collapsedtarget'
    },

    init : function(cmp) {
        cmp.insert(0, {
            xtype : 'panel',
            hidden : true,
            itemId : portal.widgets.plugins.CollapsedAccordian.HIDDEN_ID,
            collapsed : false
        });

        var cfg = Ext.apply({}, cmp.initialConfig);
        var layoutCfg = cfg.layout;
        if (Ext.isString(layoutCfg)) {
            layoutCfg = {};
        }

        layoutCfg.type = 'accordiondefault';
        layoutCfg.defaultId = portal.widgets.plugins.CollapsedAccordian.HIDDEN_ID;
        cmp.setLayout(layoutCfg)
    }
});
/**
 * An abstract base class to be extended.
 *
 * Represents a pseudo grid panel (see AUS-2685) for containing layers
 * that haven't yet been added to the map. Each row
 * will be grouped under a heading, contain links
 * to underlying data sources and have a spatial location
 * that can be viewed by the end user.
 *
 * This class is expected to be extended for usage within
 * the 'Registered Layers', 'Known Layers' and 'Custom Layers'
 * panels in the portal. Support for KnownLayers/CSWRecords and
 * other row types will be injected by implementing the abstract
 * functions of this class.
 * 
 * This is a super-classs; the sub-classes should define the columns of the Grid Panel
 *
 */
Ext.define('portal.widgets.panel.CommonBaseRecordPanel', {
    extend : 'portal.widgets.panel.recordpanel.RecordPanel',
    alias: 'widget.commonbaserecordpanel',
    browseCatalogueDNSMessage : false, //VT: Flags the do not show message when browse catalogue is clicked.
    map : null,
    menuFactory : null,
    onlineResourcePanelType : null,
    serviceInformationIcon : null,
    nagiosErrorIcon: null,
    mapExtentIcon : null,
    
    
    /**
     * Define listeners to combine with any passed in with the Options
     */
    listenersHere : {},

    constructor : function(cfg) {
        var me = this;
        me.map = cfg.map;
        me.menuFactory = cfg.menuFactory;
        me.onlineResourcePanelType = cfg.onlineResourcePanelType;
        me.serviceInformationIcon = cfg.serviceInformationIcon;
        me.nagiosErrorIcon = Ext.isEmpty(cfg.nagiosErrorIcon) ? 'portal-core/img/warning.png' : cfg.nagiosErrorIcon;
        me.mapExtentIcon = cfg.mapExtentIcon;
        me.listeners = Object.extend(me.listenersHere, cfg.listeners);

        me.callParent(arguments);
    },
    
    onDestroy : function() {
        me.callParent();
    },
    
    //-------- Abstract methods requiring implementation ---------

    /**
     * Abstract function - Should return a string based title
     * for a given record
     *
     * function(Ext.data.Model record)
     *
     * record - The record whose title should be extracted
     */
    getTitleForRecord : portal.util.UnimplementedFunction,

    /**
     * Abstract function - Should return an Array of portal.csw.OnlineResource
     * objects that make up the specified record. If no online resources exist
     * then an empty array can be returned
     *
     * function(Ext.data.Model record)
     *
     * record - The record whose underlying online resources should be extracted.
     */
    getOnlineResourcesForRecord : portal.util.UnimplementedFunction,

    /**
     * Abstract function - Should return an Array of portal.util.BBox
     * objects that represent the total spatial bounds of the record. If no
     * bounds exist then an empty array can be returned
     *
     * function(Ext.data.Model record)
     *
     * record - The record whose spatial bounds should be extracted.
     */
    getSpatialBoundsForRecord : portal.util.UnimplementedFunction,

    /**
     * Abstract function - Should return an Array of portal.csw.CSWRecord
     * objects that make up the specified record.
     *
     * function(Ext.data.Model record)
     *
     * record - The record whose underlying CSWRecords should be extracted.
     */
    getCSWRecordsForRecord : portal.util.UnimplementedFunction,

    //--------- Class Methods ---------

    /**
     * Generates an Ext.DomHelper.markup for the specified imageUrl
     * for usage as an image icon within this grid.
     */
    _generateHTMLIconMarkup : function(imageUrl) {
        return Ext.DomHelper.markup({
            tag : 'div',
            style : 'text-align:center;',
            children : [{
                tag : 'img',
                width : 16,
                height : 16,
                align: 'CENTER',
                src: imageUrl
            }]
        });
    },

    /**
     * Renderer used in Column definitions that will be done on subclasses.  Useful to define here.
     * 
     * Internal method, acts as a renderer function for rendering
     * the title of the record.
     *
     * http://docs.sencha.com/ext-js/4-0/#!/api/Ext.grid.column.Column-cfg-renderer
     */
    _titleRenderer : function(value, metaData, record, row, col, store, gridView) {
        return this.getTitleForRecord(record);
    },

    /**
     * Renderer used in Column definitions that will be done on subclasses.  Useful to define here.
     *
     * Internal method, acts as a renderer function for rendering
     * the service information of the record.
     *
     */
    _serviceInformationRenderer : function(value, record) {
        
        if(record.get('resourceProvider')=="kml"){
            return 'portal-core/img/kml.png';
        }
        
        var onlineResources = this.getOnlineResourcesForRecord(record);

        var serviceType = this._getServiceType(onlineResources);
        
        var containsDataService = serviceType.containsDataService;
        var containsImageService = serviceType.containsImageService;

        // default iconPath where there is no service info available
        var iconPath = 'portal-core/img/exclamation.png';

        if ((containsDataService || containsImageService) && 
            (record instanceof portal.knownlayer.KnownLayer) &&
            record.containsNagiosFailures()) {
            iconPath = this.nagiosErrorIcon;
        } else if (this.serviceInformationIcon && (containsDataService || containsImageService)) {
            // check whether the portal has overridden the icons
            iconPath = this.serviceInformationIcon;
        } else {        
            if (containsDataService) {            
                iconPath = 'portal-core/img/binary.png'; //a single data service will label the entire layer as a data layer
            } else if (containsImageService) {
                iconPath = 'portal-core/img/picture.png';
            }
        }
        
        return iconPath;
    },
    
    /**
     * Helper function.  Useful to define here for other methods and subclasses.
     */
    _getServiceType : function(onlineResources){
        var containsDataService = false;
        var containsImageService = false;
        
      //We classify resources as being data or image sources.
        for (var i = 0; i < onlineResources.length; i++) {
            switch(onlineResources[i].get('type')) {
            case portal.csw.OnlineResource.WFS:
            case portal.csw.OnlineResource.WCS:
            case portal.csw.OnlineResource.SOS:
            case portal.csw.OnlineResource.OPeNDAP:
            case portal.csw.OnlineResource.CSWService:
            case portal.csw.OnlineResource.IRIS:
            case portal.csw.OnlineResource.NCSS:
                containsDataService = true;
                break;
            case portal.csw.OnlineResource.WMS:
            case portal.csw.OnlineResource.WWW:
            case portal.csw.OnlineResource.FTP:
            case portal.csw.OnlineResource.CSW:
            case portal.csw.OnlineResource.UNSUPPORTED:
                containsImageService = true;
                break;
            }
        }
       
        return result = {
            containsDataService : containsDataService,
            containsImageService : containsImageService
        };
    },

    /**
     * Renderer used in Column definitions that will be done on subclasses.  Useful to define here.
     *
     * Internal method, acts as an renderer function for rendering
     * the spatial bounds column of the record.
     */
    _spatialBoundsRenderer : function(value, record) {
        var spatialBounds = this.getSpatialBoundsForRecord(record);

        if (spatialBounds.length > 0 || record.internalId == 'portal-InSar-reports') {                                   
            var icon = null;
            if (this.mapExtentIcon) {
                icon = this.mapExtentIcon;
            } else {
                icon = 'portal-core/img/magglass.gif';
            }
            return icon;
        }

        return '';
    },

    /**
     * Helper function.  Useful to define here for subclasses.
     *
     * Show a popup containing info about the services that 'power' this layer
     */
    _serviceInformationClickHandler : function(value, record) {
        var cswRecords = this.getCSWRecordsForRecord(record);
        if (!cswRecords || cswRecords.length === 0) {
            return;
        }

        var popup = Ext.create('portal.widgets.window.CSWRecordDescriptionWindow', {
            cswRecords : cswRecords,
            parentRecord : record,
            onlineResourcePanelType : this.onlineResourcePanelType
        });

        popup.show();
    },


    /**
     * Helper function.  Useful to define here for subclasses.
     *
     * On single click, show a highlight of all BBoxes
     */
    _spatialBoundsClickHandler : function(value, record) {
        var spatialBoundsArray;
        if (record.internalId == 'portal-InSar-reports') {
            spatialBoundsArray = this.getWholeGlobeBounds();
        } else {
            spatialBoundsArray = this.getSpatialBoundsForRecord(record);
        }
        var nonPointBounds = [];

        //No point showing a highlight for bboxes that are points
        for (var i = 0; i < spatialBoundsArray.length; i++) {
            var bbox = spatialBoundsArray[i];
            if (bbox.southBoundLatitude !== bbox.northBoundLatitude ||
                bbox.eastBoundLongitude !== bbox.westBoundLongitude) {

                //VT: Google map uses EPSG:3857 and its maximum latitude is only 85 degrees
                // anything more will stretch the transformation
                if(bbox.northBoundLatitude>85){
                    bbox.northBoundLatitude=85;
                }
                if(bbox.southBoundLatitude<-85){
                    bbox.southBoundLatitude=-85;
                }
                nonPointBounds.push(bbox);
            }
        }

        this.map.highlightBounds(nonPointBounds);
    },

    /**
     * Helper function.  Useful to define here for subclasses.
     *
     * Return the max bbox for insar layer as it is a dummy CSW.
     */
    getWholeGlobeBounds : function() {
        var bbox = new Array();
        bbox[0] = Ext.create('portal.util.BBox', {
            northBoundLatitude : 85,
            southBoundLatitude : -85,
            eastBoundLongitude : 180,
            westBoundLongitude : -180
        });
        return bbox;
    },

    /**
     * Helper function.  Useful to define here for subclasses.
     *
     * On double click, move the map so that specified bounds are visible
     */
    _spatialBoundsDoubleClickHandler : function(value, record) {
        var spatialBoundsArray;
        if (record.internalId == 'portal-InSar-reports') {
            spatialBoundsArray = this.getWholeGlobeBounds();
        } else {
            spatialBoundsArray = this.getSpatialBoundsForRecord(record);
        }

        if (spatialBoundsArray.length > 0) {
            var superBBox = spatialBoundsArray[0];

            for (var i = 1; i < spatialBoundsArray.length; i++) {
                superBBox = superBBox.combine(spatialBoundsArray[i]);
            }

            this.map.scrollToBounds(superBBox);
        }
    },

    /**
     * A renderer for generating the contents of the tooltip that shows when the
     * layer is loading
     */
    _loadingTipRenderer : function(value, record, tip) {
        var layer = record.get('layer');
        if(!layer){//VT:The layer has yet to be created.
            return 'No status has been recorded';
        }
        var renderer = layer.get('renderer');
        var update = function(renderStatus, keys) {
            tip.update(renderStatus.renderHtml());
        };

        //Update our tooltip as the underlying status changes
        renderer.renderStatus.on('change', update, this);
        tip.on('hide', function() {
            renderer.renderStatus.un('change', update); //ensure we remove the handler when the tip closes
        });

        return renderer.renderStatus.renderHtml();
    },
    
    _loadingClickHandler : function(value, record) {
        
        var layer = record.get('layer');
        
        var html = '<p>No Service recorded, Click on Add layer to map</p>';    
        
        if(layer){
            var renderer = layer.get('renderer');
            html =  renderer.renderStatus.renderHtml();
        }        
        var win = Ext.create('Ext.window.Window', {
            title: 'Service Loading Status',
            height: 200,
            width: 500,
            layout: 'fit',
            modal: true,
            items: {  // Let's put an empty grid in just to illustrate fit layout
                xtype: 'panel',
                autoScroll : true,                
                html : html
            }
        });
        
        win.show();
    }
});
// This file is for any global level workarounds for browser (in)compatibility
// issues. Ideally it should be limited to avoiding JS errors, not re-implementing
// functionality.
//

//The portal uses some console logging which is not defined in some browsers
//Workaround it by providing an empty logging interface
if (!window.console) {
    window.console = {
        log : function() {},
        warn : function() {}
    };
}

//Sourced from http://stackoverflow.com/questions/280634/endswith-in-javascript
if (!String.prototype.endsWith) {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}
/**
 * A Ext.Panel specialisation for allowing the user to browse
 * through the constraints of a set of CSWRecords
 */
Ext.define('portal.widgets.panel.CSWConstraintsPanel', {
    extend : 'Ext.tab.Panel',
    alias : 'widget.cswconstraintspanel',

    /**
     * Constructor for this class, accepts all configuration options that can be
     * specified for a Ext.Panel as well as the following values
     * {
     *  cswRecords : A single CSWRecord object or an array of CSWRecord objects.
     * }
     */
    constructor : function(cfg) {

        var cswRecords = cfg.cswRecords;
        if (!Ext.isArray(cswRecords)) {
            cswRecords = [cswRecords];
        }

        var tabConfigs = [];
        for (var i = 0; i < cswRecords.length; i++) {
            if (!cswRecords[i].hasConstraints()) {
                continue;
            }

            var constraints = cswRecords[i].get('constraints');

            //Each tab lays out the constraints one by one
            var constraintsAdded = 0;
            var html = '<table style="border:0px;">';
            for (var j = 0; j < constraints.length; j++) {
                var constraint = constraints[j];
                constraint = constraint.replace(/^\s\s*/, '').replace(/\s\s*$/, ''); //trim the string
                if (constraint.length === 0) {
                    continue;
                }

                constraintsAdded++;
                if (/^http:\/\//.test(constraint)) {
                    html += '<tr><td style="padding:5px;font-size:11px;"><a href="' + constraint + '" target="_blank">' + constraint + '</a></td></tr>';
                } else {
                    html += '<tr><td style="padding:5px;font-size:11px;">' + constraint + '</td></tr>';
                }
            }
            html += "</table>";

            if (constraintsAdded > 0) {
                tabConfigs.push({
                    title : cswRecords[i].get('name'),
                    items : [{
                        xtype : 'panel',
                        html : html
                    }]
                });
            }
        }


        if (tabConfigs.length === 0) {
            tabConfigs.push({
                title : 'No constraints',
                items : [{
                    xtype : 'label',
                    text : 'There have been no access constraints specified at the registry where this layer was sourced from. However, there may still be access constraints that this portal isn\'t aware of.'
                }]
            });
        }

        //Build our configuration object
        Ext.apply(cfg, {
            items : tabConfigs
        });

        this.callParent(arguments);

        //Hide the tab bar if we only have 1 tab
        this.on('afterrender', function(me) {
            if (tabConfigs.length <= 1) {
                me.getTabBar().hide();
            }
        });
    }
});/**
 * This is the panel for the form that pops up when you click on customize for the personal panel
 *
 */
Ext.define('portal.widgets.panel.CSWFilterFormPanel', {
    extend : 'Ext.form.Panel',
    alias: 'widget.cswfilterformpanel',


    keywordStore : null,
    keywordIDCounter : 0,
    spacerHeight : 22,
    keywordMatchTypeStore : null,
    miniMap : null,
    boxLayer : null,

    constructor : function(cfg){



        this.keywordStore = new Ext.data.Store({
            autoload: true,
            fields: ['keyword', 'count'],
            proxy : {
                type : 'ajax',
                url : 'getFilteredCSWKeywords.do',
                extraParams : {
                    cswServiceIds : []
                 },
                reader : {
                    type : 'json',
                    rootProperty : 'data'
                }
            }

        });


        this.keywordMatchTypeStore = Ext.create('Ext.data.Store', {
            fields: ['display', 'value'],
            data : [
                {"display":"Any", "value":"Any"},
                {"display":"All", "value":"All"}
            ]
        });





        Ext.apply(cfg, {
            xtype : 'form',
            id : 'personalpanelcswfilterform',
            width : 500,
            autoScroll :true,
            border : false,
            height : 520,
            items : [{
                     xtype:'tabpanel',
                     layout: 'fit',
                     items : [
                              this.getRegistryTab(),
                              this.getGeneralTab(),
                              this.getSpatialTab()
                         ]
            }]
        });


        this.callParent(arguments);


    },



    getGeneralTab : function() {
        var keywordsComponent = this;
        var generalTab = {
                title : 'General filter',
                layout : 'anchor',
                items : [{
                    xtype:'fieldset',
                    title : 'Match Type',
                    items:[{
                        xtype : 'combobox',
                        name : 'keywordMatchType',
                        queryMode:'local',
                        valueField:'value',
                        displayField:'display',
                        fieldLabel : 'Match Type',
                        store: this.keywordMatchTypeStore
                    },{
                        xtype : 'label',
                        html : '<font size="0.7" color="red">The keywords here are generated dynamically based on the NON CUSTOM registries that are ticked on the Registries Filter Tab</font>'

                    },{
                        xtype : 'fieldset',
                        itemId : 'cswfilterkeywordfieldsetitemid',
                        layout : 'column',
                        anchor : '100%',
                        border : false,
                        style : 'padding:5px 10px 0px 10px',
                        items : [{
                            columnWidth : 1,
                            border : false,
                            layout : 'anchor',
                            bodyStyle:'padding:0px 0 0px 2px',
                            items : []
                        },{
                            width : 25,
                            border : false,
                            bodyStyle:'padding:0px 0 0px 0px',
                            items : []
                        }, {
                            width : 25,
                            border : false,
                            bodyStyle:'padding:0px 0 0px 0px',
                            items : []
                        }]
                    }],
                    listeners : {
                        afterrender : function() {
                            keywordsComponent.handlerNewKeywordField();
                        }
                    }
                },{
                    xtype:'fieldset',
                    title : 'Match Text',
                    items:[{
                        xtype : 'textfield',
                        name : 'anyText',
                        fieldLabel : 'Match Any Text'
                    }]
                },{
                    xtype:'fieldset',
                    title : 'Text Search',
                    items:[{
                        xtype : 'textfield',
                        name : 'title',
                        fieldLabel : 'Search Title'
                    },{
                        xtype : 'textfield',
                        name : 'abstract',
                        fieldLabel : 'Search abstract'
                    }]
                }]
        };

        return generalTab;
    },





    getSpatialTab : function(){
        var cswFilterFormPanelMe = this;

        var spatialTab ={
                title : 'Spatial filter',
                layout : 'fit',
                autoScroll :false,
                items : [{
                    xtype : 'fieldset',
                    itemId : 'cswspatialfiltercoordfieldset',
                    title : 'Coordinates',
                    items : [{
                        xtype : 'textfield',
                        name : 'north',
                        itemId : 'north',
                        fieldLabel : 'North'
                    },{
                        xtype : 'textfield',
                        name : 'south',
                        itemId : 'south',
                        fieldLabel : 'South'
                    },{
                        xtype : 'textfield',
                        name : 'east',
                        itemId : 'east',
                        fieldLabel : 'East'
                    },{
                        xtype : 'textfield',
                        name : 'west',
                        itemId : 'west',
                        fieldLabel : 'West'
                    },{
                        xtype : 'button',
                        toggle : true,
                        text: 'Draw Bounds',
                        handler: function() {
                            var myMap = cswFilterFormPanelMe.miniMap;
                            for(var i in myMap.controls){
                                if(myMap.controls[i] instanceof OpenLayers.Control.DrawFeature){
                                    //VT : get a hold of the DrawFeature and toggle it.
                                    if(this.toggle == true){
                                        this.toggle = false;
                                        this.setText('Clear bounds')
                                        myMap.controls[i].activate();
                                    }else{
                                        this.toggle = true;
                                        this.setText('Draw Bounds')
                                        cswFilterFormPanelMe.boxLayer.removeAllFeatures();
                                        this.ownerCt.getComponent('north').setValue('');
                                        this.ownerCt.getComponent('south').setValue('');
                                        this.ownerCt.getComponent('east').setValue('');
                                        this.ownerCt.getComponent('west').setValue('');
                                        myMap.controls[i].deactivate();
                                    }

                                }
                            }
                        }

                    }]
                },{
                    xtype:'panel',
                    id: 'cswminimapselection',
                    width : 500,
                    height : 800,
                    html : "<div style='width:100%; height:100%' id='cswselection-mini-map'></div>",
                    listeners : {
                        afterrender : function() {
                            cswFilterFormPanelMe._getMap(this,'cswselection-mini-map');
                        }
                    }
                }]
        };

        return spatialTab
    },



    getRegistryTab : function(){
        var me = this;
        var registriesTab = {
                title : 'Registries Filter',
                xtype : 'panel',
                type: 'vbox',
                items:[{
                    xtype:'fieldset',
                    title:'Default Registries',
                    flex : 1,
                    items:[{
                        xtype: 'checkboxgroup',
                        name : 'cswServiceId',
                        id : 'registryTabCheckboxGroup',
                        fieldLabel: 'Registries',
                        // Arrange checkboxes into two columns, distributed vertically
                        columns: 1,
                        vertical: true,
                        listeners : {
                            change : function(scope,newValue, oldValue, eOpts ){
                                me.keywordStore.getProxy().extraParams = {
                                    cswServiceIds : scope.getValue().cswServiceId
                                };
                            },        
                            //VT: Stop it from adding the same url twice.
                            beforeadd : function(scope, component, index, eOpts){      
                                var addItem = true
                                scope.items.each(function(item,index,len){
                                    if(this.inputValue.serviceUrl && (this.inputValue.serviceUrl===component.inputValue.serviceUrl)){
                                        Ext.MessageBox.alert('Status', 'You are attempting to add a duplicated service URL');
                                        addItem = false;
                                        return false;
                                    }
                                })
                                return addItem;
                            }
                        }

                    }]

                },{
                    xtype:'fieldset',
                    title:'Add custom registry',
                    collapsible : true,
                    items:[{
                        xtype: 'form',
                        itemId : 'customRegistryFormID',
                        border: false,
                        flex : 1,
                        buttonAlign : 'left',
                        buttons:[{
                            xtype : 'button',
                            text : 'Manage Saved Registries',
                            handler : function(){
                                var CustomRegistryTreeGridPanel = new portal.widgets.panel.CustomRegistryTreeGrid();
                                Ext.create('Ext.window.Window', {
                                    title: 'Manage Saved Registries',
                                    height: 200,
                                    width: 600,
                                    layout: 'fit',
                                    modal: true,
                                    items: [CustomRegistryTreeGridPanel]
                                }).show();
                            }
                        },{
                            xtype : 'button',
                            text : 'Save Registry',
                            disabled : true,
                            itemId : 'cswFilterFormSaveRegistryButton',
                            tooltip : 'Add custom registry and save it to the cookies for future use.',
                            scope : this,
                            handler : function(){                                
                                //VT:_addFormToRegistry(true,false) true to add to cookies, false to not add to checkgroup
                                this._addFormToRegistry(true,false);
                            }
                        },{
                            xtype: 'tbfill'
                        },{
                            xtype : 'button',
                            text : 'Add Registry',
                            tooltip : 'Add custom registry to the registry list',
                            scope : this,
                            handler : function(){
                                this._addFormToRegistry(false,true);                                                             
                            }
                        }],
                        items:[{
                            xtype : 'textfield',
                            anchor:'100%',
                            itemId : 'cswFilterFormServiceURLTextField',
                            name : 'DNA_serviceUrl',
                            allowBlank: true,
                            fieldLabel : 'Service Url',
                            emptyText: 'CSW url in the format: http://test/gn/srv/eng/csw',
                            triggers: {
                                foo: {
                                    cls: 'x-form-clear-trigger',
                                    handler: function() {
                                        this.setRawValue('');
                                        me._setAllowRegistryAdd();
                                    }
                                }                       
                            }
                        }]
                    }]
                }]
        };

        var checkBoxItems = [];

        var cswServiceItemStore = new Ext.data.Store({
            model   : 'portal.widgets.model.CSWServices',
            proxy : {
                type : 'ajax',
                url : 'getCSWServices.do',
                reader : {
                    type : 'json',
                    rootProperty : 'data'
                }
            },
            listeners : {
                load  :  function(store, records, successful, eopts){
                    for (var i = 0; i < records.length; i++) {
                        var cswServiceItemRec = records[i];
                        checkBoxItems.push({
                            boxLabel : cswServiceItemRec.get('title'),
                            name : 'cswServiceId',
                            inputValue: cswServiceItemRec.get('id'),
                            checked : cswServiceItemRec.get('selectedByDefault')
                        });
                    }
                    var registry=Ext.getCmp('registryTabCheckboxGroup');
                    registry.add(checkBoxItems);

                    var cookieRegistries = me._getCustomRegistriesCookies();
                    me._addCookieToRegistry(cookieRegistries);

                    me.keywordStore.getProxy().extraParams = {
                        cswServiceIds : registry.getValue().cswServiceId
                    };
                }
            }

        });
        cswServiceItemStore.load();

        return registriesTab;
    },

    /**
     * Updates the visibility on all add/remove buttons
     */
    updateAddRemoveButtons : function() {
        var keywordFieldSet = Ext.getCmp('personalpanelcswfilterform').query('fieldset[itemId=cswfilterkeywordfieldsetitemid]')[0]

        var comboKeywordColumn = keywordFieldSet.items.items[0];
        var buttonRemoveColumn = keywordFieldSet.items.items[1];
        var buttonAddColumn = keywordFieldSet.items.items[2];

        var existingKeywordFields = comboKeywordColumn.items.getCount();

        for (var i = 0; i < existingKeywordFields; i++) {
            var addButton = buttonAddColumn.items.items[i];
            var removeButton = buttonRemoveColumn.items.items[i];

            //We can always remove so long as we have at least 1 keyword
            if (existingKeywordFields <= 1) {
                removeButton.hide();
            } else {
                removeButton.show();
            }
        }
    },


    /**
     * This function removes the buttons and keywords associated with button
     */
    handlerRemoveKeywordField : function(button, e) {
        var keywordFieldSet = Ext.getCmp('personalpanelcswfilterform').query('fieldset[itemId=cswfilterkeywordfieldsetitemid]')[0]

        var comboKeywordColumn = keywordFieldSet.items.items[0];
        var buttonRemoveColumn = keywordFieldSet.items.items[1];
        var buttonAddColumn = keywordFieldSet.items.items[2];

        //Figure out what component index we are attempting to remove
        var id = button.initialConfig.keywordIDCounter;
        var index = buttonRemoveColumn.items.findIndexBy(function(cmp) {
            return cmp === button;
        });

        //Remove that index from each column
        comboKeywordColumn.remove(comboKeywordColumn.getComponent(index));
        buttonRemoveColumn.remove(buttonRemoveColumn.getComponent(index));
        buttonAddColumn.remove(buttonAddColumn.getComponent(0)); //always remove spacers

        //Update our add/remove buttons
        this.updateAddRemoveButtons();
        keywordFieldSet.doLayout();
    },



    handlerNewKeywordField : function(button, e) {
        var keywordFieldSet = Ext.getCmp('personalpanelcswfilterform').query('fieldset[itemId=cswfilterkeywordfieldsetitemid]')[0]

        var comboKeywordColumn = keywordFieldSet.items.items[0];
        var buttonRemoveColumn = keywordFieldSet.items.items[1];
        var buttonAddColumn = keywordFieldSet.items.items[2];


        //Add our combo for selecting keywords
        comboKeywordColumn.add({
            xtype : 'combo',
            width : 380,
            name : 'keywords',
            queryMode : 'remote',
            typeAhead: true,
            style: {
                marginBottom: '0px'
            },
            typeAheadDelay : 500,
            forceSelection : false,
            //triggerAction : 'all',
            queryParam: 'keyword',
            valueField:'keyword',
            fieldLabel : 'keyword',
            store :    this.keywordStore,
            listeners: {
                beforequery: function(qe){
                    delete qe.combo.lastQuery;
                }
            },
            tpl: Ext.create('Ext.XTemplate',
                    '<tpl for=".">',
                        '<div class="x-boundlist-item">{keyword} - <b>({count})</b></div>',
                    '</tpl>'
                ),
                // template for the content inside text field
            displayTpl: Ext.create('Ext.XTemplate',
                    '<tpl for=".">',
                        '{keyword}',
                    '</tpl>'
                )
        });

        //We also need a button to remove this keyword field
        buttonRemoveColumn.add({
            xtype : 'button',
            iconCls : 'remove',
            width : 22,
            height : this.spacerHeight,
            scope : this,
            keywordIDCounter : this.keywordIDCounter,
            handler : this.handlerRemoveKeywordField
        });

        //Because our add button will always be at the bottom of the list
        //we need to pad preceding elements with spacers
        if (buttonAddColumn.items.getCount()===0) {
            buttonAddColumn.add({
                xtype : 'button',
                iconCls : 'add',
                width : 22,
                height : this.spacerHeight,
                scope : this,
                handler : this.handlerNewKeywordField
            });
        } else {
            buttonAddColumn.insert(0, {
                xtype : 'box',
                width : 22,
                height : this.spacerHeight
            })
        }

        this.keywordIDCounter++;
        this.updateAddRemoveButtons();
        keywordFieldSet.doLayout();

    },



    _getMap : function(container,divId){
        var containerId = divId;
        var map = new OpenLayers.Map(containerId, {
            projection: 'EPSG:3857',
            controls : [
                new OpenLayers.Control.Navigation(),
                new OpenLayers.Control.PanZoomBar({zoomStopHeight:8})
            ],
            layers: [
                     new OpenLayers.Layer.Google(
                             "Google Hybrid",
                             {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
                         ),
                     new OpenLayers.Layer.Google(
                         "Google Physical",
                         {type: google.maps.MapTypeId.TERRAIN}
                     ),
                     new OpenLayers.Layer.Google(
                         "Google Streets", // the default
                         {numZoomLevels: 20}
                     ),
                     new OpenLayers.Layer.Google(
                         "Google Satellite",
                         {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
                     )
                 ],
                 center: new OpenLayers.LonLat(133.3, -61)
                     // Google.v3 uses web mercator as projection, so we have to
                     // transform our coordinates
                     .transform('EPSG:4326', 'EPSG:3857'),
                 zoom: 3
        });



        this.boxLayer = new OpenLayers.Layer.Vector("Box layer");
        map.addLayer(this.boxLayer);


        var box= new OpenLayers.Control.DrawFeature(this.boxLayer,
                OpenLayers.Handler.RegularPolygon, {
                    handlerOptions: {
                        sides: 4,
                        irregular: true
                    }
                }
            )
        map.addControl(box);


      //We need to listen for when a feature is drawn and act accordingly
        box.events.register('featureadded', {}, Ext.bind(function(e,c){
            var ctrl = e.object;
            var feature = e.feature;

            //raise the data selection event
            var originalBounds = feature.geometry.getBounds();
            var bounds = originalBounds.transform('EPSG:3857','EPSG:4326').toArray();
            var spatialCoordFieldSet = c.ownerLayout.owner.getComponent('cswspatialfiltercoordfieldset');
            spatialCoordFieldSet.getComponent('north').setValue(bounds[3]);
            spatialCoordFieldSet.getComponent('south').setValue(bounds[1]);
            spatialCoordFieldSet.getComponent('east').setValue(bounds[2]);
            spatialCoordFieldSet.getComponent('west').setValue(bounds[0]);





            //Because click events are still 'caught' even if the click control is deactive, the click event
            //still gets fired. To work around this, add a tiny delay to when we reactivate click events
            var task = new Ext.util.DelayedTask(Ext.bind(function(ctrl){
                ctrl.deactivate();
            }, this, [ctrl]));
            task.delay(50);
        }, this,container,true));







        container.on('resize', function() {
            map.updateSize();
        }, this);

        this.miniMap = map;

    },

    /**
     * addToCookie if set to true will also add this registry to cookie
     */
    _addFormToRegistry : function(addToCookie, updateCheckGroup){

        var customRegistryForm = this.query('form[itemId=customRegistryFormID]')[0]

        portal.util.Ajax.request({
            url: 'getCSWGetCapabilities.do',
            scope : this,
            params: {
                cswServiceUrl: customRegistryForm.getValues().DNA_serviceUrl
            },
            callback : function(success, data) {
                //Check for errors
                //VT: title should be extracted from the response;
                if (success) {
                    //VT if title == null pop up box to ask user for title;
                    var title = data.title;


                    var registry=Ext.getCmp('registryTabCheckboxGroup');
                    var checkBoxItems = [];
                    var cswUrl = customRegistryForm.getValues().DNA_serviceUrl;

                    var registryEntity = {
                        id:'randomIdGen_' + Ext.id(),
                        title:title,
                        serviceUrl:cswUrl,
                        recordInformationUrl : this._covertCSWtoRecordInfoUrl(cswUrl)
                    }

                    checkBoxItems.push({
                        boxLabel : title,
                        name : 'cswServiceId',
                        inputValue: {
                            id: registryEntity.id,
                            title: registryEntity.title,
                            serviceUrl:registryEntity.serviceUrl,
                            recordInformationUrl : registryEntity.recordInformationUrl
                        },
                        checked : true
                    });

                    if(updateCheckGroup && updateCheckGroup){
                        registry.add(checkBoxItems);
                        this._setAllowRegistryAdd(true);
                    }

                    if(addToCookie && addToCookie==true){
                        if(!(this._saveToCookie(registryEntity))){
                            Ext.Msg.alert('WARNING', 'Only a maximum of 3 registry are allowed to be store locally due to space limitation. This record is not saved but will be added to the registries above. Click on "Manage Saved Registries" to delete');
                        }else{
                            Ext.Msg.alert('Status', 'Registry saved locally. Click on "Manage Saved Registries" to delete');
                        }
                    }

                }else{
                    Ext.Msg.alert('WARNING', 'Failure to connect to the registry. Check your URL and ensure it is in the right format e.g http://test/gn/srv/eng/csw');
                }

            }
        });
    },
    
    _setAllowRegistryAdd : function(allowAdd){
        var textfield = this.query('[itemId="cswFilterFormServiceURLTextField"]')[0]; 
        var button = this.query('[itemId="cswFilterFormSaveRegistryButton"]')[0] ;
        
        if(allowAdd){            
            button.enable();
            textfield.setEditable(false);
            //textfield.setFieldStyle('background-color: #E6E6E6; background-image: none; opacity: 0.8;');
            textfield.addCls('portal-ux-textfield-disabled');
        }else{
            button.disable();
            textfield.setEditable(true);
            textfield.removeCls('portal-ux-textfield-disabled');
        }
    },

    _covertCSWtoRecordInfoUrl:function(cswUrl){
        if(cswUrl.substring(cswUrl.length -3,cswUrl.length).toLowerCase()=='csw'){
            return cswUrl.slice(0,cswUrl.length - 3) + 'main.home';
        }
        return cswUrl;
    },

    _addCookieToRegistry : function(registry){

        if(!(registry instanceof Array)){
            registry=[registry]
        }
        var formRegistry=Ext.getCmp('registryTabCheckboxGroup');
        var checkBoxItems = [];
        for(var i=0;i<registry.length;i++){
            checkBoxItems.push({
                boxLabel : registry[i].title,
                name : 'cswServiceId',
                inputValue: {
                    id:registry[i].id,
                    title:registry[i].title,
                    serviceUrl:registry[i].serviceUrl,
                    recordInformationUrl : registry[i].recordInformationUrl
                },
                checked : false
            });
        }

        formRegistry.add(checkBoxItems);

    },

    _saveToCookie : function(registry){
        if(Ext.util.Cookies.get('Registries1')==null || Ext.util.Cookies.get('Registries1')=='null'){
            Ext.util.Cookies.set('Registries1',Ext.encode(registry))
            return true;
        }else if(Ext.util.Cookies.get('Registries2')==null || Ext.util.Cookies.get('Registries2')=='null'){
            Ext.util.Cookies.set('Registries2',Ext.encode(registry))
            return true;
        }else if(Ext.util.Cookies.get('Registries3')==null || Ext.util.Cookies.get('Registries3')=='null'){
            Ext.util.Cookies.set('Registries3',Ext.encode(registry))
            return true;
        };

        return false;

    },

    _getCustomRegistriesCookies : function(){
        var children=[];
        for(var i=1; i < 4 ; i++){
            var cookieRegistry = Ext.decode(Ext.util.Cookies.get('Registries' + i));
            if(cookieRegistry != null){
                var registry = {
                        id: cookieRegistry.id,
                        title: cookieRegistry.title,
                        serviceUrl:cookieRegistry.serviceUrl,
                        recordInformation:cookieRegistry.recordInformationUrl
                }
                children.push(registry);
            }
        }
        return children;

    }


});

Ext.define('portal.widgets.window.CSWFilterWindow', {
    extend : 'Ext.window.Window',

    cswFilterFormPanel : null,   
    
    constructor : function(cfg) {      
        
    	var me = this;
    	
    	var controlButtons = [];
    	
        // use the search panel defined in the config if present, otherwise use the Auscope Core default
    	me.cswFilterFormPanel = cfg.cswFilterFormPanel || new portal.widgets.panel.CSWFilterFormPanel({
            name : 'Filter Form'
        });
        
        // Only add a reset button if the search panel implements resetForm()
        if (me.cswFilterFormPanel.resetForm) {
        	controlButtons.push({
                xtype: 'button',
                text:'Reset Form',
                handler:function(button){
                    me.cswFilterFormPanel.resetForm();                        
                }
        	});
        };
        	
        controlButtons.push({            
            xtype: 'button',
            text: 'Search',
            scope : me,
            iconCls : 'add',
            handler: function(button) {
                var parent = button.findParentByType('window');
                var panel = parent.getComponent(0);

                if (panel.getForm().isValid()) {                 
                    var additionalParams = panel.getForm().getValues(false, false, false, false);
                    var filteredResultPanels=[];
                    for(additionalParamKey in additionalParams){
                        if(additionalParamKey == 'cswServiceId'){
                            if(!(additionalParams[additionalParamKey] instanceof Array)){
                                additionalParams[additionalParamKey]=[additionalParams[additionalParamKey]]
                            }
                            for(var j=0; j < additionalParams[additionalParamKey].length;j++){
                                //VT:
                                filteredResultPanels.push(this._getTabPanels(additionalParams,additionalParams[additionalParamKey][j]));
                            }
                        }
                    }
                    parent.fireEvent('filterselectcomplete',filteredResultPanels);
                    parent.hide();  
                    
                } else {
                    Ext.Msg.alert('Invalid Data', 'Please correct form errors.')
                }
            }
        });	
        
        Ext.apply(cfg, {
            title : 'Enter Parameters',
            layout : 'fit',
            modal : true,
            width : 500,
            items : [me.cswFilterFormPanel],
            buttons: controlButtons            
        });


        this.callParent(arguments);
    },
    
    /**
     * Return configuration for the tabpanels
     *
     * params - the parameter used to filter results for each tab panel
     * cswServiceId - The id of the csw registry.
     */
    _getTabPanels : function(params,cswServiceId) {
        //Convert our keys/values into a form the controller can read
        var keys = [];
        var values = [];
        var customRegistries=[];

        var additionalParams = params;

        //Utility function
        var denormaliseKvp = function(keyList, valueList, kvpObj) {
            if (kvpObj) {
                for (key in kvpObj) {
                    if (kvpObj[key]) {
                        var value = kvpObj[key].toString();
                        if(value.length>0 && key != 'cswServiceId' && !(key.slice(0, 4) == 'DNA_')){
                            keyList.push(key);
                            valueList.push(value);
                        }
                    }
                }
            }
        };


        denormaliseKvp(keys, values, additionalParams);
        if(typeof cswServiceId.id == 'undefined'){
            keys.push('cswServiceId');
            values.push(cswServiceId);
        }

      //Create our CSWRecord store (holds all CSWRecords not mapped by known layers)
        var filterCSWStore = Ext.create('Ext.data.Store', {
            model : 'portal.csw.CSWRecord',
            pageSize: 35,
            autoLoad: false,
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            proxy : {
                type : 'ajax',
                url : 'getFilteredCSWRecords.do',
                reader : {
                    type : 'json',
                    rootProperty : 'data',
                    successProperty: 'success',
                    totalProperty: 'totalResults'
                },
                extraParams: {
                    key : keys,
                    value : values,
                    customregistries : {
                        id: cswServiceId.id,
                        title: cswServiceId.title,
                        serviceUrl: cswServiceId.serviceUrl,
                        recordInformationUrl: cswServiceId.recordInformationUrl
                    }
                }

            }

        });

        var registriesArray = Ext.getCmp('registryTabCheckboxGroup').getChecked();
        var title = "Error retrieving title";
        for(var i = 0; i < registriesArray.length; i ++){
            if(registriesArray[i].inputValue === cswServiceId){
                title = registriesArray[i].boxLabel;
                break;
            }
        }


        var result={
                title : title,
                xtype: 'cswrecordpagingpanel',
                layout : 'fit',
                store : filterCSWStore
            };

        return result;

    },
    
    // overridden close method to obtain a reference to the search window and close it as well
    close: function() {      
        this.hide();
    }
});

/**
 * An implementation of portal.layer.legend.Legend for providing
 * simple GUI details on a CSW boxes and features added to the map
 */
Ext.define('portal.layer.legend.csw.CSWLegend', {
    extend: 'portal.layer.legend.Legend',

    iconUrl : '',
    
    polygonColorArr : [],

    /**
     * @param cfg an object in the form {
     *  iconUrl : String The URL of the marker icon for this layer
     *  polygonColor : String of the polygon colours '<fill colour>,<border colour>'
     * }
     */
    constructor : function(cfg) {
        this.iconUrl = cfg.iconUrl;
        if (cfg.polygonColor) this.polygonColorArr = cfg.polygonColor.split(',');
        this.callParent(arguments);
    },

    /**
     * Implemented function, see parent class
     */
    getLegendComponent : function(resources, filterer,response, isSld_body, callback) {
        var table = '<table><tr><td><img height="25" width="25" src="' + this.iconUrl +'"></td><td> Point</td></tr>';
        if (this.polygonColorArr.length>1) {
            table += '<tr><td><svg width="20" height="20"><rect width="20" height="20" style="fill:'+this.polygonColorArr[1]+';stroke-width:8;stroke:'+this.polygonColorArr[0]+';opacity:0.8;"/></svg></td><td> Geographical Bounding Box</td></tr>';
        }
        table += '</table>';
        
        var form = Ext.create('Ext.form.Panel',{
            title : 'CSW Resources',
            layout: 'fit',
            width: 250,
            html :  table
            });
        
        callback(this, resources, filterer, true, form); //this layer cannot generate a GUI popup
    },

    /**
     * Implemented function, see parent class
     */
    getLegendIconHtml : function(resources, filterer) {
        if (this.iconUrl && this.iconUrl.length > 0) {
            return Ext.DomHelper.markup({
                tag : 'div',
                style : 'text-align:center;',
                children : [{
                    tag : 'img',
                    width : 16,
                    height : 16,
                    align: 'CENTER',
                    src: this.iconUrl
                }]
            });
        } else {
            return '';
        }
    }
});/**
 * A Ext.Panel specialisation for allowing the user to browse
 * through the metadata within a single CSWRecord
 */
Ext.define('portal.widgets.panel.CSWMetadataPanel', {
    extend : 'Ext.form.Panel',
    alias : 'widget.cswmetadatapanel',

    cswRecord : null,

    // TODO: description
    extraItems : null,

    /**
     * Constructor for this class, accepts all configuration options that can be
     * specified for a Ext.Panel as well as the following values
     * {
     *  cswRecord : A single CSWRecord object
     * }
     */
    constructor : function(cfg) {
        this.cswRecord = cfg.cswRecord;

        var source = this.cswRecord.get('recordInfoUrl');
        var keywordsString = "";
        var keywords = this.cswRecord.get('descriptiveKeywords');
        for (var i = 0; i < keywords.length; i++) {
            keywordsString += keywords[i];
            if (i < (keywords.length - 1)) {
                keywordsString += ', ';
            }
        }

        //Build our configuration object
        Ext.apply(cfg, {
            layout : 'fit',
            items : [{
                xtype : 'fieldset',
                items : this._getMetadataItems(source,keywordsString)
            }]
        });

        this.callParent(arguments);
    },

    _getMetadataItems : function(source,keywordsString) {
      var items = [{
        xtype : 'displayfield',
        fieldLabel : 'Source',
        value : Ext.util.Format.format('<a target="_blank" href="{0}">Full metadata and downloads</a>', source)
      },{
        xtype : 'displayfield',
        fieldLabel : 'Title',
        anchor : '100%',
        value : this.cswRecord.get('name')
      }, {
        xtype : 'textarea',
        fieldLabel : 'Abstract',
        anchor : '100%',
        value : this.cswRecord.get('description'),
        readOnly : true
      },{
        xtype : 'displayfield',
        fieldLabel : 'Keywords',
        anchor : '100%',
        value : keywordsString
      },{
        xtype : 'displayfield',
        fieldLabel : 'Contact Org',
        anchor : '100%',
        value : this.cswRecord.get('contactOrg')
      }];
      if (this.cswRecord.get('constraints') != null && this.cswRecord.get('constraints').length > 0) {
    	  items.push({
    		  xtype : 'textarea',
    		  fieldLabel : 'Constraints',
    		  anchor : '100%',
    		  value : this.cswRecord.get('constraints'),
    		  readOnly : true
    	  });
      }
      items = items.concat(this.extraItems);
      if (this.cswRecord!=null) {
          items = items.concat({
                    fieldLabel : 'Resources',
                    xtype : 'onlineresourcepanel',
                    cswRecords : this.cswRecord
                });
      }
      return items;
    }
      
});
/**
 * Class for parsing a set of portal.csw.CSWRecord objects request/response
 * using the Querier interface
 */
Ext.define('portal.layer.querier.csw.CSWQuerier', {
    extend: 'portal.layer.querier.Querier',

    constructor: function(config){
        this.callParent(arguments);
    },

    /**
     * See parent class for definition
     */
    query : function(queryTarget, callback) {
        var cswRecord = queryTarget.get('cswRecord');
        if (!cswRecord) {
            callback(this, [], queryTarget);
            return;
        }

        var panel = Ext.create('portal.layer.querier.BaseComponent', {
            border : false,
            autoScroll : true,
            items : [{
                xtype : 'cswmetadatapanel',
                border : false,
                cswRecord : cswRecord
            }]
        });

        callback(this, [panel], queryTarget);
    }
});/**
 * CSWRecord is a simplified representation of a metadata record
 * from a catalogue service for the web (CSW)
 */
Ext.require(['portal.csw.OnlineResourceType',
             'portal.util.BBoxType',
             'portal.csw.CSWRecordType']);
Ext.define('portal.csw.CSWRecord', {
    extend: 'Ext.data.Model',
    requires: ['portal.csw.OnlineResourceType',
               'portal.util.BBoxType',
               'portal.csw.CSWRecordType'],
    fields: [
        { name: 'id', type: 'string' }, //Based on CSWRecord's file identifier
        { name: 'name', type: 'string' }, //Human readable name/title of this record
        { name: 'description', type: 'string' }, //Human readable description of this record (based on abstract)
        { name: 'adminArea', type: 'string' }, //The adminstrative area this record identifies itself as being a part of (organisation name that owns this record)
        { name: 'contactOrg', type: 'string' }, //Who is providing this resource (organisation name)
        { name: 'descriptiveKeywords', type: 'auto' }, //an array of strings representing descriptive keywords for this record
        { name: 'dataSetURIs', type: 'auto' }, //an array of strings representing URIs where file downloads may be obtained
        { name: 'geographicElements', convert: portal.util.BBoxType.convert}, //an array of portal.util.BBox objects representing the total spatial bounds of this record
        { name: 'onlineResources', convert: portal.csw.OnlineResourceType.convert}, //A set of portal.csw.OnlineResource objects
        { name: 'childRecords', convert: portal.csw.CSWRecordType.convert}, //an array of child portal.csw.CSWRecord objects
        { name: 'resourceProvider', type: 'string'}, //A set of portal.csw.OnlineResource objects
        { name: 'recordInfoUrl' , type:'string'},        
        { name: 'noCache' , type:'boolean'},
        { name: 'extensions', type:'auto'}, //A normally undefined object. CSWRecord can be extended by filling in this field.
        { name: 'constraints' , type:'auto'}, //An array of strings representing access constraints that will be shown to a user before this layer is used
        { name: 'date' , type:'date', convert: function(dateString) {
            if(dateString){
                return new Date(Date.parse(dateString.replace(' UTC', '')));
            }else{
                return dateString;
            }
        }},//The date of this CSWRecord
        { name: 'loading', type: 'boolean', defaultValue: false },//Whether this layer is currently loading data or not
        { name: 'layer', type: 'auto'}, // store the layer after it has been converted.        
        { name: 'active', type: 'boolean', defaultValue: false },//Whether this layer is current active on the map.
        { name: 'customlayer', type: 'boolean', defaultValue: false }, //If true, this layer is added from browse catalogue
        { name: 'service', type: 'boolean', defaultValue: false } //If true, this layer is a service layer that may contain layers in the getCapabilities
    ],

    /**
     * Returns a boolean indicating whether or not this record has any access constraints associated with it.
     *
     * Empty access constraints will not be counted
     */
    hasConstraints : function() {
        var constraints = this.get('constraints');
        for (var i = 0; i < constraints.length; i++) {
            var constraint = constraints[i].replace(/^\s\s*/, '').replace(/\s\s*$/, ''); //trim the string
            if (constraint.length > 0) {
                return true;
            }
        }

        return false;
    },

    /**
     * Function to return true if the keywords matches any of the filter parameter
     *
     * return boolean
     * str - str(String) can either be an array of string or a single string value filter parameter
     */
    containsKeywords : function(str) {

        var keywords = str;
        if(!Ext.isArray(str)) {
           keywords = [str];
        }

        for (var j=0;j<keywords.length; j++) {
            var descriptiveKeywords = this.get('descriptiveKeywords');
            for (var i=0; i<descriptiveKeywords.length; i++) {
                if(descriptiveKeywords[i] == keywords[j]) {
                    return true;
                }
            }

        }
        return false;
    },

    /**
     * Function for checking whether this record contains the specified portal.csw.OnlineResource
     *
     * Comparisons are made on a field/field basis
     *
     * @param onlineResource A portal.csw.OnlineResource
     *
     * returns boolean indicating
     */
    containsOnlineResource : function(onlineResource) {
        var comparator = function(or1, or2) {
            return ((or1.get('url') === or2.get('url')) &&
                   (or1.get('type') === or2.get('type')) &&
                   (or1.get('name') === or2.get('name')) &&
                   (or1.get('description') === or2.get('description')));
        };

        var resourcesToMatch = this.get('onlineResources');
        for (var i = 0; i < resourcesToMatch.length; i++) {
            if (comparator(resourcesToMatch[i], onlineResource)) {
                return true;
            }
        }

        return false;
    },
    
    containsOnlineResourceUrl : function(url) {
        
        var resourcesToMatch = this.getAllChildOnlineResources();
        for (var i = 0; i < resourcesToMatch.length; i++) {
            if (resourcesToMatch[i].get('url').toLowerCase()===url.toLowerCase()) {
                return true;
            }
        }

        return false;
    },

    /**
     * Iterates this CSWRecord and all child CSWRecords. Each record will have it's OnlineResource
     * array concatenated and the sum total of all concatenations will be returned.
     */
    getAllChildOnlineResources : function() {
        var onlineResources = this.get('onlineResources');
        var childRecs = this.get('childRecords');

        if (!onlineResources) {
            onlineResources = [];
        }

        if(childRecs){
            for (var i = 0; i < childRecs.length; i++) {
                onlineResources = onlineResources.concat(childRecs[i].getAllChildOnlineResources());
            }
        }

        return onlineResources;
    },

    /**
     * Iterates this CSWRecord and optionally all child CSWRecords. Every CSWRecord will be searched
     * for an online resource with the specified orId. Returns a portal.csw.OnlineResource object
     * with the specified ID or null
     *
     * @param orId The ID to search for
     * @param searchChildren If true, any child records will be searched for a matching OnlineResource
     */
    getOnlineResourceById : function(orId, searchChildren) {
        var onlineResources = this.get('onlineResources');
        var childRecs = this.get('childRecords');

        if(onlineResources) {
            for (var i = 0; i < onlineResources.length; i++) {
                if (onlineResources[i].get('id') === orId) {
                    return onlineResources[i];
                }
            }
        }

        if(searchChildren && childRecs) {
            for (var i = 0; i < childRecs.length; i++) {
                var matchingOr = childRecs[i].getOnlineResourceById(orId, searchChildren);
                if (matchingOr) {
                    return matchingOr;
                }
            }
        }

        return null;
    }

});/**
 * A window specialisation that is designed for showing copyright information
 * for one or more CSWRecords
 */
Ext.define('portal.widgets.window.CSWRecordConstraintsWindow', {
    extend : 'Ext.window.Window',

    /**
     *  cfg can contain all the elements for Ext.Window along with the following additions
     *
     *  cswRecords - a CSWRecord or Array of CSWRecords - these will generate the contents of this window.
     */
    constructor : function(cfg) {
        var cswRecords = cfg.cswRecords;

        //Set our default values (if they haven't been set)
        Ext.applyIf(cfg, {
            title: 'Copyright Information',
            autoDestroy : true,
            width       : 500,
            autoHeight : true,
            modal : true
        });

        //Set our 'always override' values
        Ext.apply(cfg, {
            autoScroll : true,
            layout : 'fit',
            items : [{
                xtype : 'cswconstraintspanel',
                autoScroll  : true,
                cswRecords : cswRecords
            }]
        });

        this.callParent(arguments);
    }
});/**
 * The CSWRecordDescriptionWindow is a class that specialises Ext.Window into displaying
 * detailed information about a list of CSWRecords
 */
Ext.define('portal.widgets.window.CSWRecordDescriptionWindow', {
    extend : 'Ext.window.Window',

    /**
     *  cfg can contain all the elements for Ext.Window along with the following additions
     *
     *  cswRecords - a CSWRecord or Array of CSWRecords - these will generate the contents of this window.
     *  parentRecord - the parent layer
     *  onlineResourcePanelType A specific subclass of online resource panel to use
     */
    constructor : function(cfg) {
        var cswRecords = cfg.cswRecords;
        var parentRecord = cfg.parentRecord;
        var onlineResourcePanelType = cfg.onlineResourcePanelType || 'onlineresourcepanel';        

        //Set our default values (if they haven't been set)
        Ext.applyIf(cfg, {
            title: 'Service Information',
            autoDestroy : true,
            width : 830,
            maxHeight : 400,
            minHeight : 100,
            modal : true
        });
        
        if(cswRecords[0].get('resourceProvider')=='kml'){
            
            Ext.apply(cfg, {
                autoScroll : true,
                html : '<p>This layer have been generated from a custom KML file</p>',
                listeners : {
                    resize : function(win, width, height) {
                        win.setSize(width, height);
                    }
                }
            });
            
        }else{
          //Set our 'always override' values
            Ext.apply(cfg, {
                autoScroll : true,
                items : [{
                    xtype : onlineResourcePanelType,
                    cswRecords : cswRecords,
                    parentRecord : parentRecord
                }],
                listeners : {
                    resize : function(win, width, height) {
                        win.setSize(width, height);
                    }
                }
            });
        }

        

        this.callParent(arguments);
    }
});
/**
 * A Ext.Window specialisation for allowing the user to browse
 * through the metadata within a single CSWRecord in a self contained window
 */
CSWRecordMetadataWindow = Ext.extend(Ext.Window, {

    cswRecord : null,

    /**
     * Constructor for this class, accepts all configuration options that can be
     * specified for a Ext.Window as well as the following values
     * {
     *  cswRecord : A single CSWRecord object
     * }
     */
    constructor : function(cfg) {

        this.cswRecord = cfg.cswRecord;

        //Ext JS 3 doesn't allow us to limit autoHeight panels
        //I believe there is a 'max height' element added in Ext JS 4
        var height = undefined;
        var autoHeight = true;
        if (this.cswRecord.getOnlineResources().length > 4) {
            height = 400;
            autoHeight = false;
        }

        //Build our configuration object
        Ext.apply(cfg, {
            layout : 'auto',
            modal : true,
            height : height,
            autoScroll : true,
            autoHeight : autoHeight,
            items : [{
                xtype : 'cswmetadatapanel',
                cswRecord : this.cswRecord,
                bodyStyle : {
                    'background-color' : '#ffffff'
                },
                hideBorders : true
            }]
        }, {
            width : 800,
            //height : 450,
            title : this.cswRecord.getServiceName()
        });

        //Call parent constructor
        CSWRecordMetadataWindow.superclass.constructor.call(this, cfg);
    }
});
/**
 * This is a generic paging panal for all CSW Records.
 */
Ext.define('portal.widgets.panel.CSWRecordPagingPanel', {
    extend : 'Ext.grid.Panel',
    alias: 'widget.cswrecordpagingpanel',

    cswRecordStore : null,
    pageSize: 15,

    constructor : function(cfg) {
        var me = this;

        this.cswRecordStore = cfg.store;


        Ext.apply(cfg, {
            hideHeaders : false,
            height: 200,
            layout : 'fit',
            width: 400,
            columns: [{
                header: 'Name',
                dataIndex: 'name',
                width:  390

            },{
                header: 'Administrative Area',
                dataIndex: 'adminArea',
                width: 110
            },{
                header:'Type',
                dataIndex:  'onlineResources',
                width: 100,
                renderer : function(value){
                    return this._serviceInformationRenderer(value);
                }
            }],
            store : this.cswRecordStore,
            viewConfig : {
                forceFit : true,
                enableRowBody:true
            },

            loadMask : {msg : 'Performing CSW Filter...'},
            multiSelect: true,
            dockedItems: [{
                xtype: 'pagingtoolbar',
                pageSize: this.pageSize,
                store: this.cswRecordStore,
                dock: 'bottom',
                displayInfo: true
            }],
            listeners : {
                afterrender : function(grid,eOpts) {
                    grid.cswRecordStore.load();
                },
                itemdblclick : function(grid, record, item, index, e, eOpts ){
                    this._callBackDisplayInfo(record);
                }

            }

        });

      this.callParent(arguments);
    },


    /**
     * Call back function to handle double click of the CSW to bring up a window to display its information
     */
    _callBackDisplayInfo : function(record){
        Ext.create('Ext.window.Window', {
            title : 'CSW Record Information',
            modal: true,
            items : [{
                xtype : 'cswmetadatapanel',
                width : 500,
                border : false,
                cswRecord : record
            }]
        }).show();
    },


    /**
     * Internal method, acts as an ExtJS 4 column renderer function for rendering
     * the service information of the record.
     *
     * http://docs.sencha.com/ext-js/4-0/#!/api/Ext.grid.column.Column-cfg-renderer
     */
    _serviceInformationRenderer : function(onlineResources) {

        var containsDataService = false;
        var containsImageService = false;

        //We classify resources as being data or image sources.
        for (var i = 0; i < onlineResources.length; i++) {
            switch(onlineResources[i].get('type')) {
            case portal.csw.OnlineResource.WFS:
            case portal.csw.OnlineResource.WCS:
            case portal.csw.OnlineResource.SOS:
            case portal.csw.OnlineResource.OPeNDAP:
            case portal.csw.OnlineResource.CSWService:
            case portal.csw.OnlineResource.IRIS:
                containsDataService = true;
                break;
            case portal.csw.OnlineResource.WMS:
            case portal.csw.OnlineResource.WWW:
            case portal.csw.OnlineResource.FTP:
            case portal.csw.OnlineResource.CSW:
            case portal.csw.OnlineResource.UNSUPPORTED:
                containsImageService = true;
                break;
            }
        }

        var iconPath = null;
        if (containsDataService) {
            iconPath = 'portal-core/img/binary.png'; //a single data service will label the entire layer as a data layer
        } else if (containsImageService) {
            iconPath = 'portal-core/img/picture.png';
        } else {
            iconPath = 'portal-core/img/cross.png';
        }

        return this._generateHTMLIconMarkup(iconPath);
    },

    /**
     * Generates an Ext.DomHelper.markup for the specified imageUrl
     * for usage as an image icon within this grid.
     */
    _generateHTMLIconMarkup : function(imageUrl) {
        return Ext.DomHelper.markup({
            tag : 'div',
            style : 'text-align:center;',
            children : [{
                tag : 'img',
                width : 16,
                height : 16,
                align: 'CENTER',
                src: imageUrl
            }]
        });
    }

});
/**
 * A specialisation of portal.widgets.panel.BaseRecordPanel for rendering
 * records conforming to the portal.csw.CSWRecord Model
 */
Ext.define('portal.widgets.panel.CSWRecordPanel', {
    alias: 'widget.cswrecordpanel',
    extend : 'portal.widgets.panel.BaseRecordPanel',

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    /**
     * Implements method - see parent class for details.
     */
    getTitleForRecord : function(record) {
        return record.data.name;
    },

    /**
     * Implements method - see parent class for details.
     */
    getOnlineResourcesForRecord : function(record) {
        return record.getAllChildOnlineResources();
    },

    /**
     * Implements method - see parent class for details.
     */
    getSpatialBoundsForRecord : function(record) {
        return record.data.geographicElements;
    },

    /**
     * Implements method - see parent class for details.
     */
    getCSWRecordsForRecord : function(record) {
        return [record];
    }

});/**
 * An Ext.data.Types extension for portal.csw.CSWRecord
 *
 * See http://docs.sencha.com/ext-js/4-0/#!/api/Ext.data.Types
 */
Ext.define('portal.csw.CSWRecordType', {
    singleton: true,
    requires: ['Ext.data.SortTypes',
               'Ext.data.Types']
}, function() {
    Ext.apply(portal.csw.CSWRecordType, {
        convert: function(v, data) {
            if (Ext.isArray(v)) {
                var newArray = [];
                for (var i = 0; i < v.length; i++) {
                    newArray.push(this.convert(v[i]));
                }
                return newArray;
            } else if (v instanceof portal.csw.CSWRecord) {
                return v;
            } else if (Ext.isObject(v)) {
                return Ext.create('portal.csw.CSWRecord', v);
            }
            return null;
        },
        sortType: Ext.data.SortTypes.none,
        type: 'portal.csw.OnlineResource'
    });
});
/**
 * An implementation of a portal.layer.Renderer for rendering generic Layers
 * that belong to a set of portal.csw.CSWRecord objects.
 */
Ext.define('portal.layer.renderer.csw.CSWRenderer', {
    extend: 'portal.layer.renderer.Renderer',
    config : {
        icon : null
    },

    polygonColor : null,

    constructor: function(config) {
        this.legend = Ext.create('portal.layer.legend.csw.CSWLegend', {
            iconUrl : config.icon ? config.icon.getUrl() : '',
            polygonColor : config.polygonColor ? config.polygonColor : this._getPolygonColor(null).join() /* Use local default color if not supplied in config */
        });
        this.callParent(arguments);
    }, 

    /**
     * A function for displaying generic data from a variety of data sources. This function will
     * raise the renderstarted and renderfinished events as appropriate. The effect of multiple calls
     * to this function (i.e. calling displayData again before renderfinished is raised) is undefined.
     *
     * This function will re-render itself entirely and thus may call removeData() during the normal
     * operation of this function
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success) callback
     *
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        this.removeData();
        var titleFilter = '';
        var keywordFilter = '';
        var resourceProviderFilter = '';
        var filterObj=null;
        if(filterer.getParameters()){
            filterObj=filterer.getParameters();
        }

        var regexp = /\*/;
        if(filterObj){
            titleFilter = filterObj.title;
            if(titleFilter !== '' && /^\w+/.test(titleFilter)) {
                regexp = new RegExp(titleFilter, "i");
            }
        }

        if(filterObj && filterObj.keyword) {
            keywordFilter = filterObj.keyword;
        }

        if(filterObj && filterObj.resourceProvider) {
            resourceProviderFilter = filterObj.resourceProvider;
        }

        this.fireEvent('renderstarted', this, resources, filterer);


        var cswRecords = this.parentLayer.get('cswRecords');
               
        var numRecords = 0;
        var primitives = [];
        for (var i = 0; i < cswRecords.length; i++) {
            if ((titleFilter === '' || regexp.test(cswRecords[i].get('name'))) &&
                    (keywordFilter === '' || cswRecords[i].containsKeywords(keywordFilter)) &&
                    (resourceProviderFilter === '' || cswRecords[i].get('resourceProvider') === resourceProviderFilter)) {
                numRecords++;
                var geoEls = cswRecords[i].get('geographicElements');
                
                for (var j = 0; j < geoEls.length; j++) {
                    var geoEl = geoEls[j];
                    if (geoEl instanceof portal.util.BBox) {
                        if(geoEl.eastBoundLongitude === geoEl.westBoundLongitude &&
                            geoEl.southBoundLatitude === geoEl.northBoundLatitude) {
                            //We only have a point
                            var point = Ext.create('portal.map.Point', {
                                latitude : geoEl.southBoundLatitude,
                                longitude : geoEl.eastBoundLongitude
                            });

                            primitives.push(this.map.makeMarker(cswRecords[i].get('id'), cswRecords[i].get('name'), cswRecords[i], undefined, this.parentLayer, point, this.icon));
                        } else { //polygon
                            var polygonList = geoEl.toPolygon(this.map, (this._getPolygonColor(this.polygonColor))[0], 4, 0.75,(this._getPolygonColor(this.polygonColor))[1], 0.4, undefined,
                                    cswRecords[i].get('id'), cswRecords[i], undefined, this.parentLayer);

                            for (var k = 0; k < polygonList.length; k++) {
                                primitives.push(polygonList[k]);
                            }
                        }
                    }
                }
            }
        }

        this.primitiveManager.addPrimitives(primitives);
        this.fireEvent('renderfinished', this);
    },


    _getPolygonColor : function(colorCSV){
        if(colorCSV && colorCSV.length > 0){
            var colorArray=colorCSV.split(",");
            return colorArray;
        }else{
            //default blue color used if no color is specified
            return ['#0003F9','#0055FE'];
        }
    },

    /**
     * An abstract function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return this.legend;
    },

    /**
     * An abstract function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : function() {
        this.primitiveManager.clearPrimitives();
    },

    /**
     * No point aborting a bbox rendering
     */
    abortDisplay : Ext.emptyFn
});
/**
 *
 * This is the paging panel used for inSar.
 *
 */
Ext.define('portal.widgets.panel.CSWReportPagingPanel', {
    extend : 'Ext.grid.Panel',
    alias: 'widget.cswpagingpanel',

    map : null,
    cswRecordStore : null,
    pageSize: 20,

    constructor : function(cfg) {
        var me = this;

        this.cswRecordStore = Ext.create('Ext.data.Store', {
            id:'CSWPagingStore',
            autoLoad: false,
            model : 'portal.csw.CSWRecord',
            pageSize: this.pageSize,
            proxy: {
                type: 'ajax',
                url: 'getUncachedCSWRecords.do',
                extraParams:cfg.cswConfig.extraParams,
                reader: {
                    type: 'json',
                    rootProperty: 'data',
                    successProperty: 'success',
                    totalProperty: 'totalResults'
                }
            }
        });


        Ext.apply(cfg, {
            hideHeaders : false,
            height: 200,
            layout : 'fit',
            width: 400,
            columns: [{
                header: 'Name',
                dataIndex: 'name',
                width:  390

            },{
                header: 'Administrative Area',
                dataIndex: 'adminArea',
                width: 110
            },{
                header:'Type',
                dataIndex:  'onlineResources',
                width: 100,
                renderer : function(value){
                    return '<div style="text-align:center"><img src="portal-core/img/picture.png" width="16" height="16" align="CENTER"/></div>';
                }
            }],
            store : this.cswRecordStore,
            viewConfig : {
                forceFit : true,
                enableRowBody:true
            },

            loadMask : {msg : 'Performing CSW Filter...'},
            multiSelect: true,
            dockedItems: [{
                xtype: 'pagingtoolbar',
                pageSize: this.pageSize,
                store: this.cswRecordStore,
                dock: 'bottom',
                displayInfo: true
            }],
            listeners : {
                afterrender : function(grid,eOpts) {
                    grid.cswRecordStore.load({
                        params:cfg.cswConfig.pagingConfig
                    });
                },
                itemdblclick : function(grid, record, item, index, e, eOpts ){
                    cfg.cswConfig.callback(record);
                }

            }

        });

      this.callParent(arguments);
    }
});/**
 * This is the window used to display the results from the csw filtering use by master catalogue.
 */

CSWSelectionWindow = Ext.extend(Ext.Window, {
    //this is the store of the personal panel.
    store : null,
    pageSize: 20,

	buttons : [{
	    xtype : 'button',
	    text : 'Add Selected Records',
	    iconCls : 'add',
	    scope : this,
	    handler : function(button, e) {
            var myParent = button.findParentByType('window');
	        var cswPagingPanel = myParent.getComponent('pagingRecordtabPanel').getActiveTab();
	        var csw = cswPagingPanel.getSelectionModel().getSelection();
	        myParent.fireEvent('selectioncomplete',csw);
	     }
	},{
	    xtype : 'button',
	    text : 'Add All Current Page Records',
	    iconCls : 'addall',
	    scope : this,
	    handler : function(button, e) {
            var myParent = button.findParentByType('window');
	        var cswPagingPanel = myParent.getComponent('pagingRecordtabPanel').getActiveTab();
	        var allStore = cswPagingPanel.getStore();
	        var csw = allStore.getRange();
	        myParent.fireEvent('selectioncomplete',csw);
	     }
	}],

    constructor : function(cfg) {      

        var me = this;
        
        //this is the store after filtering the registery.
        var recordStore = cfg.filterStore;
                        
        Ext.apply(cfg, {
            title: cfg.title,
            height: 500,
            width: 650,
            layout: 'fit',
            items: [{
                xtype:'tabpanel',
                itemId: 'pagingRecordtabPanel',
                layout: 'fit',
                items : cfg.resultpanels //VT: CSWRecordPagingPanel
            }],

            buttonAlign : 'right',
            modal : true

        });
        
        //Call parent constructor
        CSWSelectionWindow.superclass.constructor.call(this, cfg);

    }

});

Ext.define('portal.widgets.model.CSWServices', {
     extend: 'Ext.data.Model',
     fields: [
         {name: 'id', type: 'string'},
         {name: 'title',  type: 'string'},
         {name: 'url',       type: 'string'},
         {name: 'selectedByDefault',  type: 'string'}
     ]
 });Ext.define('portal.widgets.model.CustomRegistryModel', {
     extend: 'Ext.data.Model',

     fields: [
         {name: 'id', type: 'string'},
         {name: 'title',  type: 'string'},
         {name: 'serviceUrl',       type: 'string'},
         {name: 'recordInformationUrl',  type: 'string'},
         {name: 'active',  type: 'boolean'}
     ]

 });Ext.define('portal.widgets.panel.CustomRegistryTreeGrid', {
    extend: 'Ext.tree.Panel',

    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.ux.CheckColumn',
        'portal.widgets.model.CustomRegistryModel'
    ],
    xtype: 'tree-grid',


    title: 'Saved Registries',
    layout : 'fit',
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    singleExpand: true,

    constructor: function(cfg) {     

        Ext.apply(this, {
            itemId : 'customRegistryNavTree',
            store: new Ext.data.TreeStore({
                model: portal.widgets.model.CustomRegistryModel,
                proxy: {
                    type: 'memory'                         
                }                      
            }),
            selModel : Ext.create('Ext.selection.CheckboxModel', {}),
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                text: 'Title',
                flex: 1,
                sortable: true,
                dataIndex: 'title'
            },{
                text: 'CSW Endpoint',
                flex: 3,
                dataIndex: 'serviceUrl',
                sortable: true
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'bottom',
                items: [{
                    xtype:'button',
                    align : 'right',
                    text : 'Delete Selected',
                    scope: this,
                    handler : function(button,event){
                        var record = this.getSelectionModel().getSelection();
                        var checkboxgrp = Ext.getCmp('registryTabCheckboxGroup')
                        for(var i=0;i<record.length;i++){
                            var id = record[i].get('id');
                            this._deleteFromCookie(id);
                            this.getStore().remove(record[i]);

                            var checkBoxItems=checkboxgrp.items.items;
                            for(var j=0;j<checkBoxItems.length;j++){
                                if(checkBoxItems[j].inputValue.id && checkBoxItems[j].inputValue.id===id){
                                    checkboxgrp.remove(checkBoxItems[j]);
                                    break;
                                }
                            }

                        }
                    }
                }]
            }]
        });

        this.setRootNode(this._getRegistryFromCookie());
        this.callParent();
    },

    _getRegistryFromCookie : function(){

        var children=[];
        for(var i=1; i < 4 ; i++){
            var cookieRegistry = Ext.decode(Ext.util.Cookies.get('Registries' + i));
            if(cookieRegistry != null){
                var registry = {
                        id: cookieRegistry.id,
                        title: cookieRegistry.title,
                        serviceUrl:cookieRegistry.serviceUrl,
                        recordInformation:cookieRegistry.recordInformation,
                        active:false,
                        leaf: true, // this is a branch
                        expanded: false
                }
                children.push(registry);
            }
        }

        var data = {
           success : true,
           children : [{
               id : 'localRegistry',
               title : 'Registries',
               serviceUrl : '',
               recordInformation : '',
               active : false,
               leaf : false, // this is a branch
               expanded : true,
               children : children
           }]
       };
              

       return data;

    },

    _deleteFromCookie : function(id){
        for(var i=1; i < 4 ; i++){
            var registry = 'Registries' + i;

            var cookieRegistry = Ext.decode(Ext.util.Cookies.get('Registries' + i));
            if(cookieRegistry != null){
                if(cookieRegistry.id==id){
                    Ext.util.Cookies.clear(registry);
                }
            }
        }
    }
});/**
 * A display field extension that makes the data component
 * of the field the most prominent (compared to the actual label).
 *
 * This field is dependent on the css defined in portal-ux.css
 *
 * Also has support for rendering units of measure.
 */
Ext.define('portal.widgets.field.DataDisplayField', {
    alias : 'widget.datadisplayfield',
    extend : 'Ext.form.field.Display',

    /**
     * This is hardcoded, the label will appear in the 'display' part of the field.
     */
    hideLabel : true,
    labelStyle: 'display:none;', //Extjs 5.1 doesn't seem to honor the above. This is the nuclear alternative

    fieldCls: Ext.baseCSSPrefix + 'form-value-field',
    labelCls: Ext.baseCSSPrefix + 'form-value-field-label',
    uomCls : Ext.baseCSSPrefix + 'form-value-field-uom',

    /**
     * A unit of measure that is rendered alongside the actual value. Will be omitted if null/undefined
     */
    uom : null,
    
    /**
     * A plain tool tip for the unit of measure attribute
     */
    uomTip : null,

    /**
     * Style configuration to apply to the uom portion of the field
     */
    uomStyle : null,

    /**
     * This is set in the constructor
     */
    fieldSubTpl : null,

    constructor : function(config) {
        this.uom = config.uom ? config.uom : '';
        this.uomStyle = config.uomStyle ? config.uomStyle : {};
        this.uomCls = config.uomCls ? config.uomCls : this.uomCls;
        this.uomTip = config.uomTip ? config.uomTip : '';
        this.fieldLabel = config.fieldLabel ? config.fieldLabel : '';

        //This template defines the rendering of the data display field (and uom if appropriate)
        //We need to inject the uom values into the template via definitions
        this.fieldSubTpl = [
          '<div id="{id}"',
          '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
          ' class="{fieldCls}">{value}',
          '<tpl if="[uom]"> <span title="{[uomTip]}" class="{[uomCls]}" style="{[uomStyle]}">{[uom]}</span></tpl>',
          '</div>',
          '<div class="x-form-value-field-label">',
          '{[fieldLabel]}',
          '</div>',
          {
              compiled: true,
              disableFormats: true,
              //This is where we 'inject' our own variables for use within the template
              definitions : [
                  this.createDefinition('uom', this.uom),
                  this.createDefinition('uomStyle', this.uomStyle),
                  this.createDefinition('uomCls', this.uomCls),
                  this.createDefinition('uomTip', this.uomTip),
                  this.createDefinition('fieldLabel', this.fieldLabel)
              ]
        }];

        this.callParent(arguments);
    },

    /**
     * Utility function for generating javascript in the form
     * 'var variableName = value'
     */
    createDefinition : function(variableName, value) {
        if (Ext.isString(value)) {
            return Ext.util.Format.format('var {0} = "{1}";', variableName, value);
        } else if (Ext.isObject(value)) {
            //Assume an object is a style object
            return this.createDefinition(variableName, Ext.DomHelper.generateStyles(value));
        } else {
            return Ext.util.Format.format('var {0} = {1};', variableName, value);
        }
    }
});


/**
 * Class for turning the contents of the MapStateSerializer into
 * something the AuScope portal can utilise i.e. layers.
 *
 * Adds the following events
 *      deserializedlayer(portal.util.permalink.DeserializationHandler this, portal.layer.Layer newLayer)
 */
Ext.define('portal.util.permalink.DeserializationHandler', {
    extend : 'Ext.util.Observable',


    mapStateSerializer : null,
    knownLayerStore : null,
    layerFactory : null,
    cswRecordStore : null,
    map : null,
    stateString : null,
    stateVersion : null,

    /**
     * Creates a new instance of this class with the following config {
     *  mapStateSerializer : [Optional] The portal.util.permalink.MapStateSerializer to load from (if empty, it will be decoded from window.location)
     *  knownLayerStore : [Optional] A Ext.data.Store containing portal.knownlayer.KnownLayer models
     *  cswRecordStore : [Optional] A Ext.data.Store containing portal.csw.CSWRecord models
     *  layerFactory : A portal.layer.LayerFactory which will be used to create layers
     *  map : A portal.util.gmap.GMapWrapper instance
     *  stateString : the raw state string
     *  stateVersion : [Optional] the serialisation version used to encode stateString. If omitted it will be guessed
     * }
     */
    constructor : function(cfg) {
       

        Ext.apply(this, cfg);

        this.callParent(arguments);
 
        //Ensure our deserialisation occurs now (if appropriate) or when our datastores finish loading
        if (this.knownLayerStore) {
            this.knownLayerStore.on('load', this._deserializeIfReady, this, {single : true});
        }
        if (this.cswRecordStore) {
            this.cswRecordStore.on('load', this._deserializeIfReady, this, {single : true});
        }
        this._deserializeIfReady();
    },

    _deserializeIfReady : function() {
        //Ensure both stores are loaded before proceeding
        if ((this.knownLayerStore && this.knownLayerStore.getCount() === 0) ||
            (this.cswRecordStore && this.cswRecordStore.getCount() === 0)) {
            return;
        }

        //Prepare our map state serializer (if necessary)
        if (!this.mapStateSerializer) {
            if (this.stateString) {
                //IE will truncate our URL at 2048 characters which destroys our state string.
                //Let's warn the user if we suspect this to have occurred
                if (Ext.isIE && window.location.href.length === 2047) {
                    Ext.MessageBox.show({
                        title : 'Mangled Permanent Link',
                        icon : Ext.window.MessageBox.WARNING,
                        msg : 'The web browser you are using (Internet Explorer) has likely truncated the permanent link you are using which will probably render it unuseable. This portal will attempt to restore the saved state anyway.',
                        buttons : Ext.window.MessageBox.OK,
                        multiline : false
                    });
                }

                this.mapStateSerializer = Ext.create('portal.util.permalink.MapStateSerializer');
            } else {
                return;
            }
        }

        this.mapStateSerializer.deserialize(this.stateString, this.stateVersion, Ext.bind(function() {
            this._deserialize();
        }, this));
    },

    /**
     * Configures the specified layer with set parameters
     */
    _configureLayer : function(layer, filterParams, visible) {
        var renderer = layer.get('renderer');
        var filterer = layer.get('filterer');
        var filterForm = layer.get('filterForm');

        //Turn off any events before configuring        
        renderer.suspendEvents(false);
        filterer.suspendEvents(false);

        //Configure our layer/dependencies
        layer.set('deserialized', true);       
        if (filterParams) {
            filterer.setParameters(filterParams);
        }

        //Turn back on events before proceeding (skipping this will break the portal)        
        renderer.resumeEvents();
        filterer.resumeEvents();

        //Configure the filter form (either now or very soon after it loads its internal stores)
        if (filterParams) {
            filterForm.on('formloaded', Ext.bind(function(filterForm, filterer) {
                filterForm.readFromFilterer(filterer);
            }, this, [filterForm, filterer], false));
            if (filterForm.getIsFormLoaded()) {
                filterForm.readFromFilterer(filterer);
            }
        }
    },


    /**
     * Returns the first CSWRecord to contain all specified online resource objects
     */
    _findCSWRecordsByOnlineResources : function(onlineResources) {
        for (var i = 0; i < this.cswRecordStore.getCount(); i++) {
            var cswRecord = this.cswRecordStore.getAt(i);

            var matches = false;
            for (var j = 0; j < onlineResources.length && !matches; j++) {
                matches = cswRecord.containsOnlineResource(onlineResources[j]);
            }

            if (matches) {
                return cswRecord;
            }
        }

        return null;
    },

    _deserialize : function() {
        var featureLayers = this._getLayersToAdd();

        if (featureLayers.length < this.mapStateSerializer.serializedLayers.length) {
            Ext.MessageBox.show({
                title : 'Missing Layers',
                icon : Ext.MessageBox.WARNING,
                buttons : Ext.MessageBox.OK,
                msg : 'Some of the saved layers no longer exist and will be ignored. The remaining layers will load normally.',
                multiline : false
            });
        }

        //Add the layers to the internal store
        ActiveLayerManager.addLayers(featureLayers);
    },

    _getLayersToAdd : function() {
        var s = this.mapStateSerializer;
        var missingLayers = false;

        //Update our map location to the specified bounds
        this.map.setZoom(s.mapState.zoom);
        var centerPoint = Ext.create('portal.map.Point', {latitude : s.mapState.center.lat, longitude : s.mapState.center.lng});
        this.map.setCenter(centerPoint);

        // array of layers that we will want to add to the layer store
        var layersToAdd = [];

        //Add the layers, attempt to load whatever layers are available
        //but warn the user if some layers no longer exist
        for (var i = 0; i < s.serializedLayers.length; i++) {
            var serializedLayer = s.serializedLayers[i];
            if (serializedLayer.source === portal.layer.Layer.KNOWN_LAYER) {
                var id = serializedLayer.id;
                if (!id) {
                    continue;
                }

                var knownLayer = this.knownLayerStore.getById(id);
                if (!knownLayer) {
                    missingLayers = true;
                    continue;
                }

                //Create our new layer
                var newLayer = this.layerFactory.generateLayerFromKnownLayer(knownLayer);
                
                knownLayer.set('layer', newLayer);

                //Configure it
                this._configureLayer(newLayer, serializedLayer.filter, serializedLayer.visible);
                layersToAdd.push(newLayer);

            } else if (serializedLayer.source === portal.layer.Layer.CSW_RECORD) {

                //Turn our serialized online resources into 'actual' online resources
                var onlineResources = [];
                for (var j = 0; j < serializedLayer.onlineResources.length; j++) {
                    onlineResources.push(Ext.create('portal.csw.OnlineResource', {
                        name : serializedLayer.onlineResources[j].name,
                        type : serializedLayer.onlineResources[j].type,
                        description : serializedLayer.onlineResources[j].description,
                        url : serializedLayer.onlineResources[j].url
                    }));
                }

                //Perform a 'best effort' to find a matching CSWRecord
                var cswRecord = this._findCSWRecordsByOnlineResources(onlineResources);
                if (!cswRecord) {
                    missingLayers = true;
                    continue;
                }

                var newLayer = this.layerFactory.generateLayerFromCSWRecord(cswRecord);
              
                cswRecord.set('layer', newLayer);
                
                //Configure it
                this._configureLayer(newLayer, serializedLayer.filter, serializedLayer.visible);
                layersToAdd.push(newLayer);
                
                if(serializedLayer.customlayer){
                    cswRecord.set('customlayer', true);
                    var tabpanel =  Ext.getCmp('auscope-tabs-panel');
                    var customPanel = tabpanel.getComponent('org-auscope-custom-record-panel');
                    tabpanel.setActiveTab(customPanel);                                       
                    customPanel.getStore().insert(0,cswRecord);                    
                }                
            } else if (serializedLayer.source === 'search') {
                //Configure it
                this._configureLayer(serializedLayer, serializedLayer.filter, serializedLayer.visible);
                layersToAdd.push(newLayer);
            }
        }

        return layersToAdd;
    }
});
/**
 * An implementation of a portal.layer.Renderer for rendering WMS Layers that belong to a set of portal.csw.CSWRecord
 * objects. This extension is for Disjuncted ("OR") Layers of Layers defined through a WFSSelectors KnownLayerSelector
 * (in auscope-known-layers.xml) and was developed by GA in GPT-40.
 */
Ext.define('portal.layer.renderer.wms.DisjunctionLayerRenderer', {
    extend : 'portal.layer.renderer.Renderer',

    constructor : function(config) {
        this.legend = Ext.create('portal.layer.legend.wms.WMSLegend', {
            iconUrl : config.iconCfg ? config.iconCfg.url : 'portal-core/img/key.png'
        });
        this.callParent(arguments);
    },

    /**
     * A function for displaying layered data from a variety of data sources. This function will raise the renderstarted
     * and renderfinished events as appropriate. The effect of multiple calls to this function (ie calling displayData
     * again before renderfinished is raised) is undefined.
     * 
     * This function will re-render itself entirely and thus may call removeData() during the normal operation of this
     * function
     * 
     * function(portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer,
     * function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources,
     * portal.layer.filterer.Filterer filterer, bool success) callback
     * 
     * returns - void
     * 
     * resources - an array of data sources which should be used to render data filterer - A custom filter that can be
     * applied to the specified data sources callback - Will be called when the rendering process is completed and
     * passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        this.removeData();
        var wmsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WMS);

        // filter parameters
        var serviceFilter = filterer.getParameter('serviceFilter');
        var filteredLayerName = filterer.getParameter('name');
        var opacity = filterer.getParameter('opacity');
        
        this.renderStatus.initialiseResponses([serviceFilter], 'Loading...');

        var primitives = [];
        
        // loop through the wms resources looking for the resource with the name from the filter
        for (var i = 0; i < wmsResources.length; i++) {
            var layerName = wmsResources[i].get('name');
            if (layerName === filteredLayerName) {
                var layer = this.map.makeWms(undefined, undefined, wmsResources[i], this.parentLayer, serviceFilter, layerName,
                        opacity);

                layer.getWmsLayer().events.register("loadstart", this, function() {
                    this.currentRequestCount++;
                    var listOfStatus = this.renderStatus.getParameters();
                    this.fireEvent('renderstarted', this, wmsResources, filterer);
                    this.renderStatus.updateResponse(serviceFilter, "Loading WMS");
                });

                // VT: Handle the after wms load clean up event.
                layer.getWmsLayer().events.register("loadend", this, function(evt) {
                    this.currentRequestCount--;
                    var listOfStatus = this.renderStatus.getParameters();
                    this.renderStatus.updateResponse(serviceFilter, "WMS Loaded");
                    this.fireEvent('renderfinished', this);
                });

                primitives.push(layer);
            }
        }

        this.primitiveManager.addPrimitives(primitives);
        this.hasData = true;

    },

    /**
     * A function for creating a legend that can describe the displayed data. If no such thing exists for this renderer
     * then null should be returned.
     * 
     * function(portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer)
     * 
     * returns - portal.layer.legend.Legend or null
     * 
     * resources - (same as displayData) an array of data sources which should be used to render data filterer - (same
     * as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return this.legend;
    },

    /**
     * A function that is called when this layer needs to be permanently removed from the map. In response to this
     * function all rendered information should be removed
     * 
     * function()
     * 
     * returns - void
     */
    removeData : function() {
        this.primitiveManager.clearPrimitives();
    },

    /**
     * You can't abort a WMS layer from rendering as it does so via img tags
     */
    abortDisplay : Ext.emptyFn
});
/**
 * A downloader is an abstract class representing the
 * functionality of downloading a layer's data in some
 * form of archive. Typically as a ZIP
 */
Ext.define('portal.layer.downloader.Downloader', {
    extend: 'Ext.util.Observable',

    requires : ['portal.util.UnimplementedFunction'],

    map : null, //instance of portal.map.BaseMap

    constructor: function(config){
        this.map = config.map;

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * An abstract function for downloading all data
     * from a particular data source
     *
     * The result of the query should be that the user
     * is prompted for a download (via an actual download
     * or some form of popup prompt).
     *
     * function(portal.layer.Layer layer,
     *          portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer renderedFilterer,
     *          portal.layer.filterer.Filterer currentFilterer)
     *
     * returns - void (implementors should implement some form of prompt)
     *
     * layer - A layer that owns resources
     * resources - an array of data sources that were used to render data
     * renderedFilterer - custom filter that was applied when rendering the specified data sources
     * currentFilterer - The value of the custom filter, this may differ from renderedFilterer if the
     *                   user has updated the form/map without causing a new render to occur
     */
    downloadData : portal.util.UnimplementedFunction
});/**
 * A factory class for creating instances of portal.layer.downloader.Downloader
 */
Ext.define('portal.layer.downloader.DownloaderFactory', {

    map : null, //instance of portal.map.BaseMap

    constructor: function(config){
        this.map = config.map;
        this.callParent(arguments);
    },

    /**
     * An abstract function for building a portal.layer.downloader.Downloader
     * suitable for a given KnownLayer
     *
     * function(portal.knownlayer.KnownLayer knownLayer)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     *           Returning null will indicate that the known layer is incapable
     *           of downloading information to the user's computer
     */
    buildFromKnownLayer : portal.util.UnimplementedFunction,

    /**
     * An abstract function for building a portal.layer.downloader.Downloader
     * suitable for a given CSWRecord
     *
     * function(portal.csw.CswRecord cswRecord)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     *           Returning null will indicate that the known layer is incapable
     *           of downloading information to the user's computer
     */
    buildFromCswRecord : portal.util.UnimplementedFunction
});/**
 * Builds a form representing an empty filter panel
 */
Ext.define('portal.layer.filterer.forms.EmptyFilterForm', {
    extend: 'portal.layer.filterer.BaseFilterForm',

    constructor : function(config) {
        Ext.apply(config, {
            html: '<p class="centeredlabel"> Filter options will be shown here for special services.</p>'
        });

        this.callParent(arguments);
    }
});

/**
 * The ErrorWindow is a class that specialises Ext.Window into displaying
 * an error message along with its details if presented in errorObj.
 */
Ext.define('portal.widgets.window.ErrorWindow', {
    extend : 'Ext.window.Window',

    statics : {
        /**
         * Static utility for showing a window with the specified values.
         *
         * Creates a new instance on every call
         */
        showText : function(title, message, info) {
            Ext.create('portal.widgets.window.ErrorWindow', {
                title : title,
                message : message,
                info : info
            }).show();
        },

        /**
         * Static utility for showing a window with the specified values.
         *
         * Creates a new instance on every call
         */
        show : function(errorObj) {
            Ext.create('portal.widgets.window.ErrorWindow', {
                errorObj : errorObj
            }).show();
        }
    },

    /**
     * Accepts Ext.window.Window parameters along with the following additions:
     * {
     *  errorObj : Object - a response object from a portal controller function
     *  title : String - The title of the error (used if errorObj is undefined)
     *  message : String - The message of the error (used if errorObj is undefined)
     *  info : String - additional error information (used if errorObj is undefined)
     * }
     */
    constructor : function(cfg) {
        var errorObj = cfg.errorObj;
        var title = errorObj ? errorObj.title : cfg.title;
        var message = errorObj ? errorObj.message : cfg.message;
        var info = errorObj ? errorObj.info : cfg.info;

        // get a reference to our class
        var win = this;

        if (!title || !title.length) {
            title = 'Error';
        }

        Ext.apply(cfg, {
            title: title,
            layout: 'anchor',
            defaults: { anchor: '100%' },
            modal: true,
            resizable: false,
            width: 400,
            buttonAlign: 'center',
            items: [{
                xtype: 'container',
                autoScroll: true,
                border: false,
                height: 50,
                html: Ext.htmlEncode(message)
            }], // eo items
            buttons: [{
                text: 'OK',
                handler: function() {
                    win.destroy();
                }
            }], // eo buttons
            listeners: {
                afterrender: { // make sure layout sized properly
                    single: true,
                    buffer: 10,
                    fn: function() {
                        return;
                        win.doLayout();
                    }
                } // eo afterrender
            } // eo listeners
        });

        if (info) {
            cfg.items.push({
                xtype: 'panel',
                title: 'Details',
                collapsible: true,
                collapsed: false,
                titleCollapse: true,
                layout: 'fit',
                listeners: {
                    collapse: function() { win.doLayout(); },
                    expand: function() { win.doLayout(); }
                },
                items: {
                    xtype: 'container',
                    height: 50,
                    html: info
                }
            });
        }

        this.callParent(arguments);
    }
});/**
    Feature Download Manager

    A class for downloading features from a given URL. This class handles all querying for record counts, display
    of modal question's and downloading the actual records
*/
Ext.define('portal.layer.renderer.wfs.FeatureDownloadManager', {
    extend: 'Ext.util.Observable',

    proxyFetchUrl : null,
    proxyCountUrl : null,
    visibleMapBounds : null,
    featureSetSizeThreshold : 200,
    timeout : 1000 * 60 * 20, //20 minute timeout,
    filterParams : {},
    currentRequest : null, //the Ajax request object that is currently running (used for cancelling)


    /**
     * Accepts a Ext.util.Observable configuration object with the following extensions
     * {
     *  visibleMapBounds : [Optional] Object - An object that will be encoded as a bounding box parameter (if required). If not specified, the user will not have the option to select a visible bounds
     *  proxyFetchUrl : String - The URL for proxying requests for the actual records
     *  proxyCountUrl : [Optional] String - The URL for proxying requests for the count of records
     *  featureSetSizeThreshold : [Optional] Number - The minimum number of features that need to be returned before the user is prompted (default 200)
     *  timeout : [Optional] Number - the Ajax request timeout in milliseconds (default is 20 minutes)
     *  filterParams : [Optional] Any additional filter parameters to apply to the request
     * }
     *
     * Registers the following events
     *  success : function(FeatureDownloadManager this, Object filterParamsUsed, Object data, Object debugInfo)
     *  error : function(FeatureDownloadManager this, [Optional] String message, [Optional] Object debugInfo)
     *  cancelled : function(FeatureDownloadManager this)
     */
    constructor : function(cfg) {
        this.proxyFetchUrl = cfg.proxyFetchUrl;
        this.proxyCountUrl = cfg.proxyCountUrl;
        if (cfg.featureSetSizeThreshold) {
            this.featureSetSizeThreshold = cfg.featureSetSizeThreshold;
        }
        if (cfg.filterParams) {
            this.filterParams = cfg.filterParams;
        }
        this.visibleMapBounds = cfg.visibleMapBounds;
        if (cfg.timeout) {
            this.timeout = cfg.timeout;
        }
       
        this.listeners = cfg.listeners;
        this.callParent(arguments);
    },

    /**
     * Utility function for building an Object containing parameters to be sent. Bounding box is optional
     */
    _buildRequestParams : function(boundingBox, maxFeatures) {
        var params = {};
        Ext.apply(params, this.filterParams);
        if (boundingBox) {
            params.bbox = Ext.JSON.encode(boundingBox);
        }
        if (maxFeatures) {
            params.maxFeatures = maxFeatures;
        } else {
            params.maxFeatures = 0;
        }
        return params;
    },

    /**
     * Causes an AJAX request for all feature data to be created and fired off
     * @param boundingBox [Optional] String bounding box encoded as a string
     */
    _doDownload : function (boundingBox, maxFeatures) {
        var params = this._buildRequestParams(boundingBox, maxFeatures);
        this.currentRequest = portal.util.Ajax.request({
            url : this.proxyFetchUrl,
            params : params,
            callback : function(success, data, message, debugInfo) {
                if (success) {
                    this.fireEvent('success', this, params, data, debugInfo);
                } else {
                    this.fireEvent('error', this, message, debugInfo);
                }
            },
            timeout : this.timeout,
            scope : this
        });
    },

    /**
     * Internal function for handling a response from a 'count proxy url'
     */
    _doCount : function(success, data, message, debugInfo) {
        if (!success) {
            this.fireEvent('error', this, message, debugInfo);
            return;
        }

        if (data > this.featureSetSizeThreshold) {
            var callingInstance = this;

            //If we have too many features, tell the user
            var win = Ext.create('Ext.window.Window', {
                width : 600,
                height : 150,
                closable : false,
                modal : true,
                title : 'Warning: Large feature set',
                layout : 'fit',
                items : [{
                    xtype : 'component',
                    autoEl : {
                        tag: 'span',
                        html : Ext.util.Format.format('<p>You are about to display <b>{0}</b> features, doing so could make the portal run extremely slowly. Would you still like to continue?</p><br/><p>Alternatively you can abort this operation, adjust your zoom/filter and then try again.</p>', data)
                    },
                    cls : 'ext-mb-text'
                }],
                dockedItems : [{
                    xtype : 'toolbar',
                    dock : 'bottom',
                    ui : 'footer',
                    layout : {
                        type : 'hbox',
                        pack : 'center'
                    },
                    items : [{
                        xtype : 'button',
                        text : Ext.util.Format.format('Display {0} features', data),
                        handler : function(button) {
                            callingInstance._doDownload(callingInstance._visibleMapBounds);
                            button.ownerCt.ownerCt.close();
                        }
                    },{
                        xtype : 'button',
                        text : 'Abort operation',
                        handler : function(button) {
                            callingInstance.fireEvent('cancelled', callingInstance);
                            button.ownerCt.ownerCt.close();
                        }
                    }]
                }]
            });
            win.show();
        } else {
            //If we have an acceptable number of records, this is how we shall proceed
            this._doDownload(this.visibleMapBounds);
        }
        
    },

    /**
     * Function for starting a download through this FeatureDownloadManager
     */
    startDownload : function() {
        //Firstly attempt to discern how many records are available, this will affect how we proceed
        //If we dont have a proxy for doing this, then just download everything
        if (this.proxyCountUrl && this.proxyCountUrl.length > 0) {
            this.currentRequest = portal.util.Ajax.request({
                url : this.proxyCountUrl,
                params : this._buildRequestParams(this.visibleMapBounds),
                scope : this,
                timeout : this.timeout,
                callback : this._doCount
            });
        } else {
            //if we dont have a URL to proxy our count requests through then just
            //attempt to download all visible features (it's better than grabbing thousands of features)
            this._doDownload(this.visibleMapBounds, this.featureSetSizeThreshold);
        }
    },

    /**
     * Causees any in progress downloads to be aborted immediately. If there are no downloads,
     * nothing will occur.
     */
    abortDownload : function() {
        if (this.currentRequest) {
            Ext.Ajax.abort(this.currentRequest);
        }
    }
});
/**
 * An implementation of a portal.layer.renderer for rendering WFS Features
 * as transformed by the AuScope portal backend.
 */
Ext.define('portal.layer.renderer.wfs.FeatureRenderer', {
    extend: 'portal.layer.renderer.Renderer',

    config : {
        /**
         * portal.map.Icon - Information about the icon that is used to represent point locations of this WFS
         */
        icon : null
    },

    legend : null,
    allDownloadManagers : null,

    constructor: function(config) {
        this.currentRequestCount = 0;//how many requests are still running
        this.legend = Ext.create('portal.layer.legend.wfs.WFSLegend', {
            iconUrl : config.icon ? config.icon.getUrl() : ''
        });
        this.allDownloadManagers = [];

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Must be called whenever a download manager returns a response (success or failure)
     *
     * You can optionally pass in an array of markers/overlays that were rendered
     */
    _finishDownloadManagerResponse : function(primitiveList) {
        //If we haven't had any data come back yet from another response (and we have data now)
        //update the boolean indicating that we've had data
        if (primitiveList) {
            this.hasData = (primitiveList.length > 0);
        }

        this.currentRequestCount--;
        if (this.currentRequestCount === 0) {
            this.fireEvent('renderfinished', this);
        }
    },

    /**
     * Used to handle the case where the download manager returns an error
     */
    _handleDownloadManagerError : function(dm, message, debugInfo, onlineResource, layer) {
        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), message);
        if(debugInfo) {
            this.renderDebuggerData.updateResponse(onlineResource.get('url'), message + debugInfo.info);
        } else {
            this.renderDebuggerData.updateResponse(onlineResource.get('url'), message);
        }

        //we are finished
        this._finishDownloadManagerResponse();
    },

    /**
     * Used for handling the case when the user cancels their download request
     */
    _handleDownloadManagerCancelled : function(dm, onlineResource, layer) {
        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), 'Request cancelled by user.');

        //we are finished
        this._finishDownloadManagerResponse();
    },

    /**
     * Used for handling a successful response from a request's download manager
     */
    _handleDownloadManagerSuccess : function(dm, actualFilterParams, data, debugInfo, onlineResource, layer, icon) {
        var me = this;
        //Parse our KML into a set of overlays and markers
        var parser = Ext.create('portal.layer.renderer.wfs.GMLParser', {gml : data.gml, map : me.map});
        var primitives = parser.makePrimitives(icon, onlineResource, layer);
        var count = parser.getFeatureCount();
        
        //Add our single points and overlays to the overlay manager (which will add them to the map)
        this.primitiveManager.addPrimitives(primitives);

        //Store some debug info
        if (debugInfo) {
            this.renderDebuggerData.updateResponse(debugInfo.url, debugInfo.info);
        }

        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), count == null ? ('Unknown number of features retrieved.') : (count + " feature(s) retrieved."));

        //we are finished
        this._finishDownloadManagerResponse(primitives);
    },

    /**
     * An implementation of the abstract base function. See comments in superclass
     * for more information.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success) callback
     *
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        //start by removing any existing data
        this.abortDisplay();
        this.removeData();

        var me = this;
        var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);

        //Initialise our render status with every URL we will be calling (these will get updated as we go)
        var urls = [];
        for (var i = 0; i < wfsResources.length; i++) {
            urls.push(wfsResources[i].get('url'));
        }
        this.renderStatus.initialiseResponses(urls, 'Loading...');

        //alert any listeners that we are about to start rendering
        this.fireEvent('renderstarted', this, wfsResources, filterer);
        this.currentRequestCount = wfsResources.length; //this will be decremented as requests return

        //Each and every WFS resource will be queried with their own separate download manager

        for (var i = 0; i < wfsResources.length; i++) {
            //Build our filter params object that will make a request
            var filterParams = filterer.getParameters();
            var onlineResource = wfsResources[i];

            filterParams.serviceUrl = onlineResource.data.url;
            filterParams.typeName = onlineResource.data.name;
            filterParams.maxFeatures = 200;

            //Our requesting is handled by a download manager
            var downloadManager = Ext.create('portal.layer.renderer.wfs.FeatureDownloadManager', {
                visibleMapBounds : filterer.getSpatialParam(),
                proxyFetchUrl : this.proxyUrl,
                proxyCountUrl : this.proxyCountUrl,
                filterParams : filterParams,
                listeners : {
                    //Please note that the following bindings override args as ExtJS events append
                    //the listeners object to fired events (we don't want that) so we are forced to override
                    //that parameter using the appendArgs argument in Ext.bind
                    success : Ext.bind(this._handleDownloadManagerSuccess, this, [onlineResource, this.parentLayer, this.icon], 4), //Override args from the 4th argument
                    error : Ext.bind(this._handleDownloadManagerError, this, [onlineResource, this.parentLayer], 3), //Override args from the 3rd
                    cancelled : Ext.bind(this._handleDownloadManagerCancelled, this, [onlineResource, this.parentLayer], 1) //Override args from the 1st
                }
            });

            downloadManager.startDownload();

            this.allDownloadManagers.push(downloadManager);//save this manager in case we need to abort later on
        }
    },

    /**
     * An abstract function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return this.legend;
    },

    /**
     * An abstract function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : function() {
        this.primitiveManager.clearPrimitives();
    },

    /**
     * An abstract function - see parent class for more info
     */
    abortDisplay : function() {
        for (var i = 0; i < this.allDownloadManagers.length; i++) {
            this.allDownloadManagers[i].abortDownload();
        }
    }
});/**
 * Abstract base class representing a source of a WFS feature
 * that can fetch a single feature encoded as a snippet of DOM.
 */
Ext.define('portal.layer.querier.wfs.FeatureSource', {


    /**
     * function(String featureId, String featureType, String wfsUrl, function callback)
     *
     * The callback function to be executed with a feature node on success or null on failure.
     * callback(DOMNode feature,
     *          String featureId,
     *          String featureType,
     *          String wfsUrl)
     */
    getFeature : portal.util.UnimplementedFunction
});
/**
 * An extension to OpenLayers.Handler.Feature which ensures the raw click event
 * is also passed up to any event handlers.
 *
 * It also ensures that click events that do NOT hit a feature still return a click event (sans feature)
 */


Ext.ns('portal.map.openlayers');
portal.map.openlayers.FeatureWithLocationHandler = OpenLayers.Class(OpenLayers.Handler.Feature, {

    /**
     * Hackish approach to remembering the last handled event so we can append it to the argument list
     * of the event handler
     */
    lastHandledEvent : null,
    mouseDragFlag: false,
    downX: 0,
    downY: 0,
    handle : function(event) {
        //Remember our event (we'll inject it during our triggerCallback override)

        var handled = OpenLayers.Handler.Feature.prototype.handle.apply(this, arguments);

        //If we don't handle the click, return the event anyway
        //LJ AUS-2592.5 Separate the panning dragging event and click event 
        //   by calculating the distance between mousedown and mouseup position.
        if (!handled) {
            var type = event.type;
            if (type === 'click' && !this.mouseDragFlag) {
                handled = true;                
                this.callback('click', [null, event]);                
            } else if (type === 'mousedown') {
                this.downX = event.xy.x;
                this.downY = event.xy.y;     
                this.mouseDragFlag = true;                
            } else if (type === 'mouseup') {    
            	if (this.lastHandledEvent != null && this.lastHandledEvent.type == 'mousedown') {
                    var xx = Math.abs(event.xy.x - this.downX);
                    var yy = Math.abs(event.xy.y - this.downY);
                    if ( xx < 5 && yy < 5)
                        this.mouseDragFlag = false;
            	}                    
            }            
        }
        this.lastHandledEvent = event;
        return handled;
    },

    triggerCallback : function(type, mode, args) {
        args.push(this.lastHandledEvent);

        return OpenLayers.Handler.Feature.prototype.triggerCallback.apply(this, arguments);
    }
});/**
 * An implementation of a portal.layer.renderer for rendering WFS with WMS Features
 * as transformed by the AuScope portal backend.
 */
Ext.define('portal.layer.renderer.wfs.FeatureWithMapRenderer', {
    extend: 'portal.layer.renderer.Renderer',

    config : {
        /**
         * portal.map.Icon - Information about the icon that is used to represent point locations of this WFS
         */
        icon : null
    },

    legend : null,
    allDownloadManagers : null,
    sld_body : null,

    constructor: function(config) {
        this.currentRequestCount = 0;//how many requests are still running

        //VT: ALL KNOWLAYER ICON in FeatureWithMapRenderer has to be assigned a marker color in
        // the auscope-knownlayer config file else we will treat it has a pure WMS layer and
        // give it a wms legend.

        if(config.icon.getIsDefault()===true){
            this.legend = Ext.create('portal.layer.legend.wms.WMSLegend', {
                iconUrl : config.iconCfg ? config.iconCfg.url : 'portal-core/img/key.png'
            });
        }else{
            this.legend = Ext.create('portal.layer.legend.wfs.WFSLegend', {
                iconUrl : config.icon ? config.icon.getUrl() : ''
            });
        }
        this.allDownloadManagers = [];
        this.currentRequestCount = 0;
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
        
        this.on('renderfinished', this._cleanupAbort, this);
    },

    /**
     * Must be called whenever a download manager returns a response (success or failure)
     *
     * You can optionally pass in an array of markers/overlays that were rendered
     */
    _finishDownloadManagerResponse : function(primitiveList) {
        //If we haven't had any data come back yet from another response (and we have data now)
        //update the boolean indicating that we've had data
        if (primitiveList) {
            this.hasData = (primitiveList.length > 0);
        }

        this.currentRequestCount--;
        if (this.currentRequestCount === 0) {
            this.fireEvent('renderfinished', this);
        }
    },

    /**
     * Used to handle the case where the download manager returns an error
     */
    _handleDownloadManagerError : function(dm, message, debugInfo, onlineResource, layer) {
        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), message);
        if(debugInfo) {
            this.renderDebuggerData.updateResponse(onlineResource.get('url'), message + debugInfo.info);
        } else {
            this.renderDebuggerData.updateResponse(onlineResource.get('url'), message);
        }

        //we are finished
        this._finishDownloadManagerResponse();
    },

    /**
     * Used for handling the case when the user cancels their download request
     */
    _handleDownloadManagerCancelled : function(dm, onlineResource, layer) {
        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), 'Request cancelled by user.');

        //we are finished
        this._finishDownloadManagerResponse();
    },

    /**
     * Used for handling a successful response from a request's download manaager
     */
    _handleDownloadManagerSuccess : function(dm, actualFilterParams, data, debugInfo, onlineResource, layer, icon) {
        var me = this;
        //Parse our KML into a set of overlays and markers
        var parser = Ext.create('portal.layer.renderer.wfs.GMLParser', {gml : data.gml, map : me.map});
        var primitives = parser.makePrimitives(icon, onlineResource, layer);
        var count = parser.getFeatureCount();

        //Add our single points and overlays to the overlay manager (which will add them to the map)
        this.primitiveManager.addPrimitives(primitives);

        //Store some debug info
        if (debugInfo) {
            this.renderDebuggerData.updateResponse(debugInfo.url, debugInfo.info);
        }

        //store the status
        this.renderStatus.updateResponse(onlineResource.get('url'), count == null ? ('Unknown number of features retrieved.') : (count + " feature(s) retrieved."));

        //we are finished
        this._finishDownloadManagerResponse(primitives);
    },

    /**
     * An implementation of the abstract base function. See comments in superclass
     * for more information.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success) callback
     *
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        //start by removing any existing data
        this.abortDisplay();
        this.removeData();
        this.aborted = false;

        var me = this;
        var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
        var wmsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WMS);

        var urls = [];
        var wmsRendered=[];
        //alert any listeners that we are about to start rendering wms
        //this.fireEvent('renderstarted', this, wmsResources, filterer);

        //VT: portal.util.URL.base is unable to resolve the name of the
        // local machine instead return localhost. eg localhost:8080/Auscope-Portal/
        var home=portal.util.URL.base;
        if(home.indexOf("localhost") != -1){
            home=home.replace("localhost",LOCALHOST);
        }
      //get the style format encoded as string
      //  var styleUrl = escape(Ext.urlAppend(home + this.parentLayer.get('source').get('proxyStyleUrl'), unescape(Ext.Object.toQueryString(filterer.getMercatorCompatibleParameters()))));
      //  this.sld=unescape(styleUrl);


        for (var i = 0; i < wmsResources.length; i++) {
            var wmsUrl = wmsResources[i].get('url');
            // VT: Instead of rendering the WMS url in the status, it is neater to display the wfs url

            var wmsLayer = wmsResources[i].get('name');
            var wmsOpacity = filterer.getParameter('opacity');
            //  FT: Generate serviceUrl based on WFS URL, serviceUrl is important for NVCL Borehole
            //      - to display only those with Hylogger Data based on those listed in nvcl:ScannedBoreholeCollection
            for (var j=0; j<wfsResources.length; j++) {
                var wfsHost = this._getDomain(wfsResources[j].get('url'));
                var wmsHost = this._getDomain(wmsUrl);
                if (wfsHost==wmsHost) {
                    serviceUrl = wfsResources[j].get('url');
                }

            }

            if(filterer.getParameters().serviceFilter &&
                    (this._getDomain(wmsResources[i].get('url'))!= this._getDomain(filterer.getParameters().serviceFilter[0]))){
                continue;
            }

            var color = portal.map.Icon.mapIconColor(this.parentLayer.get('source').get('iconUrl'));


            var proxyUrl = this.parentLayer.get('source').get('proxyStyleUrl');
            var filterParams = (Ext.Object.toQueryString(filterer.getMercatorCompatibleParameters()));
            if(typeof(color) != "undefined"){
                filterParams += "&color=" + escape(color);
            }
            filterParams += "&serviceUrl=" + escape(serviceUrl);
            var styleUrl="";
            if(proxyUrl){
                styleUrl = Ext.urlAppend(proxyUrl,filterParams);
                styleUrl = Ext.urlAppend(styleUrl,"layerName="+wmsLayer);
            }else{
                //VT: if style proxy url is not defined, we assign it a default.
                styleUrl = Ext.urlAppend("getDefaultStyle.do","layerName="+wmsLayer);
            }


            wmsRendered[this._getDomain(wmsUrl)]=1;


            Ext.Ajax.request({
                url: styleUrl,
                timeout : 180000,
                scope : this,
                success: Ext.bind(this._getRenderLayer,this,[wmsResources[i], wmsUrl, wmsLayer, wmsOpacity,wfsResources, filterer],true),
                failure: function(response, opts) {                    
                     if (this.currentRequestCount <= 0) {
                         this.fireEvent('renderfinished', this);
                     }
                    console.log('server-side failure with status code ' + response.status);
                }
            });

        }


        this.hasData = true;
        //this array will contain a list of wfs url that are process by its wms component.
        var wmsUrls = [];

        //Initialise our render status with every URL we will be calling (these will get updated as we go)

        for (var i = 0; i < wfsResources.length; i++) {
            var wfsUrl = wfsResources[i].get('url');
            var wfsLayer = wfsResources[i].get('name');
            urls.push(wfsUrl);
            // VT: Instead of rendering the WMS url in the status, it is neater to display the wfs url
            if(wmsRendered[this._getDomain(wfsUrl)]){
                wmsUrls.push(wfsUrl);
                this.renderStatus.updateResponse(wfsUrl, "Loading WMS");
            }
        }
        this.renderStatus.initialiseResponses(urls, 'Loading...');



        //alert any listeners that we are about to start rendering wfs
        this.fireEvent('renderstarted', this, wfsResources, filterer);
        //this.currentRequestCount = wfsResources.length; //this will be decremented as requests return

        //Each and every WFS resource will be queried with their own seperate download manager

        for (var i = 0; i < wfsResources.length; i++) {

            var wfsUrl = wfsResources[i].get('url');
            var wfsLayer = wfsResources[i].get('name');
            //only if WMS has not been built
            if(!wmsRendered[this._getDomain(wfsUrl)]){
                this.currentRequestCount++;
                //Build our filter params object that will make a request
                var filterParams = filterer.getParameters();
                var onlineResource = wfsResources[i];
                filterParams.serviceUrl = wfsUrl;
                filterParams.typeName = wfsLayer;
                filterParams.maxFeatures = 200;

                if(filterer.getParameters().serviceFilter &&
                        filterParams.serviceUrl!=filterer.getParameters().serviceFilter[0]){
                    this.currentRequestCount--;
                    this.renderStatus.updateResponse(filterParams.serviceUrl, "Not Queried");
                    continue;
                }

                //Our requesting is handled by a download manager
                var downloadManager = Ext.create('portal.layer.renderer.wfs.FeatureDownloadManager', {
                    visibleMapBounds : filterer.getSpatialParam(),
                    proxyFetchUrl : this.proxyUrl,
                    proxyCountUrl : this.proxyCountUrl,
                    filterParams : filterParams,
                    listeners : {
                        //Please note that the following bindings override args as ExtJS events append
                        //the listeners object to fired events (we don't want that) so we are forced to override
                        //that parameter using the appendArgs argument in Ext.bind
                        success : Ext.bind(this._handleDownloadManagerSuccess, this, [onlineResource, this.parentLayer, this.icon], 4), //Override args from the 4th argument
                        error : Ext.bind(this._handleDownloadManagerError, this, [onlineResource, this.parentLayer], 3), //Override args from the 3rd
                        cancelled : Ext.bind(this._handleDownloadManagerCancelled, this, [onlineResource, this.parentLayer], 1) //Override args from the 1st
                    }
                });

                downloadManager.startDownload();

                this.allDownloadManagers.push(downloadManager);//save this manager in case we need to abort later on
            }
            
        }
        if (this.currentRequestCount === 0) {
            this.fireEvent('renderfinished', this);
        }

    },


    _getRenderLayer : function(response,opts,wmsResource, wmsUrl, wmsLayer, wmsOpacity,wfsResources,filterer){
        if (this.aborted) {
            return;
        }
        
        if(wmsOpacity === undefined){
            wmsOpacity = filterer.parameters.opacity;
        }

        var sld_body = response.responseText;
        this.sld_body = sld_body;
        if(sld_body.indexOf("<?xml version=")!=0){
            this._updateStatusforWFSWMS(wmsUrl, "error: invalid SLD response");
            return
        }
        
        this._updateStatusforWFSWMS(wmsUrl, "Testing Connection");
        this.fireEvent('renderstarted', this, wfsResources, filterer);
        //VT: add for test connection
        this.currentRequestCount++;
        
        Ext.Ajax.request({
            url: "testServiceGetCap.do",
            timeout : 180000,  
            params : {
                serviceUrl : wmsUrl  
            },
            scope : this,
            success: Ext.bind(this._addWMSLayer,this,[wmsResource, wmsUrl, wmsLayer, wmsOpacity,wfsResources, filterer,sld_body],true),
            failure: function(response, opts) {
                 this.currentRequestCount--;
                 this._updateStatusforWFSWMS(wmsUrl, "Address cannot be reached");
                 if (this.currentRequestCount === 0) {                     
                     this.fireEvent('renderfinished', this);
                 }
            }
        });
        
       
    },
    
    _addWMSLayer : function(response,opts,wmsResource, wmsUrl, wmsLayer, wmsOpacity,wfsResources,filterer,sld_body){
        //VT: minus test connection
        this.currentRequestCount--;
        
        if (this.aborted) {
            return;
        }
        
        var layer=this.map.makeWms(undefined, undefined, wmsResource, this.parentLayer, wmsUrl, wmsLayer, wmsOpacity,sld_body)

        layer.getWmsLayer().events.register("loadstart",this,function(){
            this.currentRequestCount++;
            var listOfStatus=this.renderStatus.getParameters();
            for(key in listOfStatus){
                if(this._getDomain(key)==this._getDomain(layer.getWmsUrl())){
                    this.renderStatus.updateResponse(key, "Loading WMS");
                    this.fireEvent('renderstarted', this, wfsResources, filterer);
                    break
                }
            }

        });

        //VT: Handle the after wms load clean up event.
        layer.getWmsLayer().events.register("loadend",this,function(evt){
            this.currentRequestCount--;
            if (this.currentRequestCount === 0) {
                this.fireEvent('renderfinished', this);
            }
            var listOfStatus=this.renderStatus.getParameters();
            this._updateStatusforWFSWMS(layer.getWmsUrl(),"WMS Loaded");                        
        });
        
        var primitives = [];
        primitives.push(layer);
        this.primitiveManager.addPrimitives(primitives);
        
    },
    
    _updateStatusforWFSWMS : function(updateKey,newValue){
        for(key in this.renderStatus.getParameters()){
            if(this._getDomain(key)==this._getDomain(updateKey)){
                this.renderStatus.updateResponse(key, newValue);
                break
            }
        }
    },

    _getDomain : function(data) {
        return portal.util.URL.extractHostNSubDir(data,1);
      },

  
    /**
     * An abstract function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return this.legend;
    },

    /**
     * An abstract function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : function() {
        this.abortDisplay();
        this.primitiveManager.clearPrimitives();
        this.fireEvent('renderfinished', this);
    },

    /**
     * An abstract function - see parent class for more info
     */
    abortDisplay : function() {
        this.aborted = true;
        for (var i = 0; i < this.allDownloadManagers.length; i++) {
            this.allDownloadManagers[i].abortDownload();
        }
    },
    
    _cleanupAbort : function() {
        this.aborted = false;
    }
});/**
 * Utility functions for downloading files
 */
Ext.define('portal.util.FileDownloader', {
    singleton: true
}, function() {
    /**
     * Given a URL this function will create a hidden form and post its (empty) contents
     * to the specified URL. If the response contains an appropriate header the user will
     * be prompted for a file download.
     *
     * @param url The URL to download to
     * @param parameters [Optional] a javascript object containing parameter key value pairs to be posted. The values may consist of Javascript primitives or Arrays
     */
    portal.util.FileDownloader.downloadFile = function(url, parameters, method) {
        //build our list of input children first
        var inputs = [];

        if (typeof method === "undefined") {
        	method = "POST"; 
        }
        if (method == "GET") {
        	// Populate inputs from the url
        	Ext.apply(parameters, Ext.urlDecode(url.split('?')[1]));
        }
       
        
        if (parameters) {
            for (var key in parameters) {
                if (!key) {
                    continue;
                }

                //We need to treat arrays and objects
                var value = parameters[key];
                if (!Ext.isArray(value)) {
                    value = [value];
                }

                //Build a number of Ext.DomHelper config objects representing the inputs
                for (var i = 0; i < value.length; i++) {
                    inputs.push({
                        tag : 'input',
                        id : Ext.util.Format.format('portal-input-{0}-{1}', key, i),
                        type : 'hidden',
                        name : key,
                        value : value[i]
                    });
                }
            }
        }

        //This will leak iframes but it shouldnt be an issue under "normal" usage
        var frameId = Ext.id();
        var body = Ext.getBody();
        var frame = body.createChild({
            tag : 'iframe',
            id : frameId,
            name : 'iframe'
        });
        var form = body.createChild({
            tag : 'form',
            target : frameId,
            method : method,
            children : inputs
        });

        portal.util.GoogleAnalytic.trackevent('FileDownloader','Download', url);
        portal.util.PiwikAnalytic.trackevent('FileDownloader', 'Url:' + url,'parameters:' + Ext.encode(parameters));

        form.dom.action = url;	
        form.dom.submit();
    };
});/**
 * An abstract class represent a 'hash map esque' class
 * representing a custom filter that may be applied a layer
 * for the purposes of subsetting it's referenced data sources
 *
 * events :
 *      change(portal.layer.filterer.Filterer this, String[] keys)
 *          Fired whenever the filter changes, passed an array of all keys that have changed.
 */
Ext.define('portal.layer.filterer.Filterer', {
    extend: 'portal.util.ObservableMap',

    statics : {
        BBOX_FIELD : 'bbox' //the portal wide name for a bounding box field
    },

    config : {
        spatialParam : null //this should always be an instance of portal.util.BBox
    },

    constructor: function(config){     
        this.callParent(arguments);       
    },


    /**
     * Gets the set of parameters configured within this map as
     * a simple javascript object with key/value pairs
     *
     * The spatial component will be written to portal.layer.filterer.SpatialFilterer.BBOX_FIELD
     *
     * returns - a javascript object
     */
    getParameters : function() {
        var params = this.callParent(arguments);

        var bbox = this.getSpatialParam();
        if (bbox) {
            params[portal.layer.filterer.Filterer.BBOX_FIELD] = Ext.JSON.encode(bbox);
        }

        return params;
    },

    /**
     * Gets the set of parameters configured within this map as
     * a simple javascript object with key/value pairs
     *
     * The spatial component will be written to portal.layer.filterer.SpatialFilterer.BBOX_FIELD
     *
     * returns - a javascript object
     */
    getMercatorCompatibleParameters : function() {
        var params = this.getParameters();

        var bbox = this.getSpatialParam();            	

        if(bbox) {
            if (bbox.crs=='EPSG:4326') {
                var bounds = new OpenLayers.Bounds(bbox.westBoundLongitude, bbox.southBoundLatitude, bbox.eastBoundLongitude, bbox.northBoundLatitude);
                bounds = bounds.transform('EPSG:4326','EPSG:3857');
                bbox = Ext.create('portal.util.BBox', {
                    northBoundLatitude : bounds.top,
                    southBoundLatitude : bounds.bottom,
                    eastBoundLongitude : bounds.right,
                    westBoundLongitude : bounds.left,
                    crs : 'EPSG:3857'
                });
            }
            
            params[portal.layer.filterer.Filterer.BBOX_FIELD] = Ext.JSON.encode(bbox);
        }

        return params;
    },

    /**
     * Sets the internal bbox field with value - value can be a Object, BBox or JSON String
     */
    applySpatialParam : function(value) {
        if (!value) {
            return value;
        }

        if (value instanceof portal.util.BBox) {
            return value;
        }

        //Any string should be a JSON string
        if (Ext.isString(value)) {
            value = Ext.JSON.decode(value);
        }

        if (Ext.isObject(value)) {
            return Ext.create('portal.util.BBox', value);
        }

        throw 'unable to parse value';
    },

    /**
     * Given a set of parameters as a plain old javascript object of
     * key/value pairs, apply it's contents to this map.
     *
     * This is a useful function if you want to set multiple parameters
     * and only raise a single event
     *
     * Any parameter named portal.layer.filterer.SpatialFilterer.BBOX_FIELD will be treated as a special
     * case and
     *
     * parameters - a plain old javascript object
     * clearFirst - [Optional] if true, then the internal map will be cleared BEFORE any values are added
     */
    setParameters : function(parameters, clearFirst) {
        //Get rid of the 'bbox' field, we treat it as a special case
        var noBboxParams = Ext.apply({}, parameters);
        var bbox = parameters.bbox;
        noBboxParams[portal.layer.filterer.Filterer.BBOX_FIELD] = undefined;

        //Only apply the bbox value (undefined, null or otherwise) if
        //    a) we are explicitly clearing values - it doesn't matter what bbox's value is
        //       it will overwrite the internal BBOX_FIELD
        //    b) the bbox value has been explicitly included in parameters
        //In the event that an empty object is passed to parameters '{}' we don't wan't to be assigning
        //'undefined' to bbox as it is inconsistent with the behaviour of other parameters
        if (portal.layer.filterer.Filterer.BBOX_FIELD in parameters || clearFirst) {
            this.setSpatialParam(bbox, true);
        }

        //Proceed normally
        this.callParent([noBboxParams, clearFirst]);
    },

    /**
     * Sets a single parameter of this map
     *
     * key - a string key whose value will be set. Will override any existing key of the same name
     * value - The object value to set
     * quiet[optional] - qu
     */
    setParameter : function(key, value, quiet){
        if (key === portal.layer.filterer.Filterer.BBOX_FIELD) {
            this._setBboxField(value);
            this.fireEvent('change', this, [key]);
        } else {
            this.callParent(arguments);
        }
    },

    /**
     * Gets the value of the specified key as an Object
     *
     * key - A string key whose value will be fetched.
     *
     * returns - a javascript object matching key
     */
    getParameter : function(key) {
        if (key === portal.layer.filterer.Filterer.BBOX_FIELD) {
            if (this.bbox) {
                return Ext.JSON.encode(this.getSpatialParam());
            } else {
                return undefined;
            }
        } else {
            return this.callParent(arguments);
        }
    },

    /**
     * Sets the value of the internal spatialParam and fires a change event
     * (unless suppressEvents is specified)
     */
    setSpatialParam : function(spatialParam, suppressEvents) {
        this.spatialParam = this.applySpatialParam(spatialParam);

        if (!suppressEvents) {
            this.fireEvent('change', this, [portal.layer.filterer.Filterer.BBOX_FIELD]);
        }
    },
    
    /**
     * Sets the value of the internal spatialParam and fires a change event
     * (unless suppressEvents is specified)
     */
    getSpatialParam : function() {
        return this.spatialParam;
    },

    /**
     * Returns a shallow clone of this filterer with the exception of the spatial params,
     * in which a true copy is returned
     */
    clone : function() {
        var clonedObj = Ext.create('portal.layer.filterer.Filterer', {});
        var thisBBox = this.getSpatialParam();

        Ext.apply(clonedObj.parameters, this.parameters);
        clonedObj.setSpatialParam(thisBBox ? thisBBox.clone() : thisBBox, true);

        return clonedObj;
    }
});/**
 * A panel for displaying filter forms for a given portal.layer.Layer
 *
 * A filter panel is coupled tightly with a portal.widgets.panel.LayerPanel
 * as it is in charge of displayed appropriate filter forms matching the current
 * selection
 *
 * VT: THIS CLASS IS TO BE DELETED WITH THE NEW INLINE UI
 */

Ext.define('portal.widgets.panel.FilterPanel', {
    extend: 'Ext.panel.Panel',

   
    _addOrUpdateLayerButton : null,
    
    filterForm : null,
    
    constraintShown : false,
    nagiosAlertShown: false,
    
    optionsButtonIsHidden : false,
    
    layerStore: null,
    
    menuFactory: null,

    /**
     * Accepts all parameters for a normal Ext.Panel instance with the following additions
     * {
     *  layerPanel : [Required] an instance of a portal.widgets.panel.LayerPanel - selection events will be listend for
     *  wantAddLayerButton : boolean [Optional - defaults to True]
     *  wantOptionsButton : boolean [Optional - defaults to True]
     * } 
     */
    constructor : function(config) {
 
        this._map = config.map;     
        
        this.layerStore = config.layerStore;
        
        // setup the default options or use those passed in by the portal
        var menuItems = config.menuItems ? config.menuItems :
            [this._getResetFormAction(),this._getDeleteAction(),this._setVisibilityAction()];
        
        if(Ext.isIE){
            this.filterForm = config.filterForm.cloneConfig();        
            this.filterForm.getForm().setValues(config.filterForm.getForm().getValues());
            config.filterForm  = this.filterForm;                
            config.filterForm.layer.set('filterForm',this.filterForm);
        }else{
            this.filterForm = config.filterForm
        }
                    
        if (typeof config.wantAddLayerButton === 'undefined' || config.wantAddLayerButton ) {
            this._addOrUpdateLayerButton = Ext.create('Ext.button.Button', {
                xtype : 'button',
                text      : 'Add layer to Map',
                iconCls    :   'add',
                handler : Ext.bind(this._onAddLayer, this)
            });
        } else if (config.wantUpdateLayerButton ) {
            this._addOrUpdateLayerButton = Ext.create('Ext.button.Button', {
                xtype : 'button',
                text      : 'Update Layer on Map',
                iconCls    :   'edit',
                handler : Ext.bind(this._onAddLayer, this)
            });
        }        
        
        if (typeof config.wantOptionsButton === 'undefined' || config.wantOptionsButton ) {
            this.optionsButtonIsHidden = false;
        } else {
            this.optionsButtonIsHidden = true;
        }                            
        
        if(config.menuFactory){
            this.menuFactory = config.menuFactory;
            var mf= config.menuFactory;
            if (mf.addResetFormActionForWMS) {
                menuItems.push(this._getResetFormAction());
            }
            mf.appendAdditionalActions(menuItems,this.filterForm.layer,this.filterForm.layer.get('source').get('group'),this._map);            
        }
        
        //VT:All special menu item should be determined from the menu factory. This is the only exception as all layers 
        //VT:Should have a legend action except for Insar data.
        // but even then if the portal is specifiying the menu items then don't add a legend by default
        if (config.menuItems && this.filterForm.layer.get('renderer').getLegend()){            
            menuItems.push(this._getLegendAction(this.filterForm.layer));
        }   
        
        if(!config.menuFactory){
            //VT:Default behavior if there are no menuFactory defined.
            if(this.filterForm.layer.get('cswRecords').length > 0 &&
                    this.filterForm.layer.get('cswRecords')[0].get('noCache')==false){
                     menuItems.push(this._getDownloadAction());
            }
        }
        

        var optionsButton = Ext.create('Ext.button.Button', {
            xtype : 'button',
            text : 'Options',
            iconCls : 'setting',
            arrowAlign : 'right'
        });
        
        if (menuItems.length === 0) {
            this.optionsButtonIsHidden = true;
        } else if (menuItems.length === 1) {
            var optionsAction = menuItems[0];
            optionsButton = Ext.create('Ext.button.Button', optionsAction);
        } else {
            optionsButton.setMenu(menuItems)
        }

        optionsButton.setHidden(this.optionsButtonIsHidden)

        Ext.apply(config, { 
            items : [
                this.filterForm
            ],
            buttons : [
                this._addOrUpdateLayerButton,
            {
                xtype:'tbfill'
            },
            optionsButton
            ]
        
        });

        this.callParent(arguments);
    },
    
    _getResetFormAction : function(){
        var baseform = this.filterForm;
        
        return new Ext.Action({
            text : 'Reset Form',
            iconCls : 'refresh',
            handler : function(){
                baseform.getForm().reset();
            }
        })
        
    },
    
    _getLegendAction : function(layer){                       
        var legend = layer.get('renderer').getLegend();
        var text = 'Get Legend';
        var me = this;
       
        var getLegendAction = new Ext.Action({
            text : text,
            icon : legend.iconUrl,
            //icon : null,
            iconCls : 'portal-ux-menu-icon-size',
            itemId : 'LegendAction',
            
            handler : function(){
                var legendCallback = function(legend, resources, filterer, success, form, layer){
                    if (success && form) {
                        var winId = 'legend-' + layer.get('id');    
                        if (Ext.getCmp(winId)) {
                            Ext.getCmp(winId).close();
                        }
                        var win = Ext.create('Ext.window.Window', {
                            id          : winId,
                            title       : 'Legend: '+ layer.get('name'),
                            layout      : 'fit',
                            width       : 200,
                            height      : 300,
                            modal : true,
                            items: form
                        });
                        return win.show();
                    }
                };

                var onlineResources = layer.getAllOnlineResources();
                var filterer = layer.get('filterer');
                var renderer = layer.get('renderer');
                var legend = renderer.getLegend(onlineResources, filterer);

                //VT: this style is just for the legend therefore no filter is required.
                var styleUrl = layer.get('renderer').parentLayer.get('source').get('proxyStyleUrl');    
                //VT: if a layer has style, the style should take priority as the default GetLegend source else use default
                if(styleUrl && styleUrl.length > 0){
                    //LJ: AUS-2619 Additional params for legend.
                    var sldConfig = me.menuFactory.appendAdditionalLegendParams(layer, filterer, styleUrl);

                    if (sldConfig.isSld_body === false) {
                        legend.getLegendComponent(onlineResources, filterer, sldConfig.sldUrl, false, Ext.bind(legendCallback, this, [layer], true));  
                    } else {
	                    Ext.Ajax.request({
	                        url: sldConfig.sldUrl,
	                        timeout : 180000,
	                        scope : this,
	                        success:function(response,opts){
	                            legend.getLegendComponent(onlineResources, filterer,response.responseText, true, Ext.bind(legendCallback, this, [layer], true));
	                        },
	                        failure: function(response, opts) {
	                            legend.getLegendComponent(onlineResources, filterer,"", true, Ext.bind(legendCallback, this, [layer], true));
	                        }                        
	                    });
                    }
                
                }else{
                    legend.getLegendComponent(onlineResources, filterer,"", true, Ext.bind(legendCallback, this, [layer], true));
                }
                
            }
        });
        
        return getLegendAction;
    },
    
    _getDownloadAction : function(){
        var me = this;
        var downloadLayerAction = new Ext.Action({
            text : 'Download Layer',
            iconCls : 'download',
            handler : function(){
                var layer = me.filterForm.layer; 
                var downloader = layer.get('downloader');
                var renderer = layer.get('renderer');
                if (downloader) {// && renderer.getHasData() -> VT: It is too confusing when the download will be active. We will treat it as always active to 
                                 // make it easier for the user.
                    //We need a copy of the current filter object (in case the user
                    //has filled out filter options but NOT hit apply filter) and
                    //the original filter objects
                    var renderedFilterer = layer.get('filterer').clone();
                    var currentFilterer = Ext.create('portal.layer.filterer.Filterer', {});
                    var currentFilterForm = layer.get('filterForm');

                    currentFilterer.setSpatialParam(me._map.getVisibleMapBounds(), true);
                    currentFilterForm.writeToFilterer(currentFilterer);

                    //Finally pass off the download handling to the appropriate downloader (if it exists)
                    var onlineResources = layer.getAllOnlineResources();
                    downloader.downloadData(layer, onlineResources, renderedFilterer, currentFilterer);

                }
            }
        });
        
        return downloadLayerAction
    },
    
    
    _getDeleteAction : function(){
        var me = this;
        var downloadLayerAction = new Ext.Action({
            text : 'Remove Layer',
            iconCls : 'trash',
            handler : function(){
                var layer = me.filterForm.layer; 
                ActiveLayerManager.removeLayer(layer);
                me.menuFactory.layerRemoveHandler(layer);
            }
        });
        
        return downloadLayerAction;
    },
    
    _setVisibilityAction : function(){
        var me = this;
        var visibleLayerAction = new Ext.Action({
            text : 'Toggle Layer Visibility OFF',
            iconCls : 'visible_eye',
            handler : function(){
                var layer = me.filterForm.layer;                 
                layer.setLayerVisibility(!layer.visible);
                if(layer.visible){
                    this.setText('Toggle Layer Visibility OFF');
                }else{
                    this.setText('Toggle Layer Visibility ON');
                }
                
            }
        });
        
        return visibleLayerAction;
    },


    /**
     * Internal handler for when the user clicks 'Apply Filter'.
     *
     * Simply updates the appropriate layer filterer. It's the responsibility
     * of renderers/layers to listen for filterer updates.
     */
    _onAddLayer : function() {

        var layer = this.filterForm.layer;    
        
        var filterer = layer.get('filterer');              
        
        //Before applying filter, update the spatial bounds (silently)
        try {
            filterer.setSpatialParam(this._map.getVisibleMapBounds(), true);
            this.filterForm.writeToFilterer(filterer);
        } catch (e) {
            console.log(e);
        }
        
        ActiveLayerManager.addLayer(layer);
        
        this._showConstraintWindow(layer);
        this._showNagiosAlerts(layer);
        this.menuFactory.layerAddHandler(layer);

        //VT: Tracking
        
        portal.util.GoogleAnalytic.trackevent('Add:' + layer.get('sourceType'), 'Layer:' + layer.get('name'),'Filter:' + Ext.encode(filterer.getParameters())); 
        
    },   
    
    _showNagiosAlerts : function(layer){
        if(this.nagiosAlertShown){
            return;
        }
        
        if (layer.get('sourceType') === portal.layer.Layer.KNOWN_LAYER &&
            layer.get('source').containsNagiosFailures()) {
            var failingHosts = layer.get('source').get('nagiosFailingHosts');
            
            var message = 'Please be aware that some of the services underpinning this layer have recently been reported as being unstable. The unstable hosts will be not be queried. The hosts reported to be experiencing problems are:<br><ul>';
            Ext.each(failingHosts, function(host) {
                message += '<li><b>' + host + '</b></li>';
            });
            message += '</ul>';
            
            Ext.MessageBox.show({
                title: 'Service Problems',
                message: message,
                icon: Ext.Msg.WARNING
            });
        }
        
        this.nagiosAlertShown = true;
    },
    
    _showConstraintWindow : function(layer){
        if(this.constraintShown){
            return;
        }
        var cswRecords = layer.get('cswRecords');
        for (var i = 0; i < cswRecords.length; i++) {
            if (cswRecords[i].hasConstraints()) {
                var popup = Ext.create('portal.widgets.window.CSWRecordConstraintsWindow', {
                    width : 625,
                    cswRecords : cswRecords
                });

                popup.show();

                  //HTML images may take a moment to load which stuffs up our layout
                  //This is a horrible, horrible workaround.
                var task = new Ext.util.DelayedTask(function(){
                    popup.doLayout();
                });
                task.delay(1000);

                break;
            }
        }
        this.constraintShown = true;
    },

    /**
     * Internal handler for when the user clicks 'Reset Filter'.
     *
     * Using the reset method from Ext.form.Basic. All fields in
     * the form will be reset. However, any record bound by loadRecord
     * will be retained.
     */
    _onResetFilter : function() {
        var baseFilterForm = this.getLayout().getActiveItem();
        baseFilterForm.getForm().reset();
    },

   

    clearFilter : function(){
        var layout = this.getLayout();

        //Remove custom CSS styles for filter button
        //this._filterButton.getEl().removeCls("applyFilterCls");

        //Disable the filter and reset buttons (set to default values)
        //this._filterButton.setDisabled(true);
        //this._resetButton.setDisabled(true);

        //Close active item to prevent memory leak
        var actvItem = layout.getActiveItem();
        if (actvItem) {
            actvItem.close();
        }
        layout.setActiveItem(this._emptyCard);
    }
});
/**
 * A FormFactory is a Factory class for generating instances of portal.layer.filterer.forms.BaseFilterForm
 * that are appropriate for a given portal.layer.Layer
 */
Ext.define('portal.widgets.FilterPanelMenuFactory', {
    extend : 'Ext.util.Observable',
       
    constructor : function(config) {        
        this.callParent(arguments);
    },
   
    /**
     * Given an portal.layer.Layer, work out whether there is an appropriate portal.layer.filterer.BaseFilterForm to show
     *
     * function(layer)
     *
     * layer - a portal.layer.Layer
     *
     * Returns a response in the form
     * {
     *    form : Ext.FormPanel - the formpanel to be displayed when this layer is selected (can be EmptyFilterForm)
     *    supportsFiltering : boolean - whether this formpanel supports the usage of the filter button
     *    layer : portal.layer.Layer that was used to generate this object
     * }
     *
     */
    appendAdditionalActions : portal.util.UnimplementedFunction,

    /**
     * Given an portal.layer.Layer, work out whether there is necessary to append additional style url params.
     *
     * function(layer)
     *
     * layer - a portal.layer.Layer
     *
     * Returns sldConfig
     * {
     *    sldUrl : styleUrl with params
     *    isSld_body : boolean - whether styleUrl is for SLD_BODY or SLD in legend query
     * }
     *
     */
    appendAdditionalLegendParams : portal.util.UnimplementedFunction
    
});/**
 * A FormFactory is a Factory class for generating instances of portal.layer.filterer.forms.BaseFilterForm
 * that are appropriate for a given portal.layer.Layer
 */
Ext.define('portal.layer.filterer.FormFactory', {
    extend : 'Ext.util.Observable',

    map : null, //an instance of portal.util.gmap.GMapWrapper

    /**
     * map : [Required] an instance of portal.util.gmap.GMapWrapper
     */
    constructor : function(config) {
        this.map = config.map;
        this.callParent(arguments);
    },

    /**
     * Utility function for creating the return type
     */
    _generateResult : function(form, supportsFiltering) {
        return {
            form                : form,
            supportsFiltering   : supportsFiltering
        };
    },

    /**
     * Given an portal.layer.Layer, work out whether there is an appropriate portal.layer.filterer.BaseFilterForm to show
     *
     * function(layer)
     *
     * layer - a portal.layer.Layer
     *
     * Returns a response in the form
     * {
     *    form : Ext.FormPanel - the formpanel to be displayed when this layer is selected (can be EmptyFilterForm)
     *    supportsFiltering : boolean - whether this formpanel supports the usage of the filter button
     *    layer : portal.layer.Layer that was used to generate this object
     * }
     *
     */
    getFilterForm : portal.util.UnimplementedFunction
});/**
 * Control for allow a user to draw a bounding box representing an area to be selected for subdivision
 *
 * Adapted from: GmapSubsetControl Class v1.3 - original copyright 2005-2007, Andre Lewis, andre@earthcode.com
 *
 */
function GmapSubsetControl(dragEndCallback) {
  this.globals = {
      buttonStyle: {
        width: '80px',
        border: '1px solid black',
        padding: '1px',
        borderWidth: '1px',
        fontFamily: 'Arial,sans-serif',
        fontSize: '12px',
        textAlign: 'center',
        background: '#A0D4FF'
      },

      buttonSubsetStyle : {
        background: '#FF0'
      },

      buttonHtml : 'Select Data',
      buttonId : 'gmap-subset-control',
      buttonSubsetHtml : 'Click and drag a region of interest',

      boxStyle : {
        outlineWidth : 2,
        opacity: .2,
          fillColor: "#000",
          border: "2px solid blue",
          outlineColor : 'blue',
          alphaIE : 'alpha(opacity=20)'
      },

      minDragSize: 0,
      overlayRemoveTime: 6000,

      dragEndCallback : dragEndCallback
  };
};

GmapSubsetControl.prototype = new GControl();

/**
 * Sets button mode to zooming or otherwise, changes CSS & HTML.
 * @param {String} mode Either "zooming" or not.
 */
GmapSubsetControl.prototype.setButtonMode_ = function(mode){
  var G = this.globals;
  if (mode == 'subset') {
    G.buttonDiv.innerHTML = G.buttonSubsetHtml;
    GmapSubsetUtil.style([G.buttonDiv], G.buttonStyle);
    GmapSubsetUtil.style([G.buttonDiv], G.buttonSubsetStyle);
  } else {
    G.buttonDiv.innerHTML = G.buttonHtml;
    GmapSubsetUtil.style([G.buttonDiv], G.buttonStyle);
  }
};


GmapSubsetControl.prototype.initButton_ = function(buttonContainerDiv) {
  var G = this.globals;
  var buttonDiv = document.createElement('div');
  buttonDiv.innerHTML = G.buttonHtml;
  buttonDiv.id = G.buttonId;
  GmapSubsetUtil.style([buttonDiv], {cursor: 'pointer', zIndex:200});
  GmapSubsetUtil.style([buttonDiv], G.buttonStyle);
  buttonContainerDiv.appendChild(buttonDiv);
  return buttonDiv;
};

GmapSubsetControl.prototype.initialize = function(map) {
  var G = this.globals;
  var me = this;
  var mapDiv = map.getContainer();

  // Create div for selection box button
  var buttonContainerDiv = document.createElement("div");
  GmapSubsetUtil.style([buttonContainerDiv], {cursor: 'pointer', zIndex: 150});

  // create and init the zoom button
  //DOM:button
  var buttonDiv = this.initButton_(buttonContainerDiv);

  // Add the two buttons to the map
  mapDiv.appendChild(buttonContainerDiv);

  //DOM:map covers
  var zoomDiv = document.createElement("div");
  var DIVS_TO_CREATE = ['outlineDiv', 'cornerTopDiv', 'cornerLeftDiv', 'cornerRightDiv', 'cornerBottomDiv'];
  for (var i=0; i<DIVS_TO_CREATE.length; i++) {
    var id = DIVS_TO_CREATE[i];
    var div = document.createElement("div");
    GmapSubsetUtil.style([div], {position: 'absolute', display: 'none'});
    zoomDiv.appendChild(div);
    G[id] = div;
  }
  GmapSubsetUtil.style([zoomDiv], {position: 'absolute', display: 'none', overflow: 'hidden', cursor: 'crosshair', zIndex: 101});
  mapDiv.appendChild(zoomDiv);

  // add event listeners
  GEvent.addDomListener(buttonDiv, 'click', function(e) {
    me.buttonclick_(e);
  });
  GEvent.addDomListener(zoomDiv, 'mousedown', function(e) {
    me.coverMousedown_(e);
  });
  GEvent.addDomListener(document, 'mousemove', function(e) {
    me.drag_(e);
  });
  GEvent.addDomListener(document, 'mouseup', function(e) {
    me.mouseup_(e);
  });

  //get globals
  G.mapPosition = GmapSubsetUtil.getElementPosition(mapDiv);
  G.buttonDiv = buttonDiv;
  G.mapCover = zoomDiv;
  G.map = map;

  G.borderCorrection = G.boxStyle.outlineWidth * 2;
  this.setDimensions_();

  //styles
  this.initStyles_();

  // disable text selection on map cover
  G.mapCover.onselectstart = function() {return false;};

  return buttonContainerDiv;
};

/**
 * Required by GMaps API for controls.
 * @return {GControlPosition} Default location for control
 */
GmapSubsetControl.prototype.getDefaultPosition = function() {
  return new GControlPosition(G_ANCHOR_TOP_LEFT, new GSize(3, 120));
};

/**
 * Function called when mousedown event is captured.
 * @param {Object} e
 */
GmapSubsetControl.prototype.coverMousedown_ = function(e){
  var G = this.globals;
  var pos = this.getRelPos_(e);
  G.startX = pos.left;
  G.startY = pos.top;

  GmapSubsetUtil.style([G.mapCover], {background: 'transparent', opacity: 1, filter: 'alpha(opacity=100)'});
  GmapSubsetUtil.style([G.outlineDiv], {left: G.startX + 'px', top: G.startY + 'px', display: 'block', width: '1px', height: '1px'});
  G.draggingOn = true;

  G.cornerTopDiv.style.top = (G.startY - G.mapHeight) + 'px';
  G.cornerTopDiv.style.display ='block';
  G.cornerLeftDiv.style.left = (G.startX - G.mapWidth) +'px';
  G.cornerLeftDiv.style.top = G.startY + 'px';
  G.cornerLeftDiv.style.display = 'block';

  G.cornerRightDiv.style.left = G.startX + 'px';
  G.cornerRightDiv.style.top = G.startY + 'px';
  G.cornerRightDiv.style.display = 'block';
  G.cornerBottomDiv.style.left = G.startX + 'px';
  G.cornerBottomDiv.style.top = G.startY + 'px';
  G.cornerBottomDiv.style.width = '0px';
  G.cornerBottomDiv.style.display = 'block';

  return false;
};

/**
 * Function called when drag event is captured
 * @param {Object} e
 */
GmapSubsetControl.prototype.drag_ = function(e){
  var G = this.globals;
  if(G.draggingOn) {
    var pos = this.getRelPos_(e);
    var rect = this.getRectangle_(G.startX, G.startY, pos, G.mapRatio);

    if (rect.left) {
      addX = -rect.width;
    } else {
      addX = 0;
    }

    if (rect.top) {
      addY = -rect.height;
    } else {
      addY = 0;
    }

    GmapSubsetUtil.style([G.outlineDiv], {left: G.startX + addX + 'px', top: G.startY + addY + 'px', display: 'block', width: '1px', height: '1px'});

    G.outlineDiv.style.width = rect.width + "px";
    G.outlineDiv.style.height = rect.height + "px";

    G.cornerTopDiv.style.height = ((G.startY + addY) - (G.startY - G.mapHeight)) + 'px';
    G.cornerLeftDiv.style.top = (G.startY + addY) + 'px';
    G.cornerLeftDiv.style.width = ((G.startX + addX) - (G.startX - G.mapWidth)) + 'px';
    G.cornerRightDiv.style.top = G.cornerLeftDiv.style.top;
    G.cornerRightDiv.style.left = (G.startX + addX + rect.width + G.borderCorrection) + 'px';
    G.cornerBottomDiv.style.top = (G.startY + addY + rect.height + G.borderCorrection) + 'px';
    G.cornerBottomDiv.style.left = (G.startX - G.mapWidth + ((G.startX + addX) - (G.startX - G.mapWidth))) + 'px';
    G.cornerBottomDiv.style.width = (rect.width + G.borderCorrection) + 'px';

    return false;
  }
};

/**
 * Function called when mouseup event is captured
 * @param {Event} e
 */
GmapSubsetControl.prototype.mouseup_ = function(e){
  var G = this.globals;
  if (G.draggingOn) {
    var pos = this.getRelPos_(e);
    G.draggingOn = false;

    var rect = this.getRectangle_(G.startX, G.startY, pos, G.mapRatio);

    if (rect.left) rect.endX = rect.startX - rect.width;
    if (rect.top) rect.endY = rect.startY - rect.height;

    this.resetDragZoom_();

    if (rect.width >= G.minDragSize && rect.height >= G.minDragSize) {
      var nwpx = new GPoint(rect.startX, rect.startY);
      var nepx = new GPoint(rect.endX, rect.startY);
      var sepx = new GPoint(rect.endX, rect.endY);
      var swpx = new GPoint(rect.startX, rect.endY);
      var nw = G.map.fromContainerPixelToLatLng(nwpx);
      var ne = G.map.fromContainerPixelToLatLng(nepx);
      var se = G.map.fromContainerPixelToLatLng(sepx);
      var sw = G.map.fromContainerPixelToLatLng(swpx);

      var zoomAreaPoly = new GPolyline([nw, ne, se, sw, nw], G.boxStyle.outlineColor, G.boxStyle.outlineWidth + 1,.4);

      try{
        G.map.addOverlay(zoomAreaPoly);
        setTimeout (function() {G.map.removeOverlay(zoomAreaPoly);}, G.overlayRemoveTime);
      }catch(e) {}

      var polyBounds = zoomAreaPoly.getBounds();
      var ne = polyBounds.getNorthEast();
      var sw = polyBounds.getSouthWest();
      var se = new GLatLng(sw.lat(), ne.lng());
      var nw = new GLatLng(ne.lat(), sw.lng());

      // invoke callback if provided
      if (G.dragEndCallback) {
        G.dragEndCallback(nw, ne, se, sw, nwpx, nepx, sepx, swpx);
      }
    }
  }
};

/**
 * Set the cover sizes according to the size of the map
 */
GmapSubsetControl.prototype.setDimensions_ = function() {
  var G = this.globals;
  var mapSize = G.map.getSize();
  G.mapWidth  = mapSize.width;
  G.mapHeight = mapSize.height;
  G.mapRatio  = G.mapHeight / G.mapWidth;
  // set left:0px in next <div>s in case we inherit text-align:center from map <div> in IE.
  GmapSubsetUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv],
      {top: '0px', left: '0px', width: G.mapWidth + 'px', height: G.mapHeight +'px'});
};

/**
 * Initializes styles based on global parameters
 */
GmapSubsetControl.prototype.initStyles_ = function(){
  var G = this.globals;
  GmapSubsetUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv],
    {filter: G.boxStyle.alphaIE, opacity: G.boxStyle.opacity, background:G.boxStyle.fillColor});
  G.outlineDiv.style.border = G.boxStyle.border;
};

/**
 * Function called when the zoom button's click event is captured.
 */
GmapSubsetControl.prototype.buttonclick_ = function(){
  var G = this.globals;
  if (G.mapCover.style.display == 'block') { // reset if clicked before dragging
    this.resetDragZoom_();
  } else {
    this.initCover_();
  }
};

/**
 * Shows the cover over the map
 */
GmapSubsetControl.prototype.initCover_ = function(){
  var G = this.globals;
  G.mapPosition = GmapSubsetUtil.getElementPosition(G.map.getContainer());
  this.setDimensions_();
  this.setButtonMode_('subset');
  GmapSubsetUtil.style([G.mapCover], {display: 'block', background: G.boxStyle.fillColor});
  GmapSubsetUtil.style([G.outlineDiv], {width: '0px', height: '0px'});
};

/**
 * Gets position of the mouse relative to the map
 * @param {Object} e
 */
GmapSubsetControl.prototype.getRelPos_ = function(e) {
  var pos = GmapSubsetUtil.getMousePosition(e);
  var G = this.globals;
  return {top: (pos.top - G.mapPosition.top),
          left: (pos.left - G.mapPosition.left)};
};

/**
 * Figures out the rectangle the user's trying to draw
 * @param {Number} startX
 * @param {Number} startY
 * @param {Object} pos
 * @param {Number} ratio
 * @return {Object} Describes the rectangle
 */
GmapSubsetControl.prototype.getRectangle_ = function(startX, startY, pos, ratio) {
  var left = false;
  var top = false;
  var dX = pos.left - startX;
  var dY = pos.top - startY;

  var rect = {
    startX: Math.min(startX, pos.left),
    startY: Math.min(startY, pos.top),
    endX: Math.max(startX, pos.left),
    endY: Math.max(startY, pos.top),
    left:left,
    top:top
  };

  rect.width = rect.endX - rect.startX;
  rect.height = rect.endY - rect.startY;
  return rect;
};

/**
 * Resets CSS and button display when drag zoom done
 */
GmapSubsetControl.prototype.resetDragZoom_ = function() {
  var G = this.globals;
  GmapSubsetUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv],
    {display: 'none', opacity: G.boxStyle.opacity, filter: G.boxStyle.alphaIE});
  G.outlineDiv.style.display = 'none';
  this.setButtonMode_('normal');
};

var GmapSubsetUtil={};

/**
 * Alias function for getting element by id
 * @param {String} sId
 * @return {Object} DOM object with sId id
 */
GmapSubsetUtil.gE = function(sId) {
  return document.getElementById(sId);
};

/**
 * A general-purpose function to get the absolute position
 * of the mouse.
 * @param {Object} e  Mouse event
 * @return {Object} Describes position
 */
GmapSubsetUtil.getMousePosition = function(e) {
  var posX = 0;
  var posY = 0;
  if (!e) var e = window.event;
  if (e.pageX || e.pageY) {
    posX = e.pageX;
    posY = e.pageY;
  } else if (e.clientX || e.clientY){
    posX = e.clientX +
      (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
    posY = e.clientY +
      (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
  }
  return {left: posX, top: posY};
};

/**
 * Gets position of element
 * @param {Object} element
 * @return {Object} Describes position
 */
GmapSubsetUtil.getElementPosition = function(element) {
  var leftPos = element.offsetLeft;          // initialize var to store calculations
  var topPos = element.offsetTop;            // initialize var to store calculations
  var parElement = element.offsetParent;     // identify first offset parent element
  while (parElement != null ) {                // move up through element hierarchy
    leftPos += parElement.offsetLeft;      // appending left offset of each parent
    topPos += parElement.offsetTop;
    parElement = parElement.offsetParent;  // until no more offset parents exist
  }
  return {left: leftPos, top: topPos};
};

/**
 * Applies styles to DOM objects
 * @param {String/Object} elements Either comma-delimited list of ids
 *   or an array of DOM objects
 * @param {Object} styles Hash of styles to be applied
 */
GmapSubsetUtil.style = function(elements, styles){
  if (typeof(elements) == 'string') {
    elements = GmapSubsetUtil.getManyElements(elements);
  }
  for (var i = 0; i < elements.length; i++){
    for (var s in styles) {
      elements[i].style[s] = styles[s];
    }
  }
};

/**
 * Gets DOM elements array according to list of IDs
 * @param {String} elementsString Comma-delimited list of IDs
 * @return {Array} Array of DOM elements corresponding to s
 */
GmapSubsetUtil.getManyElements = function(idsString){
  var idsArray = idsString.split(',');
  var elements = [];
  for (var i = 0; i < idsArray.length; i++){
    elements[elements.length] = GmapSubsetUtil.gE(idsArray[i]);
  };
  return elements;
};/**
 * A class for parsing a GML document into a list of Marker and Overlay primitives.
 */
Ext.define('portal.layer.renderer.wfs.GMLParser', {
    /**
     * portal.map.BaseMap - The map to generate primitives for
     */
    map : null,


    /**
     * Must the following config
     * {
     *  gml - string - a String of KML that will be parsed
     *  map - portal.map.BaseMap - The map to generate primitives for
     * }
     */
    constructor : function(config) {
        this.rootNode = portal.util.xml.SimpleDOM.parseStringToDOM(config.gml);
        this.map = config.map;
        this.callParent(arguments);
    },

    //Given a series of space seperated tuples, return a list of portal.map.Point
    generateCoordList : function(coordsAsString, srsName) {
        var coordinateList = coordsAsString.split(' ');
        var parsedCoordList = [];
        
        for (var i = 0; i < coordinateList.length; i+=2) {
            this.forceLonLat(coordinateList, srsName, i);

            parsedCoordList.push(Ext.create('portal.map.Point', {
                latitude : parseFloat(coordinateList[i + 1]),
                longitude : parseFloat(coordinateList[i])
            }));
        }

        return parsedCoordList;
    },
    
    getSrsName : function(node) {
        var srsName = node.getAttribute("srsName");
        if (Ext.isEmpty(srsName)) {
            srsName = node.getAttribute("srs");
        }
        
        if (!srsName) {
            return '';
        }
        
        return srsName;
    },
    
    /**
     * Forces lon/lat coords into coords (an array). Swaps coords[offset] and coords[offset + 1] if srsName requires it
     */
    forceLonLat : function(coords, srsName, offset) {
        if (!offset) {
            offset = 0;
        }
        
        if (srsName.indexOf('http://www.opengis.net/gml/srs/epsg.xml#4283') == 0 || 
            srsName.indexOf('urn:x-ogc:def:crs:EPSG') == 0) {
            //lat/lon
            var tmp = coords[offset];
            coords[offset] = coords[offset + 1];
            coords[offset + 1] = tmp;
        } else if (srsName.indexOf('EPSG') == 0 ||
                   srsName.indexOf('http://www.opengis.net/gml/srs/epsg.xml') == 0) {
            //lon/lat (no action required)
        } else {
            //fallback to lon/lat
        }
    },

    parseLineString : function(onlineResource, layer, name, description, lineStringNode) {
        var srsName = this.getSrsName(lineStringNode);
        var parsedCoordList = this.generateCoordList(portal.util.xml.SimpleDOM.getNodeTextContent(lineStringNode.getElementsByTagNameNS("*", "posList")[0]), srsName);
        if (parsedCoordList.length === 0) {
            return null;
        }

        //I've seen a few lines come in with start/end points being EXACTLY the same with no other points. These can be ignored
        if (parsedCoordList.length === 2) {
            if (parsedCoordList[0].getLongitude() === parsedCoordList[1].getLongitude() &&
                parsedCoordList[0].getLatitude() === parsedCoordList[1].getLatitude()) {
                return null;
            }
        }
        
        return this.map.makePolyline(name, undefined, onlineResource, layer, parsedCoordList, '#FF0000', 3, 1);
    },

    //Given a root placemark node attempt to parse it as a single point and return it
    //Returns a single portal.map.primitives.Polygon
    parsePolygon : function(onlineResource, layer, name, description, polygonNode) {
        var srsName = this.getSrsName(polygonNode);
        var parsedCoordList = this.generateCoordList(portal.util.xml.SimpleDOM.getNodeTextContent(polygonNode.getElementsByTagNameNS("*", "posList")[0]), srsName);
        if (parsedCoordList.length === 0) {
            return null;
        }
        
        //I've seen a few lines come in with start/end points being EXACTLY the same with no other points. These can be ignored
        if (parsedCoordList.length === 2) {
            if (parsedCoordList[0].getLongitude() === parsedCoordList[1].getLongitude() &&
                parsedCoordList[0].getLatitude() === parsedCoordList[1].getLatitude()) {
                return null;
            }
        }

        return this.map.makePolygon(name, undefined, onlineResource, layer, parsedCoordList, undefined, undefined,0.7,undefined,0.6);
    },

    //Given a root placemark node attempt to parse it as a single point and return it
    //Returns a single portal.map.primitives.Marker
    parsePoint : function(onlineResource, layer, name, description, icon, pointNode) {
        var rawPoints = portal.util.xml.SimpleDOM.getNodeTextContent(pointNode.getElementsByTagNameNS("*", "pos")[0]);
        var coordinates = rawPoints.split(' ');
        if (!coordinates || coordinates.length < 2) {
            return null;
        }
        
        //Workout whether we are lat/lon or lon/lat
        var srsName = this.getSrsName(pointNode);
        this.forceLonLat(coordinates, srsName);

        var lon = coordinates[0];
        var lat = coordinates[1];
        var point = Ext.create('portal.map.Point', {latitude : parseFloat(lat), longitude : parseFloat(lon)});
        return this.map.makeMarker(name, description, undefined, onlineResource, layer, point, icon);
    },

    /**
     * Returns the feature count as reported by the WFS response. Returns null if the count cannot be parsed.
     */
    getFeatureCount : function() {
        var wfsFeatureCollection = portal.util.xml.SimpleDOM.getMatchingChildNodes(this.rootNode, null, "FeatureCollection");
        if (Ext.isEmpty(wfsFeatureCollection)) {
            return null;
        }
        
        var count = parseInt(wfsFeatureCollection[0].getAttribute('numberOfFeatures'));
        if (Ext.isNumber(count)) {
            return count;
        }
        
        return null;
    },
    
    makePrimitives : function(icon, onlineResource, layer) {
        var primitives = [];
        var wfsFeatureCollection = portal.util.xml.SimpleDOM.getMatchingChildNodes(this.rootNode, null, "FeatureCollection");
        
        //Read through our wfs:FeatureCollection and gml:featureMember(s) elements
        if (Ext.isEmpty(wfsFeatureCollection)) {
            return primitives;
        }
        var featureMembers = portal.util.xml.SimpleDOM.getMatchingChildNodes(wfsFeatureCollection[0], null, "featureMembers");
        var features = [];
        
        if (Ext.isEmpty(featureMembers)) {
            featureMembers = portal.util.xml.SimpleDOM.getMatchingChildNodes(wfsFeatureCollection[0], null, "featureMember");
            for (var i = 0; i < featureMembers.length; i++) {
                features.push(featureMembers[i].firstElementChild);
            }
        }else{
            features = featureMembers[0].childNodes;
        }
        
        for(var i = 0; i < features.length; i++) {
            //Pull out some general stuff that we expect all features to have
            var featureNode = features[i]; 
            var name = featureNode.getAttribute('gml:id');
            var description = portal.util.xml.SimpleXPath.evaluateXPath(this.rootNode, featureNode, "gml:description", portal.util.xml.SimpleXPath.XPATH_STRING_TYPE).stringValue;
            if (Ext.isEmpty(description)) {
                description = portal.util.xml.SimpleXPath.evaluateXPath(this.rootNode, featureNode, "gml:name", portal.util.xml.SimpleXPath.XPATH_STRING_TYPE).stringValue;
                if (Ext.isEmpty(description)) {
                    description = name; //resort to gml ID if we have to
                }
            }
            
            //Look for geometry under this feature
            var pointNodes = featureNode.getElementsByTagNameNS("*", "Point");
            var polygonNodes = featureNode.getElementsByTagNameNS("*", "Polygon");
            var lineStringNodes = featureNode.getElementsByTagNameNS("*", "LineString");
            
            //Parse the geometry we found into map primitives
            for (var geomIndex = 0; geomIndex < polygonNodes.length; geomIndex++) {
                mapItem = this.parsePolygon(onlineResource, layer, name, description, polygonNodes[geomIndex]);
                if (mapItem !== null) {
                    primitives.push(mapItem);
                }
            }
            
            for (var geomIndex = 0; geomIndex < pointNodes.length; geomIndex++) {
                mapItem = this.parsePoint(onlineResource, layer, name, description, icon, pointNodes[geomIndex]);
                if (mapItem !== null) {
                    primitives.push(mapItem);
                }
            }
            
            for (var geomIndex = 0; geomIndex < lineStringNodes.length; geomIndex++) {
                mapItem = this.parseLineString(onlineResource, layer, name, description, lineStringNodes[geomIndex]);
                if (mapItem !== null) {
                    primitives.push(mapItem);
                }
            }
        }

        return primitives;
    }
});


/**
 * Utility functions for downloading files
 */
Ext.define('portal.util.GoogleAnalytic', {
    singleton: true
}, function() {


    portal.util.GoogleAnalytic.trackevent = function(catagory,action,label,value) {
        if(typeof _gaq != 'undefined' ){
            if(value){
                _gaq.push(['_trackEvent', catagory, action, label,value]);
            } else {
                _gaq.push(['_trackEvent', catagory, action, label]);
            }
        }

    };


    portal.util.GoogleAnalytic.trackpage = function(page) {
        if(typeof _gaq != 'undefined' ){
            _gaq.push(['_trackPageview', page]);
        }

    };
});/**
 * Utility class for encapsulating the required
 * interface for the portal to interact with the
 * Google Map API v2
 */
Ext.define('portal.map.gmap.GoogleMap', {

    extend : 'portal.map.BaseMap',

    /**
     * See parent definition
     */
    constructor : function(cfg) {
        this.callParent(arguments);
    },

    /**
     * See parent class for information
     */
    makeMarker : function(id, tooltip, sourceCswRecord, sourceOnlineResource, sourceLayer, point, icon) {
        return Ext.create('portal.map.gmap.primitives.Marker', {
            id : id,
            tooltip : tooltip,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            point : point,
            icon : icon
        });
    },

    /**
     * See parent class for information.
     */
    makePolygon : function(id, cswRecord, sourceOnlineResource, sourceLayer, points, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity) {
        return Ext.create('portal.map.gmap.primitives.Polygon', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : cswRecord,
            points : points,
            strokeColor : strokeColor,
            strokeWeight : strokeWeight,
            strokeOpacity : strokeOpacity,
            fillColor : fillColor,
            fillOpacity : fillOpacity
        });
    },

    /**
     * See parent class for information.
     */
    makePolyline : function(id, cswRecord, sourceOnlineResource, sourceLayer, points, strokeColor, strokeWeight, strokeOpacity) {
        return Ext.create('portal.map.gmap.primitives.Polyline', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : cswRecord,
            points : points,
            strokeColor : strokeColor,
            strokeWeight : strokeWeight,
            strokeOpacity : strokeOpacity
        });
    },

    /**
     * See parent class for information.
     */
    makeWms : function(id, sourceCswRecord, sourceOnlineResource, sourceLayer, wmsUrl, wmsLayer, opacity) {
        return Ext.create('portal.map.gmap.primitives.WMSOverlay', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            wmsUrl : wmsUrl,
            wmsLayer : wmsLayer,
            opacity : opacity,
            map : this.map
        });
    },

    /**
     * Renders this map to the specified container.
     */
    renderToContainer : function(container) {
        //Is user's browser suppported by Google Maps?
        if (!GBrowserIsCompatible()) {
            alert('Your browser isn\'t compatible with the Google Map API V2. This portal will not be functional as a result.');
            throw 'IncompatibleBrowser';
        }

        this.container = container;
        this.map = new GMap2(this.container.body.dom);

        /* AUS-1526 search bar. */
        this.map.enableGoogleBar();
        /*
        // Problems, find out how to
        1. turn out advertising
        2. Narrow down location seraches to the current map view
                        (or Australia). Search for Albany retruns Albany, US
        */
        this.map.setUIToDefault();

        //add google earth
        this.map.addMapType(G_SATELLITE_3D_MAP);

        // Toggle between Map, Satellite, and Hybrid types
        this.map.addControl(new GMapTypeControl());

        var startZoom = 4;
        this.map.setCenter(new google.maps.LatLng(-26, 133.3), startZoom);
        this.map.setMapType(G_SATELLITE_MAP);

        //Thumbnail map
        var Tsize = new GSize(150, 150);
        this.map.addControl(new GOverviewMapControl(Tsize));

        this.map.addControl(new DragZoomControl(), new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(345, 7)));

        // Fix for IE/Firefox resize problem (See issue AUS-1364 and AUS-1565 for more info)
        this.map.checkResize();
        container.on('resize', this._onContainerResize, this);

        GEvent.addListener(this.map, "mousemove", Ext.bind(this._onMouseMove, this));
        GEvent.addListener(this.map, "mouseout", Ext.bind(this._onMouseOut, this));
        GEvent.addListener(this.map, "click", Ext.bind(this._onClick, this));

        this.highlightPrimitiveManager = this.makePrimitiveManager();

        //Add data selection box (if required)
        if (this.allowDataSelection) {
            this.map.addControl(new GmapSubsetControl(Ext.bind(function(nw, ne, se, sw) {
                var bbox = Ext.create('portal.util.BBox', {
                  northBoundLatitude : nw.lat(),
                      southBoundLatitude : sw.lat(),
                      eastBoundLongitude : ne.lng(),
                      westBoundLongitude : sw.lng()
                });

                //Iterate all active layers looking for data sources (csw records) that intersect the selection
                var intersectedRecords = this.getLayersInBBox(bbox);

                this.fireEvent('dataSelect', this, bbox, intersectedRecords);
              }, this)), new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(405, 7)));
        }

        this.rendered = true;
    },

    /**
     * See parent for definition.
     */
    getVisibleMapBounds : function() {
        var mapBounds = this.map.getBounds();
        var sw = mapBounds.getSouthWest();
        var ne = mapBounds.getNorthEast();

        return Ext.create('portal.util.BBox', {
            eastBoundLongitude : ne.lng(),
            westBoundLongitude : sw.lng(),
            southBoundLatitude : sw.lat(),
            northBoundLatitude : ne.lat()
        });
    },

    /**
     * See parent for definition.
     */
    makePrimitiveManager : function() {
        return Ext.create('portal.map.gmap.PrimitiveManager', {
            baseMap : this
        });
    },

    /**
     * See Parent class.
     */
    //VT: initFunction doesn't seem to be in use.
    //openInfoWindow : function(windowLocation, width, height, content, initFunction,layer) {
    openInfoWindow : function(windowLocation, width, height, content,layer) {
        if (!Ext.isArray(content)) {
            content = [content];
        }

        //We need to open an info window with a number of tabs for each of the content
        //Each tab will need to have an appropriately sized parent container rendered into it
        //AND once they are all rendered, we need to then add each element of baseComponents
        //to each of the tabs

        //Build our info window content (sans parent containers)
        var infoWindowIds = []; //this holds the unique ID's to bind to
        var infoWindowTabs = []; //this holds GInfoWindowTab instances
        for (var i = 0; i < content.length; i++) {
            var html = null;
            var title = 'HTML';

            infoWindowIds.push(Ext.id());
            if (Ext.isString(content[i])) {
                html = content[i];
            } else {
                title = content[i].tabTitle;
                html = Ext.util.Format.format('<html><body><div id="{0}" style="width: {1}px; height: {2}px;"></div></body></html>', infoWindowIds[i], width, height);
            }
            infoWindowTabs.push(new GInfoWindowTab(title, html));
        }
        var initFunctionParams = { //this will be passed to the info window manager callback
            width : width,
            height : height,
            infoWindowIds : infoWindowIds,
            content : content
        };
        var infoWindowParams = undefined; //we don't dictate any extra info window options

        //Show our info window - create our parent components
        this._openInfoWindowTabs(windowLocation, infoWindowTabs, infoWindowParams, initFunctionParams, function(map, location, params) {
            for (var i = 0; i < params.content.length; i++) {
                if (Ext.isString(params.content[i])) {
                    continue; //HTML tabs need no special treatment
                }

                Ext.create('Ext.container.Container', {
                    renderTo : params.infoWindowIds[i],
                    border : 0,
                    width : params.width,
                    height : params.height,
                    layout : 'fit',
                    items : [params.content[i]],
                    listeners : {
                        //To workaround some display issues with ext JS under Google maps
                        //We need to force a layout of the ExtJS container when the GMap tab
                        //changes. GMap doesn't offer anyway of doing that so we instead monitor
                        //the underlying DOM for style changes referencing the 'display' CSS attribute.
                        //See: http://www.sencha.com/forum/showthread.php?186027-Ext-4.1-beta-3-Strange-layout-on-grids-rendered-into-elements-with-display-none&p=752916#post752916
                        afterrender : function(container) {
                            //Find the parent info window DOM
                            var el = container.getEl();
                            var tabParentDiv = el.findParentNode('div.gmnoprint', 10, true);
                            var headerParentDiv = tabParentDiv.findParentNode('div.gmnoprint', 10, true);

                            //Firstly get all child div's (these are our tabs).
                            //This tells us how many headers there should be (one for each tab)
                            var tabElements = tabParentDiv.select('> div');
                            var tabElementsArr = [];
                            tabElements.each(function(div) {
                                tabElementsArr.push(div.dom);   //don't store a reference to div, it's the Ext.flyWeight el. Use div.dom
                            });

                            //Now there are a lot of divs under the header parent, we are interested
                            //in the last N (which represent the N headers of the above tabs)
                            var allParentDivs = headerParentDiv.select('> div');
                            var allParentDivsArr = [];
                            allParentDivs.each(function(div) {
                                allParentDivsArr.push(div.dom); //don't store a reference to div, it's the Ext.flyWeight el. Use div.dom
                            });
                            var headerDivsArr = allParentDivsArr.slice(allParentDivsArr.length - tabElementsArr.length);

                            //Start iterating from the second index - the first tab will never need a forced layout
                            for (var i = 1; i < headerDivsArr.length; i++) {
                                var headerDiv = new Ext.Element(headerDivsArr[i]);
                                var tabDiv = new Ext.Element(tabElementsArr[i]);

                                headerDiv.on('click', Ext.bind(function(e, t, eOpts, headerElement, tabElement) {
                                    //Find the container which belongs to t
                                    for (var i = 0; i < params.content.length; i++) {
                                        var container = params.content[i];
                                        var containerElId = container.getEl().id;

                                        //Only layout the child of the element firing the event (i.e. the tab
                                        //which is visible)
                                        var matchingElements = tabElement.select(Ext.util.Format.format(':has(#{0})', containerElId));
                                        if (matchingElements.getCount() > 0) {
                                            //Only perform the layout once for performance reasons
                                            if (!container._portalTabLayout) {
                                                container._portalTabLayout = true;
                                                container.doLayout();
                                            }
                                        }
                                    }
                                }, this, [headerDiv, tabDiv], true));
                            }
                        }
                    }
                });
            }
        });
        this.openedInfoLayerId=layer.get('id');
    },

    /**
     * See parent class
     */
    scrollToBounds : function(bbox) {
        var sw = new GLatLng(bbox.southBoundLatitude, bbox.westBoundLongitude);
        var ne = new GLatLng(bbox.northBoundLatitude, bbox.eastBoundLongitude);
        var layerBounds = new GLatLngBounds(sw,ne);

        //Adjust zoom if required
        var visibleBounds = this.map.getBounds();
        this.map.setZoom(this.map.getBoundsZoomLevel(layerBounds));

        //Pan to position
        var layerCenter = layerBounds.getCenter();
        this.map.panTo(layerCenter);
    },

    /**
     * See parent class
     */
    getZoom : function() {
        return this.map.getZoom();
    },

    /**
     * See parent class
     */
    setZoom : function(zoom) {
        return this.map.setZoom(zoom);
    },

    /**
     * See parent class.
     */
    setCenter : function(point) {
        this.map.panTo(new GLatLng(point.getLatitude(), point.getLongitude()));
    },

    /**
     * See parent class.
     */
    getCenter : function() {
        var c = this.map.getCenter();
        return Ext.create('portal.map.Point', {
            latitude : c.lat(),
            longitude : c.lng()
        });
    },

    /**
     * See parent class.
     */
    getTileInformationForPoint : function(point) {
        var latitude = point.getLatitude();
        var longitude = point.getLongitude();
        var info = Ext.create('portal.map.TileInformation', {});

        var zoom = this.map.getZoom();
        var mapType = this.map.getCurrentMapType();

        var tileSize = mapType.getTileSize();
        info.setWidth(tileSize);
        info.setHeight(tileSize);

        var currentProj = mapType.getProjection();
        var point = currentProj.fromLatLngToPixel(new GLatLng(latitude, longitude),zoom);
        var tile = {
            x : Math.floor(point.x / tileSize),
            y : Math.floor(point.y / tileSize)
        };

        var sw = currentProj.fromPixelToLatLng(new GPoint(tile.x*tileSize, (tile.y+1)*tileSize), zoom);
        var ne = currentProj.fromPixelToLatLng(new GPoint((tile.x+1)*tileSize, tile.y*tileSize), zoom);

        info.setTileBounds(Ext.create('portal.util.BBox', {
            northBoundLatitude : ne.lat(),
            southBoundLatitude : sw.lat(),
            eastBoundLongitude : ne.lng(),
            westBoundLongitude : sw.lng()
        }));

        info.setOffset({
            x : point.x % tileSize,
            y : point.y % tileSize
        });

        return info;
    },

    /**
     * Returns an portal.map.Size object representing the map size in pixels in the form
     *
     * function()
     */
    getMapSizeInPixels : function() {
        var size = this.map.getSize();
        return Ext.create('portal.map.Size', {
            width : size.width,
            height : size.height
        });
    },

    /**
     * See parent
     */
    getPixelFromLatLng : function(point) {
        var latLng = new GLatLng(point.getLatitude(), point.getLongitude());
        return this.map.fromLatLngToContainerPixel(latLng);
    },

    /**
     * Update the Lat/Long text in the HTML
     */
    _onMouseMove : function(latlng) {
        var latStr = "<b>Long:</b> " + latlng.lng().toFixed(6) +
                     "&nbsp&nbsp&nbsp&nbsp" +
                     "<b>Lat:</b> " + latlng.lat().toFixed(6);
        document.getElementById("latlng").innerHTML = latStr;
    },

    /**
     * Remove the lat/long text from the HTML
     */
    _onMouseOut : function(latlng) {
        document.getElementById("latlng").innerHTML = "";
    },

    /**
     * Ensure our map fills the parent container at all times
     */
    _onContainerResize : function() {
        this.map.checkResize();
    },

    /**
     * When the map is clicked, convert that into a click event the portal can understand and offload it
     */
    _onClick : function(overlay, latlng, overlayLatlng) {
        var queryTargets = portal.map.gmap.ClickController.generateQueryTargets(overlay, latlng, overlayLatlng, this.layerStore);
        this.fireEvent('query', this, queryTargets);
    },

    /**
     * If the removal of a layer has the same ID has a info window opened, close it.
     */
    closeInfoWindow: function(layerid){
        if(layerid === this.openedInfoLayerId){
          this.map.closeInfoWindow();
        }
    },


    /**
     * Opens an info window at a location with the specified content. When the window loads initFunction will be called
     *
     * windowLocation - either a portal.map.gmap.primitives.Marker or a portal.map.Point which is where the window will be opened from
     * content - A HTML string representing the content of the window OR a array of GInfoWindowTab
     * infoWindowOpts - [Optional] an instance of GInfoWindowOptions that will be passed to the new window
     * initFunction - [Optional] function(map, location, initFunctionParam) a function that will be called with initFunctionParam when the window opens
     * initFunctionParam - [Optional] will be passed as a parameter to initFunction
     */
    _openInfoWindowTabs : function(windowLocation, content, infoWindowOpts, initFunctionParam, initFunction) {
        //We listen for the open event once
        var scope = this;
        var listenerHandler = null;
        var listenerFunction = function() {
            GEvent.removeListener(listenerHandler);

            if (initFunction) {
                initFunction(scope, content, initFunctionParam);
            }
        };

        //Figure out which function to call based upon our parameters
        if (windowLocation instanceof portal.map.Point) {
            var latLng = new GLatLng(windowLocation.getLatitude(), windowLocation.getLongitude());
            listenerHandler = GEvent.addListener(this.map, "infowindowopen", listenerFunction);

            if (content instanceof Array) {
                this.map.openInfoWindowTabs(latLng, content, infoWindowOpts);
            } else if (typeof(content) === "string") {
                this.map.openInfoWindowHtml(latLng, content, infoWindowOpts);
            }
        } else if (windowLocation instanceof portal.map.gmap.primitives.Marker) {
            var gMarker = windowLocation.getMarker();
            listenerHandler = GEvent.addListener(gMarker, "infowindowopen", listenerFunction);

            if (content instanceof Array) {
                gMarker.openInfoWindowTabs(content, infoWindowOpts);
            } else if (typeof(content) === "string") {
                gMarker.openInfoWindowHtml(content, infoWindowOpts);
            }
        }
    }
});/**
 * Ext.panel.Panel extensions to emulate the display of a grid panel group for a CommonBaseRecordPanel widget.
 * 
 * It will be used to grouping instances of portal.widgets.panel.RecordRowPanel 
 * 
 * The grid panel was deprecated as part of AUS-2685
 */
Ext.define('portal.widgets.panel.recordpanel.GroupPanel', {
    extend : 'portal.widgets.panel.recordpanel.AbstractChild',
    xtype : 'recordgrouppanel',

    /**
     * 
     */
    constructor : function(config) {
        var plugins = Ext.isEmpty(config.plugins) ? [] : config.plugins;
        plugins.push('collapsedaccordian');
        
        Ext.apply(config, {
            collapsed: true,
            groupMode: true,
            bodyPadding: '0 0 0 0',
            header: {
                style: 'cursor: pointer;',
                padding: '8 4 8 4'
            },
            layout: {
                type: 'accordion',
                hideCollapseTool: true,
                fill: false
            },
            plugins: plugins
        });
        this.callParent(arguments);
    },
    
    /**
     * Recalculates the visible item count for this group
     */
    refreshTitleCount: function() {
        this.setTitle(this.rawTitle);
    },
    
    /**
     * Overrides the normal implementation by adding a visible item count 
     */
    setTitle: function(title) {
        var visibleItemCount = 0;
        
        //We have to adjust our item count algorithm based on whether
        //we are dealing with a constructed widget or a config object
        if (this.items instanceof Ext.util.AbstractMixedCollection) {
            this.items.each(function(cmp) {
                if (!cmp.isHidden()) {
                    visibleItemCount++;
                }
            });
        } else {
            Ext.each(this.items, function(cmp) {
                if (Ext.isBoolean(cmp.hidden)) {
                    if (cmp.hidden) {
                        return;
                    }
                } else if (Ext.isBoolean(cmp.visible)) {
                    if (!cmp.visible) {
                        return;
                    }
                }
                visibleItemCount++;
            });
        }
        
        this.rawTitle = title;
        this.visibleItemCount = visibleItemCount;
        title = Ext.util.Format.format('{0} ({1} item{2})', title, visibleItemCount, (visibleItemCount != 1 ? 's' : ''));
        return this.callParent([title]);
    }
});/*
 * Call generic wms service for GoogleMaps v2
 * John Deck, UC Berkeley
 * Inspiration & Code from:
 *	Mike Williams:
 * http://www.econym.demon.co.uk/googlemaps2/ V2 Reference & custommap code
 *	Brian Flood:
 * http://www.spatialdatalogic.com/cs/blogs/brian_flood/archive/2005/07/11/39.aspx V1 WMS code
 *	Kyle Mulka:
 * http://blog.kylemulka.com/?p=287  V1 WMS code modifications
 * http://search.cpan.org/src/RRWO/GPS-Lowrance-0.31/lib/Geo/Coordinates/MercatorMeters.pm
 *
 * Modified by Chris Holmes, TOPP to work by default with GeoServer.
 * Modified by Eduin Yesid Carrillo Vega to work with any map name.
 * Modified by Ivan Dubrov for more clean code
 *
 * Note this only works with gmaps v2.36 and above. http://johndeck.blogspot.com
 * has scripts that do the same for older gmaps versions - just change from
 * 54004 to 41001.
 *
 * About:
 * This script provides an implementation of GTileLayer that works with WMS
 * services that provide epsg 41001 (Mercator).  This provides a reasonable
 * accuracy on overlays at most zoom levels.  It switches between Mercator
 * and Lat/Long at the myMercZoomLevel variable, defaulting to MERC_ZOOM_DEFAULT
 * of 5.  It also performs the calculation from updateCSWRecords GPoint to the
 * appropriate BBOX to pass the WMS.  The overlays could be more accurate, and
 * if you figure out a way to make them so please contribute information back to
 * http://docs.codehaus.org/display/GEOSDOC/Google+Maps.  There is much
 * information at:
 * http://cfis.savagexi.com/articles/2006/05/03/google-maps-deconstructed
 *
 * Use:
 * This script is used by creating a new GTileLayer, setting the required
 * and any desired optional variables, and setting the functions here to
 * override the appropriate GTileLayer ones.
 *
 * At the very least you will need:
 * var myTileLayer = new GWMSTileLayer(map, new GCopyrightCollection(""), 1, 17);
 *     myTileLayer.baseURL='http://yourserver.org/wms?'
 *     myTileLayer.layers='myLayerName';
 *
 * After that you can override the format (format), the level at
 * which the zoom switches (mercZoomLevel), and the style (styles)
 * - be sure to put one style for each layer (both are separated by
 * commas). You can also override the Opacity:
 *     myTileLayer.opacity = 0.69
 *
 * Then you can overlay on google maps with something like:
 * var map = new GMap2(document.getElementById("map"));
 * var tileLayer = new GWMSTileLayer(map, new GCopyrightCollection(""), 1, 17);
 * map.addOverlay(new GTileLayerOverlay(tileLayer));
 */

//--- Constructor function
function GWMSTileLayer(map, copyrights,  minResolution,  maxResolution) {

   // GWMSTileLayer inherits from GTileLayer
    GTileLayer.call(this, copyrights, minResolution, maxResolution);

    // Attributes
    this.map = map;
    this.format = "image/png";   // Use PNG by default
    this.opacity = 1.0;

   // Google Maps Zoom level at which we switch from Mercator to Lat/Long.
    this.mercZoomLevel = 4;
}

GWMSTileLayer.prototype = new GTileLayer(new GCopyrightCollection(), 0, 0);

GWMSTileLayer.prototype.MAGIC_NUMBER = 6356752.3142;
GWMSTileLayer.prototype.WGS84_SEMI_MAJOR_AXIS = 6378137.0;
GWMSTileLayer.prototype.WGS84_ECCENTRICITY = 0.0818191913108718138;

GWMSTileLayer.prototype.dd2MercMetersLng = function(longitude) {
    return this.WGS84_SEMI_MAJOR_AXIS * (longitude * Math.PI / 180.0);
};

GWMSTileLayer.prototype.dd2MercMetersLat = function(latitude) {
   var rads = latitude * Math.PI / 180.0;

   return this.WGS84_SEMI_MAJOR_AXIS * Math.log(
        Math.tan((rads+Math.PI/2)/2) *
        Math.pow(((1-this.WGS84_ECCENTRICITY*Math.sin(rads))/(1+this.WGS84_ECCENTRICITY*Math.sin(rads))), this.WGS84_ECCENTRICITY/2));
};

GWMSTileLayer.prototype.isPng = function() {
   return this.format === "image/png";
};

GWMSTileLayer.prototype.getOpacity = function() {
    return this.opacity;
};

GWMSTileLayer.prototype.getTileUrl = function(point, zoom) {
    var mapType = this.map.getCurrentMapType();
    var proj = mapType.getProjection();
    var tileSize = mapType.getTileSize();

    var lowerLeftPix = new GPoint(point.x * tileSize, (point.y+1) * tileSize);
    var upperRightPix = new GPoint((point.x+1) * tileSize, point.y * tileSize);
    var upperRight = proj.fromPixelToLatLng(upperRightPix, zoom);
    var lowerLeft = proj.fromPixelToLatLng(lowerLeftPix, zoom);

    var boundBox = null;
    var srs = null;
    if (this.mercZoomLevel !== 0 && zoom < this.mercZoomLevel) {
        boundBox = Ext.create('portal.util.BBox', {
           crs : 'EPSG:41001',
           westBoundLongitude : this.dd2MercMetersLng(lowerLeft.lng()),
           southBoundLatitude : this.dd2MercMetersLat(lowerLeft.lat()),
           eastBoundLongitude : this.dd2MercMetersLng(upperRight.lng()),
           northBoundLatitude : this.dd2MercMetersLat(upperRight.lat())
        });
    } else {
        boundBox = Ext.create('portal.util.BBox', {
            crs : 'EPSG:4326',
            westBoundLongitude : lowerLeft.lng(),
            southBoundLatitude : lowerLeft.lat(),
            eastBoundLongitude : upperRight.lng(),
            northBoundLatitude : upperRight.lat()
         });
    }

    return portal.map.primitives.BaseWMSPrimitive.getWmsUrl(this.baseURL, this.layers, boundBox, tileSize, tileSize, this.format);
};
/**
 * A plugin for an Ext.panel.Panel class that will modify the header
 * to contain a number of clickable icons (surrounding the header text)
 */
Ext.define('portal.widgets.plugins.HeaderIcons', {
    alias: 'plugin.headericons',
    
    panel : null,
    header : null,
    iconsLeft : null,
    iconsText : null,

    /**
     * Adds the following constructor args
     * {
     *  icons : Object[] - The icons to display in this header (see schema below)
     * }
     * 
     * icon schema:
     * {
     *  location - ['left', 'text'] - Will the icon be aligned to the left or after the header text? 
     *  src - String - the image source href
     *  tip - String - [Optional] tooltip text (if any)
     *  handler - function() - called when the icon is clicked.
     *  style - Mixed - String/Object containing style information for the icon img
     *  width - Number - width of the icon
     *  height - Number - height of the icon
     * }
     */
    constructor : function(cfg) {
        var me = this;
        this.iconsLeft = [];
        this.iconsText = [];
        if (cfg.icons) {
            Ext.each(cfg.icons, function(i) {
                if (i.location === 'left') {
                    me.iconsLeft.push(i);
                } else {
                    me.iconsText.push(i);
                }
            });
        }
        
        this.callParent(arguments);
    },

    _iconCfgToMarkup : function(iconCfg, left, containerHeight) {
        var style = {
            'vertical-align': 'middle',
            'margin-top': '-2px',
            display: 'inline-block'
        };
        if (left) {
            style['margin-right'] = Math.floor((iconCfg.width / 2)) + 'px'
        } else {
            style['margin-left'] = Math.floor((iconCfg.width / 2)) + 'px'
        }
        
        return {
            tag: 'div',
            //'data-qtip': iconCfg.tip,
            style: style,
            children: [{
                tag: 'img',
                width: iconCfg.width,
                height: iconCfg.height,
                style: iconCfg.style ? iconCfg.style : '',
                src: iconCfg.src
            }]  
        };
    },
    
    init: function(panel) {
        if (panel.rendered) {
            this.afterRender(panel);
        } else {
            panel.on('afterrender', this.afterRender, this, {single: true});
        }
    },
    
    afterRender : function(panel) {
        var me = this;
        
        me.panel = panel;
        me.header = panel.getHeader();
        
        
        var headerEl = me.header.getEl();
        var containerEl = headerEl.down('.x-panel-header-title');
        var textEl = headerEl.down('.x-title-text');
        
        var containerHeight = containerEl.getHeight();
        containerEl.setStyle('overflow', 'visible');
        containerEl.setStyle('height', containerHeight); //fix the height to its current size 

        var tipCfg = {
            showDelay: 200,
            dismissDelay: 10000  
        };
        Ext.each(me.iconsLeft, function(iconCfg) {
            var newEl = Ext.DomHelper.insertFirst(textEl, me._iconCfgToMarkup(iconCfg, true, containerHeight), true);
            if (iconCfg.handler) {
                newEl.on('click', iconCfg.handler);
            }
            if (iconCfg.tip) {
                tipCfg.text = iconCfg.tip;
                tipCfg.target = newEl;
                Ext.tip.QuickTipManager.register(tipCfg);
            }
        });
        
        Ext.each(me.iconsText, function(iconCfg) {
            var newEl = textEl.appendChild(me._iconCfgToMarkup(iconCfg, false, containerHeight), false);
            if (iconCfg.handler) {
                newEl.on('click', iconCfg.handler);
            }
            if (iconCfg.tip) {
                tipCfg.text = iconCfg.tip;
                tipCfg.target = newEl;
                Ext.tip.QuickTipManager.register(tipCfg);
            }
        });
    }

});/**
 * Represents information about an Icon that can be reused by
 * some aspects of the map
 */
Ext.define('portal.map.Icon', {

    statics : {
        markerOrder : 0,
        genericMarkers : [
            'http://maps.google.com/mapfiles/ms/micons/ylw-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/blue-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/grn-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/ltblu-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/pink-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/purple-pushpin.png',
            'http://maps.google.com/mapfiles/ms/micons/red-pushpin.png'
        ],
        colorMap : {
            purple : '#9C16F0',
            orange : '#F59B0A',
            wht : '#E8DECF',
            red : '#CF082D',
            pink : '#F249A9',
            grn : '#65ED4A',
            blu : '#442BE3',
            ltblu : '#66EDD4',
            ylw : '#E8E527',
            defaultColor : '#78686C'
        },//VT: This is to generate the right wms color to match the marker icon color.
        mapIconColor : function(url){         
            for(var key in portal.map.Icon.colorMap){
                if(url.indexOf(key)!==-1){
                    return portal.map.Icon.colorMap[key];
                }
            }
        }
    },



    config : {
        /**
         * String - URL of the icon image
         */
        url : '',

        /**
         * Mark this icon has a randomly allocated icon
         */
        isDefault : false,
        /**
         * Number - the width of the icon in pixels
         */
        width : 0,
        /**
         * Number - the height of the icon in pixels
         */
        height : 0,
        /**
         * Number - the offset in pixels (x direction) of the point in the icon which will be anchored (touching) the map.
         */
        anchorOffsetX : 0,
        /**
         * Number - the offset in pixels (y direction) of the point in the icon which will be anchored (touching) the map.
         */
        anchorOffsetY : 0
    },

    /**
     * Accepts the following
     *
     * url : String - URL of the icon image
     * width : Number - the width of the icon in pixels
     * height : Number - the height of the icon in pixels
     * anchorOffsetX : Number - the offset in pixels (x direction) of the point in the icon which will be anchored (touching) the map.
     * anchorOffsetY : Number - the offset in pixels (y direction) of the point in the icon which will be anchored (touching) the map.
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        if(cfg.url && cfg.url.length > 0){
            this.setUrl(cfg.url);
        }else{
            //VT:give this a default marker to use.
            var order=this.self.markerOrder;
            this.setUrl(this.self.genericMarkers[order]);
            if(order == 6){
                this.self.markerOrder = 0;
            }else{
                this.self.markerOrder ++;
            }
            this.setIsDefault(true);
        }
        this.setWidth(cfg.width);
        this.setHeight(cfg.height);
        this.setAnchorOffsetX(cfg.anchorOffsetX);
        this.setAnchorOffsetY(cfg.anchorOffsetY);

    }
});/**
 * A display field extension that makes the data component
 * of the field the most prominent (compared to the actual label).
 *
 * This particular display field is specialised into rendering an image as the data component
 */
Ext.define('portal.widgets.field.ImageDisplayField', {
    alias : 'widget.imagedisplayfield',
    extend : 'Ext.form.field.Display',

    /**
     * This is hardcoded, the label will appear in the 'display' part of the field.
     */
    hideLabel : true,

    fieldCls: Ext.baseCSSPrefix + 'form-image-field',
    labelCls: Ext.baseCSSPrefix + 'form-image-field-label',


    /**
     * URL of the image to render
     */
    imgHref : null,
    /**
     * Width of the image to render in pixels
     */
    imgWidth : null,
    /**
     * Height of the image to render in pixels
     */
    imgHeight : null,
    

    /**
     * This is set in the constructor
     */
    fieldSubTpl : null,

    constructor : function(config) {

        this.imgHref = config.imgHref;
        this.imgWidth = config.imgWidth;
        this.imgHeight = config.imgHeight;
        this.fieldLabel = config.fieldLabel ? config.fieldLabel : '';

        //This template defines the rendering of the data display field (and uom if appropriate)
        //We need to inject the uom values into the template via definitions
        this.fieldSubTpl = [
          '<div id="{id}"',
          '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
          ' class="{fieldCls}">',
          '<img style="margin: auto;" width="{[imgWidth]}" height="{[imgHeight]}" src="{[imgHref]}" alt="Loading..."/>',
          '</div>',
          '<div class="x-form-value-field-label">',
          '{[fieldLabel]}',
          '</div>',{
              compiled: true,
              disableFormats: true,
              //This is where we 'inject' our own variables for use within the template
              definitions : [
                  this.createDefinition('imgHref', this.imgHref),
                  this.createDefinition('imgHeight', this.imgHeight),
                  this.createDefinition('imgWidth', this.imgWidth),
                  this.createDefinition('fieldLabel', this.fieldLabel)
              ]
        }];

        this.callParent(arguments);
    },

    /**
     * Utility function for generating javascript in the form
     * 'var variableName = value'
     */
    createDefinition : function(variableName, value) {
        if (Ext.isString(value)) {
            return Ext.util.Format.format('var {0} = "{1}";', variableName, value);
        } else if (Ext.isObject(value)) {
            //Assume an object is a style object
            return this.createDefinition(variableName, Ext.DomHelper.generateStyles(value));
        } else {
            return Ext.util.Format.format('var {0} = {1};', variableName, value);
        }
    }
});


/**
 * A plugin for an Ext.grid.Panel class that a context menu that
 * shows whenever a row is selected. The menu will render horizontally
 * below the selected row, seemingly "inline"
 *
 * To use this plugin, assign the following field to the plugin constructor
 * {
 *  actions : Ext.Action[] - Actions to be shown/hidden according to row selection.
 *  toggleColIndexes : int[] - Optional - Which column indexes can toggle open/close on single click - Defaults to every column 
 * }
 *
 * Contains two events:
 *  contexthide, contextshow
 *  
 *  Example usage:
 *  
 *  var removeAction = new Ext.Action({
                  text : 'Remove',
                  iconCls : 'remove',
                  handler : function(cmp) {
                      console.log('remove click');
                  }
              });
              
              
              var downloadLayerAction = new Ext.Action({
                  text : 'Download',
                  iconCls : 'download',
                  handler : function(cmp) {
                      console.log('download click');
                  }
              });

    var panel = Ext.create('Ext.grid.Panel', {
                      title : 'Grid Panel Test',
                      store : store,
                      split: true,
                      renderTo: 'foo',
                      plugins : [{
                          ptype : 'inlinecontextmenu',
                          actions : [removeAction,downloadLayerAction]
                      }]
    })

 *
 */
Ext.define('portal.widgets.grid.plugin.InlineContextMenu', {
    extend: 'portal.widgets.grid.plugin.RowExpanderContainer',

    alias: 'plugin.inlinecontextmenu',

    actions : null,
    align : null,

    /**
     * Supported config options
     * {
     *  actions : Ext.Action[] - *required* Actions to be shown/hidden according to row selection.
     *  align : [Optional] String - Choose from values: 'center' 'left' 'right'. Default is 'right' 
     *  toggleColIndexes : int[] - Optional - Which column indexes can toggle open/close on single click - Defaults to every column
     * }
     */
    constructor : function(cfg) {

        cfg.generateContainer = this.generateToolbar;
        cfg.allowMultipleOpen = false;
        this.align = cfg.align ? cfg.align : 'right';
        this.callParent(arguments);
    },

    generateToolbar : function(record, renderTo, grid) {
        var items = [];
        Ext.each(this.actions, function(action) {
            items.push(Ext.create('Ext.button.Button', action));
        });
        
        return Ext.create('Ext.container.Container', {
            renderTo : renderTo,
            items : items,
            defaults : {
                margin : '0 0 0 5'
            },
            padding : '5 10 5 0',
            layout : {
                type : 'anchor'
            },
            style : {
                'text-align' : this.align
            }
         });
    }
});/**
 * Represents a single instruction for the purposes of helping a user.
 *
 */
Ext.define('portal.util.help.Instruction', {
    extend : 'Ext.data.Model',

    fields: [
        { name: 'highlightEl', type: 'auto' }, //The page element which will be highlighted by this particular instruction. This can be a ExtJS element, DOM element or a string id of a DOM element
        { name: 'anchor', defaultValue: 'left', type: 'string'}, //Should the description be anchored to the left/right/top/bottom of the hightlightEl?. Defaults to 'left'
        { name: 'title', type: 'string' }, //The title of this instruction (should be short)
        { name: 'description', type: 'string' } //The longer description of this instruction
    ]
});/**
 * An instruction manager takes a set of portal.util.help.Instruction objects and creates a step by step
 * wizard for highlighting each instruction in sequence.
 */

Ext.require([  
    'Ext.ux.Spotlight'
]);

Ext.define('portal.util.help.InstructionManager',{ 
    
    spot : null, //Ext.ux.Spotlight,
    tip : null, //Ext.tip.ToolTip
    currentInstruction : 0,

    /**
     * Accepts the following {
     *  spotCfg : Applied to the internal Ext.ux.Spotlight. If not specified, Spotlight default values will be used
     * }
     */
    constructor : function(cfg) {
        var spotCfg = cfg.spotCfg ? cfg.spotCfg : {};

        this.spot = Ext.create('Ext.ux.Spotlight', spotCfg);
        this.callParent(arguments);
    },

    /**
     * Shows a single instruction, closes any existing instructions. If index is invalid,
     * any current instruction will be hidden and nothing will be shown.
     */
    _showInstruction : function(index, instructions) {
        
        if(index < instructions.length && index >= 0){
            portal.util.GoogleAnalytic.trackevent('HelpHandlerClick', 'Step:' + index,'HelpTitle:' + instructions[index].get('title'));
        }
        
        //Move the spotlight
        var instr = (index >= instructions.length || index < 0) ? null : instructions[index];

        //Close any existing tips
        if (this.tip) {
            this.tip.hide();
        }

        //Remove or move spotlight
        if (instr) {
            this.spot.show(instr.get('highlightEl'));
        } else {
            this.spot.hide();
        }

        // Show info popup alongside highlighted element
        if (instr) {
            var btnText = 'Next >>';
            if (index === (instructions.length - 1)) {
                btnText = 'Finish';
            }

            this.tip = Ext.create('Ext.tip.ToolTip', {
                target : instr.get('highlightEl'),
                anchor : instr.get('anchor'),
                closable : true,
                autoHide : false,
                title : instr.get('title'),
                html : instr.get('description'),
                buttons : [{
                    text : btnText,
                    scope : this,
                    index : index,
                    instructions : instructions,
                    handler : function(btn) {
                        this._showInstruction(btn.index + 1, btn.instructions);
                    }
                }],
                listeners : {
                    scope : this,
                    destroy : function() {
                        this.tip = null;
                    },
                    close : function() {
                        //If this is closed and not progressed by hitting next - abort the instruction set
                        this.spot.hide();
                    },
                    hide : function() {
                        //We don't want this popping up again
                        this.tip.destroy();
                    }
                }
            });
            this.tip.show();
        }
    },

    /**
     * Shows the series of instruction starting at instruction 0
     *
     * @param instructions portal.util.help.Instruction[] - the set of instructions to manage
     */
    showInstructions : function(instructions) {
        this.currentInstruction = 0;
        this._showInstruction(this.currentInstruction, instructions);
    }
});/**
 * A knownlayer downloader that creates an Ext.Window specialised into showing a
 * dialog for the user to download features from a WFS in a zip file.
 * This differ from WFSDownloader as it engage the use of downloader tracker which
 * forces the user enter a email address and allow the user to check back later with
 * the download progress
 */
Ext.define('portal.layer.downloader.wfs.KLWFSDownloader', {
    extend: 'portal.layer.downloader.Downloader',

    currentTooltip : null,
    featureCountUrl : null,
    enableFeatureCounts : false,

    /**
     * Adds the following config options
     * 
     * featureCountUrl : String - URL where feature counts will be looked up if proxy URL DNE. 
     * enableFeatureCounts : Boolean - Set to true to use feature counting in the popup.   
     */
    constructor : function(cfg) {
        this.featureCountUrl = cfg.featureCountUrl ? cfg.featureCountUrl : null;
        this.enableFeatureCounts = cfg.enableFeatureCounts ? true : false;
        this.callParent(arguments);
    },

    /**
     * An implementation of an abstract method, see parent method for details
     *
     * layer - portal.layer.Layer that owns resources
     * resources - an array of data sources that were used to render data
     * renderedFilterer - custom filter that was applied when rendering the specified data sources
     * currentFilterer - The value of the custom filter, this may differ from renderedFilterer if the
     *                   user has updated the form/map without causing a new render to occur
     * 
     */
    downloadData : function(layer, resources, renderedFilterer, currentFilterer) {
        var me = this;

        var currentlyVisibleBBox = null;

        currentlyVisibleBBox = currentFilterer.getSpatialParam();

        var downloadFilterer = Ext.create('portal.layer.filterer.Filterer', {});

        downloadFilterer.setSpatialParam(currentlyVisibleBBox, true);
        downloadFilterer.setParameters(renderedFilterer.getParameters(), true);

        var cswRecords = layer.get('cswRecords');
        //Create a popup showing our options
        Ext.create('Ext.Window', {
            title : 'Download Options',
            buttonAlign : 'right',
            modal : true,
            border : '1 1 0 1',
            width : 400,
            layout : {
                type : 'anchor'
                //align : 'stretch'
            },

            items : [{
                xtype : 'fieldset',
                anchor : '100%',
                layout : 'fit',
                padding : '10 10 0 10',
                border : false,
                items : [{
                    xtype : 'label',
                    style : 'font-size: 12px;',
                    itemId: 'klwfs-htmllabel',
                    html : this._parseNotifcationString(cswRecords)
                }]
            },{
                xtype : 'fieldset',
                anchor : '100%',
                layout : 'fit',
                border : false,
                items : [{
                    //Our radiogroup can see its item list vary according to the presence of bounding boxes
                    xtype : 'fieldcontainer',
                    defaultType: 'checkboxfield',

                    items : [{
                        boxLabel : 'Filter my download using the current visible map bounds.',
                        itemId: 'klwfs-checkbox',
                        checked : true,
                        listeners : {
                            change : Ext.bind(this._handleRadioChange, this, [currentlyVisibleBBox, resources, layer], true)
                        }

                    }]
                },{

                    xtype : 'fieldset',
                    anchor : '100%',
                    layout : 'fit',
                    border : false,
                    margin : '0 0 50 0',
                    items : [{
                        xtype           : 'textfield',
                        itemId          : 'downloadToken',
                        fieldLabel      : 'Email Address*',
                        emptyText       : 'Enter your email address',
                        name            : 'email',
                        selectOnFocus   : true,
                        allowBlank      : false,
                        blankText       : 'This field is required',
                        anchor          : '-50'
                    }]

                }]

            }],
            dockedItems: [{
                xtype : 'toolbar',
                dock: 'bottom',
                anchor : '100%',
                border : '0 1 1 1',
                margin : '-1 0 0 0',
                layout: {
                    type: 'hbox',
                    pack: 'end'
                },
                items : [{
                    xtype : 'button',
                    text: 'Check Status',
                    iconCls : 'info',
                    handler: function(btn) {
                        var popup = btn.up('window');
                        var sEmail = popup.down('#downloadToken').getValue();
                        if ( sEmail === '') {
                            Ext.MessageBox.alert('Unable to submit request...','Please enter a valid email address');
                            popup.down('#downloadToken').markInvalid();
                            return;
                        } else {
                           me._doCheckRequest(sEmail);
                        }
                    }

                },{
                    xtype : 'button',
                    text : 'Download',
                    iconCls : 'download',
                    handler : function(button) {
                        var popup = button.up('window');
                        var sEmail = popup.down('#downloadToken').getValue();
                        var outputFormat = "csv";
                        if ( sEmail === '' && sEmail.length < 4) {
                            Ext.MessageBox.alert('Unable to submit request...','Please enter a valid email address');
                            popup.down('#downloadToken').markInvalid();
                            return;
                        } else {
                            var bboxJson = '';
                            var popup = button.ownerCt.ownerCt;

                            var selected = popup.down('#klwfs-checkbox').getValue();
                            if (selected) {
                                me._doDownload(layer, downloadFilterer, resources, sEmail, outputFormat);
                            } else {
                                me._doDownload(layer, renderedFilterer, resources, sEmail, outputFormat);
                            }

                            //popup.close();
                        }
                    }//end of handler function

                }]
            }],
            listeners: {
                //After rendering - start the feature count loading
                afterrender: function(popup) {
                    if (me.enableFeatureCounts) {
                        var selected = popup.down('#klwfs-checkbox').getValue();
                        if (selected) {
                            me._updateFeatureCounts(layer, popup, resources, currentlyVisibleBBox);
                        } else {
                            me._updateFeatureCounts(layer, popup, resources, null);
                        }
                    }
                }
            }
        }).show();
    },

    _configureImageClickHandlers : function(c, eOpts, bbox) {
        var fireRender = function(bbox) {
            this.map.highlightBounds(bbox);
        };

        var fireScroll = function(bbox) {
            this.map.scrollToBounds(bbox);
        };

        c.getEl().on('click', Ext.bind(fireRender, this, [bbox], false), c);
        c.getEl().on('dblclick', Ext.bind(fireScroll, this, [bbox], false), c);
    },

    _handleRadioChange : function(radioGroup, newValue, oldValue, eOpts, currentBounds, resources, layer) {
        var popup = radioGroup.up('window');
        
        if (newValue) {
            this.map.scrollToBounds(currentBounds);
            this._updateFeatureCounts(layer, popup, resources, currentBounds);
        } else {
            this._updateFeatureCounts(layer, popup, resources, null);
        }
    },

    _doCheckRequest : function(email){

        var sUrl = '<iframe id="nav1" style="overflow:auto;width:100%;height:100%;" frameborder="0" src="';
        sUrl += 'checkGMLDownloadStatus.do?';
        sUrl += "email=";
        sUrl += email;
        sUrl += '"></iframe>';

        var winDwld = new Ext.Window({
            autoScroll  : true,
            border      : true,
            html        : sUrl,
            id          : 'dwldWindow',
            layout      : 'fit',
            maximizable : true,
            modal       : true,
            plain       : false,
            title       : 'Check Status: ',
            height      : 120,
            width       : 500
          });

        winDwld.on('show',function(){
            winDwld.center();
        });
        winDwld.show();

    },
    
    _updateFeatureCount : function(layer, url, typeName, el, bbox) {
        //Override feature count callback if the known layer specifies it
        var countUrl = this.featureCountUrl;
        if (layer.get('sourceType') === portal.layer.Layer.KNOWN_LAYER) {
            var override = layer.get('source').get('proxyCountUrl');
            if (!Ext.isEmpty(override)) {
                countUrl = override;
            }
        }
        
        //Load filter details into our feature count request
        var params = layer.get('filterer').getParameters();
        params.serviceUrl = url;
        params.typeName = typeName;
        params.bbox = bbox ? Ext.JSON.encode(bbox) : '';
        
        el.setHtml('<img src="portal-core/img/dotdotdot.gif" width="16" height="16">'); //our loading placeholder
        portal.util.Ajax.request({
            url: countUrl,
            params: params,
            timeout: 5 * 60 * 1000, //5 minutes  
            callback: function(success, data) {
                if (!success) {
                    el.setHtml('Error');
                } else {
                    el.setHtml(data);
                }
            }
        });
    },
    
    _updateFeatureCounts : function(layer, popup, resources, bbox) {
        var els = popup.down('#klwfs-htmllabel').getEl().query('.klwfs-featurecount', false);
        var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
        
        for (var i = 0; i < els.length; i++) {
            var url = wfsResources[i].get('url');
            var el = els[i];
            
            this._updateFeatureCount(layer, url, wfsResources[i].get('name'), el, bbox);
        }
    },

    _parseNotifcationString : function(cswRecords){

        var text = '<p>The portal will make a download request on your behalf and return the results as a zipped CSV file. ';
            text += 'To check the progress of your download and retrieve the file, click the "Check Status" button, using the email address that you used to start the download.</p>';
            text += '<p>We limit the results to 5000 features per access point. ';
            text += 'If you need more than 5000 features from any one data provider, you can download directly from the WFS service points below, or contact the data provider directly.</p>';
            text += "<p>Note: The links below are WFS service endpoints. Read <a href='http://docs.geoserver.org/latest/en/user/services/wfs/reference.html'>here</a> for more information </p>";




        text += '<div style="display:block;">';
        if (this.enableFeatureCounts) {
            text += '<div style="text-align:right;text-decoration:underline;">Total Features</div>';
        }

        for (var i = 0; i < cswRecords.length; i++) {
            var cswRecord = cswRecords[i];
            var resources = cswRecord.get('onlineResources');
            var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
            for (var j = 0; j < wfsResources.length; j++) {
                var wfsResource = wfsResources[j];
                text += '<div style="display:block; height:25px;">';
                text += '<div style="display:inline-block;width:78%;">'
                text += this._generateWFSGetCapabilititesUrl(wfsResource.get('url'), cswRecord.get('adminArea'), cswRecord.get('contactOrg'));
                text += '</div>'
                if (this.enableFeatureCounts) {
                    text += '<div class="klwfs-featurecount" style="display:inline-block; text-align:center;width:80px;"></div>';
                }
            }

            text += '</div>';
        }
        text += '</div>';

        return text;


    },

    /**
     * Handles a download the specified set of online resources and filterer
     *
     * filterer - a portal.layer.filterer.Filterer
     * resources - an array portal.csw.OnlineResource
     */
    _doDownload : function(layer, filterer, resources, sEmail, outputFormat) {
        var renderer = layer.get('renderer');


        var email = sEmail;
        var downloadUrl=layer.get('source').get('proxyDownloadUrl');
        var proxyUrl = renderer.getProxyUrl();

        if(downloadUrl && downloadUrl.length > 0){
            proxyUrl = downloadUrl;
        } else {
            proxyUrl = (proxyUrl && proxyUrl.length > 0) ? proxyUrl : 'getAllFeatures.do';
        }

        var prefixUrl = portal.util.URL.base + proxyUrl + "?";


        var sUrl = '<iframe id="nav1" style="overflow:auto;width:100%;height:100%;" frameborder="0" src="';

        sUrl += 'downloadGMLAsZip.do?';
        sUrl += "outputFormat=";
        sUrl += escape(outputFormat);
        sUrl += "&email=";
        sUrl += escape(email);


        //Iterate our WFS records and generate the array of PORTAL BACKEND requests that will be
        //used to proxy WFS requests. That array will be sent to a backend handler for making
        //multiple requests and zipping the responses into a single stream for the user to download.
        var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
        for (var i = 0; i < wfsResources.length; i++) {
        	
        	//VT: if there is a service provider filter we only want to download from the service provider specified
        	if(filterer.parameters.serviceFilter && filterer.parameters.serviceFilter.length > 0
        			&& wfsResources[i].get('url')!= filterer.parameters.serviceFilter[0]){
        		continue;
        	}
            //Create a copy of the last set of filter parameters
            var url = wfsResources[i].get('url');
            var typeName = wfsResources[i].get('name');
            var filterParameters = filterer.getParameters();

            filterParameters.serviceUrl = url;
            filterParameters.typeName = typeName;
            filterParameters.maxFeatures = 5000;
            filterParameters.outputFormat = outputFormat;
            
            portal.util.GoogleAnalytic.trackevent('KLWFSDownloader', 'Url:' + url,'parameters:' + Ext.encode(filterer.getParameters()));


            sUrl += '&serviceUrls=' + escape(Ext.urlEncode(filterParameters, prefixUrl));
        }

        sUrl += '"></iframe>';

        var winDwld = new Ext.Window({
            autoScroll  : true,
            border      : true,
            html        : sUrl,
            id          : 'dwldWindow',
            layout      : 'fit',
            maximizable : true,
            modal       : true,
            plain       : false,
            title       : 'Download confirmation: ',
            height      : 200,
            width       : 840
          });

        winDwld.on('show',function(){
            winDwld.center();
        });
        winDwld.show();


    },

    _generateWFSGetCapabilititesUrl : function(url, adminArea, contactOrg) {
        var provider = adminArea;
        if (adminArea == 'ACT') {
            provider = contactOrg;
        }
        var params = {
            "request" : "GetCapabilities",
            "version" : "1.1.0",
            "service" : "WFS"
        };
        url = Ext.urlAppend(url, Ext.Object.toQueryString(params));
        return '<a href="' + url +'">' + provider + '</a>';
    }

});
/**
 * A class for parsing a KML document into a list of GMarker and GOverlay primitives.
 */
Ext.define('portal.layer.renderer.wfs.KMLParser', {
    /**
     * portal.map.BaseMap - The map to generate primitives for
     */
    map : null,


    /**
     * Must the following config
     * {
     *  kml - string - a String of KML that will be parsed
     *  map - portal.map.BaseMap - The map to generate primitives for
     * }
     */
    constructor : function(config) {
        this.rootNode = portal.util.xml.SimpleDOM.parseStringToDOM(config.kml);
        this.map = config.map;
        this.callParent(arguments);
    },

    /**
     * Given features run through the KML parser, we can extract our gml ID from anything
     * running through the GENERIC PARSER workflow. Everything else gets lost
     */
    descriptionToGmlId : function(description) {
        var idPrefix = 'GENERIC_PARSER:';
        if (description.indexOf(idPrefix) === 0) {
            return description.substring(idPrefix.length);
        }

        return '';
    },

    //Given a series of space seperated CSV tuples, return a list of portal.map.Point
    generateCoordList : function(coordsAsString) {
        var coordinateList = coordsAsString.split(' ');
        var parsedCoordList = [];
        for (var i = 0; i < coordinateList.length; i++) {
            if (coordinateList[i].length === 0) {
                continue;
            }

            var coords = coordinateList[i].split(',');

            if (coords.length === 0) {
                continue;
            }

            parsedCoordList.push(Ext.create('portal.map.Point', {
                latitude : parseFloat(coords[1]),
                longitude : parseFloat(coords[0])
            }));
        }

        return parsedCoordList;
    },

    parseLineString : function(onlineResource, layer, name, description, lineStringNode) {
        var parsedCoordList = this.generateCoordList(portal.util.xml.SimpleDOM.getNodeTextContent(lineStringNode.getElementsByTagName("coordinates")[0]));
        if (parsedCoordList.length === 0) {
            return null;
        }

        var gmlId = this.descriptionToGmlId(description);

        return this.map.makePolyline(gmlId, undefined, onlineResource, layer, parsedCoordList, '#FF0000', 3, 1);
    },

    //Given a root placemark node attempt to parse it as a single point and return it
    //Returns a single portal.map.primitives.Polygon
    parsePolygon : function(onlineResource, layer, name, description, polygonNode) {
        var parsedCoordList = this.generateCoordList(portal.util.xml.SimpleDOM.getNodeTextContent(polygonNode.getElementsByTagName("coordinates")[0]));
        if (parsedCoordList.length === 0) {
            return null;
        }

        var gmlId = this.descriptionToGmlId(description);
        return this.map.makePolygon(gmlId, undefined, onlineResource, layer, parsedCoordList, undefined, undefined,0.7,undefined,0.6);
    },

    //Given a root placemark node attempt to parse it as a single point and return it
    //Returns a single portal.map.primitives.Marker
    parsePoint : function(onlineResource, layer, name, description, icon, pointNode) {
        var textCoordinates = portal.util.xml.SimpleDOM.getNodeTextContent(pointNode.getElementsByTagName("coordinates")[0]);
        var coordinates = textCoordinates.split(',');

        // We do not want placemarks without coordinates
        if (!coordinates || coordinates.length < 2) {
            return null;
        }

        var lon = coordinates[0];
        var lat = coordinates[1];
        var point = Ext.create('portal.map.Point', {latitude : parseFloat(lat), longitude : parseFloat(lon)});
        var gmlId = this.descriptionToGmlId(description);
        return this.map.makeMarker(gmlId, name,undefined, onlineResource, layer, point, icon);
    },

    makePrimitives : function(icon, onlineResource, layer) {
        var primitives = [];
        var placemarks = this.rootNode.getElementsByTagName("Placemark");

        for(i = 0; i < placemarks.length; i++) {
            var placemarkNode = placemarks[i];
            var mapItem = null;

            //Get the settings global to the placemark

            var name = portal.util.xml.SimpleDOM.getNodeTextContent(placemarkNode.getElementsByTagName("name")[0]);
            var description = portal.util.xml.SimpleDOM.getNodeTextContent(placemarkNode.getElementsByTagName("description")[0]);

            //Then extract the actual geometry for the placemark
            var polygonList = placemarkNode.getElementsByTagName("Polygon");
            var lineStringList = placemarkNode.getElementsByTagName("LineString");
            var pointList = placemarkNode.getElementsByTagName("Point");

            //Now parse the geometry
            //Parse any polygons
            for (var j = 0; j < polygonList.length; j++) {
                mapItem = this.parsePolygon(onlineResource, layer, name, description, polygonList[j]);
                if (mapItem === null) {
                    return;
                }

                primitives.push(mapItem);
            }

            //Parse any lineStrings
            for (var j = 0; j < lineStringList.length; j++) {
                mapItem = this.parseLineString(onlineResource, layer, name, description, lineStringList[j]);
                if (mapItem === null) {
                    return;
                }

                primitives.push(mapItem);
            }

            //Parse any points
            for (var j = 0; j < pointList.length; j++) {
                mapItem = this.parsePoint(onlineResource, layer, name, description, icon, pointList[j]);
                if (mapItem === null) {
                    return;
                }

                primitives.push(mapItem);
            }

        }


        return primitives;
    }
});


/**
 * An implementation of a portal.layer.Renderer for rendering generic Layers
 * that belong to a set of portal.csw.CSWRecord objects.
 */
Ext.define('portal.layer.renderer.csw.KMLRenderer', {
    extend: 'portal.layer.renderer.Renderer',

    vectorLayer : null,
    renderedId : null,

    constructor: function(config) {
       
        this.callParent(arguments);
    }, 

    /**   
     *
     * A function for rendering KML layers either from KML file input or URL.
     * 
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        
        //VT: I have taken a short path to render this layer on the map. 
        //If there are any actual demand to improve KML, 
        //create a KML Primitive and add makeKML in openlayersmap.js
        //this.PrimitiveManager.add(kml) and in primitiveManager handle if primitive type is kml.
        this.removeData();
        this.fireEvent('renderstarted', this, resources, filterer);
        this.renderStatus.initialiseResponses("KML Layer", 'Rendering...');
        this.renderedId=this.parentLayer.get('id');
        var me = this;
        var task = new Ext.util.DelayedTask(function() {
            me.vectorLayer = me.map.addKMLFromString(me.renderedId,me.parentLayer.get('name'), me.parentLayer.get('source').get('extensions'));    
            me.renderStatus.updateResponse("KML Layer", "Complete");   
            me.fireEvent('renderfinished', me);
        });

        task.delay(500);

    },


   

    /**
     * An abstract function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return null
    },

    /**
     * An abstract function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : function() {
        if(this.vectorLayer){
            this.map.removeKMLLayer(this.vectorLayer);
            this.vectorLayer = null;
        }
    },
    
    setVisibility : function(visible) {
        return this.vectorLayer.setVisibility(visible);
    },

    /**
     * No point aborting a bbox rendering
     */
    abortDisplay : Ext.emptyFn
});/**
 * KnownLayers are portal-defined grouping of CSWRecords. The records are grouped
 * 'manually' at the portal due to limitations with the way services can identify themselves.
 *
 * For example, there may be many WFSs with gsml:Borehole features but there is no way to
 * automatically identify them as 'Pressure DB' or 'National Virtual Core Library' feature
 * services. Instead we manually perform the grouping on the portal backend
 */
Ext.require('portal.csw.CSWRecordType');
Ext.define('portal.knownlayer.KnownLayer', {
    extend: 'Ext.data.Model',

    requires: ['portal.csw.CSWRecordType'],

    fields: [
        { name: 'id', type: 'string' }, //a unique ID of the known layer grouping
        { name: 'name', type: 'string'}, //A human readable name/title for this grouping
        { name: 'description', type: 'string' }, //A human readable description of this KnownLayer
        { name: 'group', type: 'string' }, //A term in which like KnownLayers can be grouped under
        { name: 'proxyUrl', type: 'string' }, //A URL of a backend controller method for fetching available data with a filter specific for this KnonwLayer
        { name: 'proxyGetFeatureInfoUrl', type: 'string' }, // A URL of a backend controller method for processing the WMS Get Feature Info response
        { name: 'proxyCountUrl', type: 'string' }, //A URL of a backend controller method for fetching the count of data available (eg for WFS a URL that will set featureType=hits)
        { name: 'proxyStyleUrl', type: 'string' }, // A URL of a backend controller method for fetching style
        { name: 'proxyDownloadUrl', type: 'string' }, // A URL of a backend controller method for download request
        { name: 'iconUrl', type: 'string' }, //A URL of an icon that will be used for rendering GMarkers associated with this layer
        { name: 'polygonColor', type: 'string' }, //Color of the polygon for csw rendering
        { name: 'iconAnchor', type: 'auto' }, //An object containing x,y for the pixel location of where the icon gets anchored to the map
        { name: 'iconSize', type: 'auto' }, //An object containing width,height for the pixel size of the icon
        { name: 'cswRecords', convert: portal.csw.CSWRecordType.convert}, //a set of portal.csw.CSWRecord objects that belong to this KnownLayer grouping
        { name: 'relatedRecords', convert: portal.csw.CSWRecordType.convert},// a set of portal.csw.CSWRecord objects that relate to this knownlayer
        { name: 'loading', type: 'boolean', defaultValue: false },//Whether this layer is currently loading data or not
        { name: 'layer', type: 'auto'}, // store the layer after it has been converted.        
        { name: 'active', type: 'boolean', defaultValue: false },//Whether this layer is current active on the map.
        { name: 'feature_count', type: 'string'}, //GetFeatureInfo feature_count attribute, 0 would be to default to whatever is set on the server.
        { name: 'order', type: 'string'},	// Order of the layers within a group
        { name: 'singleTile', type: 'boolean'},    // Whether the layer should be requested as a single image (ie not tiled)
        { name: 'nagiosFailingHosts', type: 'auto'},    // An array of host names that are failing according to a remote Nagios instance.
        { name: 'staticLegendUrl', type: 'string'}    // A URL to use to grab a canned legend graphic for the layer, optional.
    ],

    /**
     * Collates all portal.csw.OnlineResource objects owned by every CSWRecord instance in this known layer
     * and returns the set as an Array.
     */
    getAllOnlineResources : function() {
        var ors = [];
        for (var i = 0; i < this.data.cswRecords.length; i++) {
            ors = ors.concat(this.data.cswRecords[i].get('onlineResources'));
        }
        return ors;
    },

    /**
     * Given a keyword, search through every portal.csw.CSWRecord contained by this KnownLayer and return
     * an Array of portal.csw.CSWRecord objects that have the specified keyword
     * @param keyword String keyword or an Array of strings
     */
    getCSWRecordsByKeywords : function(keyword){
        //Filter our results
        var results = [];
        var cswRecords = this.get('cswRecords');
        for (var i = 0; i < cswRecords.length; i++){
            if (cswRecords[i].containsKeywords(keyword)) {
                results.push(cswRecords[i]);
            }
        }
        return results;
    },

    /**
     * Similar to getCSWRecordsByKeywords but instead sources portal.csw.CSWRecord objects
     * from the 'relatedRecords' property of this known layer
     * @param keyword String keyword or an Array of strings
     */
    getRelatedCSWRecordsByKeywords : function(keyword){
        //Filter our results
        var results = [];
        var cswRecords = this.get('relatedRecords');
        for (var i = 0; i < cswRecords.length; i++){
            if (cswRecords[i].containsKeywords(keyword)) {
                results.push(cswRecords[i]);
            }
        }
        return results;
    },

    containsCSWService : function() {
        var cswRecords = this.get('cswRecords');
        if (cswRecords.length == 1) {
            var onlineResources = cswRecords[0].get('onlineResources');
            if (onlineResources.length == 1) {
                return onlineResources[0].get('type') == portal.csw.OnlineResource.CSWService;
            }
        }

        return false;
    },
    
    /**
     * Returns true if this knownlayer has one or more hosts failing according to nagios
     */
    containsNagiosFailures : function() {
        return !Ext.isEmpty(this.get('nagiosFailingHosts'));
    }
});/**
 * A specialisation of portal.widgets.panel.BaseRecordPanel for rendering
 * records conforming to the portal.knownlayer.KnownLayer Model
 */
Ext.define('portal.widgets.panel.KnownLayerPanel', {
    extend : 'portal.widgets.panel.BaseRecordPanel',

    constructor : function(cfg) {
        this.callParent(arguments);
    },

  

    /**
     * Implements method - see parent class for details.
     */
    getTitleForRecord : function(record) {
        return record.get('name');
    },

    /**
     * Implements method - see parent class for details.
     */
    getCSWRecordsForRecord : function(record) {
        return record.get('cswRecords');
    },

    /**
     * Implements method - see parent class for details.
     */
    getOnlineResourcesForRecord : function(record) {
        var onlineResources = [];
        var cswRecords = record.get('cswRecords');

        for (var i = 0; i < cswRecords.length; i++) {
            onlineResources = onlineResources.concat(cswRecords[i].getAllChildOnlineResources());
        }

        return onlineResources;
    },

    /**
     * Implements method - see parent class for details.
     */
    getSpatialBoundsForRecord : function(record) {
        var bboxes = [];
        var cswRecords = record.data.cswRecords;

        for (var i = 0; i < cswRecords.length; i++) {
            if(cswRecords[i].get('noCache')==true){
                bboxes = bboxes.concat(this.getWholeGlobeBounds());
            }else{
                bboxes = bboxes.concat(cswRecords[i].get('geographicElements'));
            }
        }

        return bboxes;
    }
});/**
 * Class for transforming individual 'features' (WFS or otherwise) of a KnownLayer into
 * components representing GUI widgets for demonstrating ancillary information about
 * that 'feature'
 *
 * For example, the process of identifying the 'NVCL KnownLayer' and creating a 'Observations Download'
 * window is handled by this class (supported by underlying factories).
 *
 */
Ext.define('portal.layer.querier.wfs.KnownLayerParser', {
    extend: 'Ext.util.Observable',

    /**
     * Builds a new KnownLayerParser from a list of factories. Factories in factoryList will be tested before
     * the items in factoryNames
     *
     * {
     *  factoryNames : String[] - an array of class names which will be instantiated as portal.layer.querier.wfs.factories.BaseFactory objects
     *  factoryList : portal.layer.querier.wfs.factories.BaseFactory[] - an array of already instantiated factory objects
     * }
     */
    constructor : function(config) {
        //The following ordering is important as it dictates the order in which to try
        //factories for parsing a particular node
        this.factoryList = Ext.isArray(config.factoryList) ? config.factoryList : [];
        if (Ext.isArray(config.factoryNames)) {
            for (var i = 0; i < config.factoryNames.length; i++) {
                this.factoryList.push(Ext.create(config.factoryNames[i], config));
            }
        }
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Internal Method - returns a factory instance that can parse the specified feature/KnownLayer or null if it DNE
     */
    _getSupportingFactory : function(featureId, parentKnownLayer, parentOnlineResource, parentLayer) {
        if (!parentKnownLayer) {
            return null;
        }

        for (var i = 0; i < this.factoryList.length; i++) {
            if (this.factoryList[i].supportsKnownLayer(parentKnownLayer)) {
                return this.factoryList[i];
            }
        }

        return null;
    },

    /**
     * Iterates through all internal factories looking for factories that can generate GUI widgets
     * that can also represent the specified feature belonging to knownLayer.
     *
     * Returns a Boolean indicating whether there is a factory that can support this feature/knownLayer
     *
     *
     * @param featureId The unique ID of the feature (belonging to a known layer)
     * @param parentKnownLayer The KnownLayer that 'owns' featureId
     * @param parentOnlineResource The OnlineResource Object belonging to CSWRecord that sourced featureId
     * @param parentLayer The portal.layer.Layer representing the layer generated from parentKnownLayer
     */
    canParseKnownLayerFeature : function(featureId, parentKnownLayer, parentOnlineResource, parentLayer) {
        var supportingFactory = this._getSupportingFactory(featureId, parentKnownLayer, parentOnlineResource, parentLayer);
        return supportingFactory !== null && supportingFactory !== undefined;
    },

    /**
     * Iterates through all internal factories looking for factories that can generate GUI widgets
     * that can also represent the specified feature belonging to knownLayer.
     *
     * Returns a single GenericParser.BaseComponent object
     *
     * @param featureId The unique ID of the feature (belonging to a known layer)
     * @param parentKnownLayer The KnownLayer that 'owns' featureId
     * @param parentOnlineResource The OnlineResource Object belonging to CSWRecord that sourced featureId
     * @param parentLayer The portal.layer.Layer representing the layer generated from parentKnownLayer
     * @param rootCfg [Optional] An Object whose properties will be applied to the top level component parsed (a GenericParser.BaseComponent instance)
     */
    parseKnownLayerFeature : function(featureId, parentKnownLayer, parentOnlineResource, parentLayer) {
        var supportingFactory = this._getSupportingFactory(featureId, parentKnownLayer, parentOnlineResource, parentLayer);
        if (supportingFactory) {
            return supportingFactory.parseKnownLayerFeature(featureId, parentKnownLayer, parentOnlineResource, parentLayer);
        }

        return Ext.create('portal.layer.querier.BaseComponent', {});
    }
});/**
 * A Layer is what a portal.csw.CSWRecord or portal.knownlayer.KnownLayer becomes
 * when the user wishes to add it to the map.
 *
 * i.e. What a collection of service URL's becomes so that the GUI can render and
 * make the resulting data interactive
 */
Ext.define('portal.layer.Layer', {
    extend: 'Ext.data.Model',

    statics : {
        KNOWN_LAYER : 'KnownLayer', //A value for 'sourceType'
        CSW_RECORD : 'CSWRecord', //A value for 'sourceType'
        KML_RECORD : 'KMLRecord'
    },

    
    // TODO: Deprecate?
    visible : true,

    fields: [
        { name: 'id', type: 'string' }, //A unique ID of this layer - sourced from the original KnownLayer/CSWRecord
        { name: 'sourceType', type: 'string' }, //an 'enum' representing whether this Layer was constructed from a KnownLayer or CSWRecord
        { name: 'source', type: 'auto' }, //a reference to an instance of portal.knownlayer.KnownLayer or portal.csw.CSWRecord that was used to create this layer
        { name: 'name', type: 'string' }, //A human readable name/title of this layer
        { name: 'description', type: 'string' }, //A human readable description/abstract of this layer
        { name: 'renderer', type: 'auto' }, //A concrete implementation of a portal.layer.renderer.Renderer
        { name: 'filterer', type: 'auto' }, //A concrete implementation of a portal.layer.filterer.Filterer
        { name: 'downloader', type: 'auto' }, //A concrete implementation of a portal.layer.downloader.Downloader
        { name: 'querier', type: 'auto' }, //A concrete implementation of a portal.layer.querier.Querier
        { name: 'cswRecords', type: 'auto'}, //The source of all underlying data is an array of portal.csw.CSWRecord objects
        { name: 'loading', type: 'boolean', defaultValue: false },//Whether this layer is currently loading data or not
        { name: 'active', type: 'boolean', defaultValue: false },//Whether this layer is current active on the map.
        { name: 'visible', type: 'boolean', defaultValue: true }, // Whether this layer is visible
        { name: 'filterForm', type: 'auto'}, //The portal.layer.filterer.BaseFilterForm that houses the GUI for editing this layer's filterer
        { name: 'renderOnAdd', type: 'boolean', defaultValue: false }, //If true then this layer should be rendered the moment it is added as a layer. Currently used by KML
        { name: 'deserialized', type: 'boolean', defaultValue: false }, //If true then this layer has been deserialized from a permanent link
        { name: 'singleTile', type: 'boolean', defaultValue: false},    // Whether the layer should be requested as a single image (ie not tiled)
        { name: 'staticLegendUrl', type: 'string'} // A URL to use to grab a canned legend graphic for the layer (optional).
    ],

    /**
     * Utility function for concatenating all online resources stored in all
     * CSWRecords and returning the result as an Array. Online resources
     * that are sourced from a known layer with defined nagiosFailingHosts
     * that are "known failing" will be omitted. To force the inclusion of
     * all online resources set includeFailingHosts to true.
     *
     * returns an Array of portal.csw.OnlineResource objects
     */
    getAllOnlineResources : function(includeFailingHosts) {
        
        var failingHosts = null;
        if (!includeFailingHosts) {
            if (this.get('sourceType') === portal.layer.Layer.KNOWN_LAYER) {
                failingHosts = this.get('source').get('nagiosFailingHosts');
            }
        }
        
        var resources = [];
        var cswRecords = this.get('cswRecords');
        for (var i = 0; i < cswRecords.length; i++) {
            if (includeFailingHosts || Ext.isEmpty(failingHosts)) {
                resources = resources.concat(cswRecords[i].get('onlineResources'));
            } else {
                Ext.each(cswRecords[i].get('onlineResources'), function(or) {
                    var isFailing = false;
                    Ext.each(failingHosts, function(host) {
                        if (or.get('url').indexOf(host) >= 0) {
                            isFailing = true;
                            return false;
                        }
                    });
                    
                    if (!isFailing) {
                        resources.push(or);
                    }
                });
            }
            
        }
        return resources;
    },
    
    setLayerVisibility : function(visibility){
        this.get('renderer').setVisibility(visibility);
        // TODO: Deprecate?
        this.visible=visibility;
        this.set('visible',visibility);
    },                

    onRenderStarted : function(renderer, onlineResources, filterer) {
        this.set('loading', true);
        this.set('active', true);
        this.get('source').set('loading', true);
        this.get('source').set('active', true);
    },

    /** Called when this layer is completely rendered.
     * Each renderer is responsible for firing the renderfinished
     * event when all of its resources have been rendered to the map.
     * 
     * @param renderer the layer renderer that just fired the renderfinished event
     */
    onRenderFinished : function(renderer) {
        //this.set('loading', false);
        this.get('source').set('loading', false);
        this.set('loading', false);

        var map = renderer.map;
        var layerStore = map.layerStore;

        var l = 0;
        var zIndex = 0;
        for (var i = layerStore.data.items.length-1; i >= 0; --i) {
            var onlineResourcesForLayer = [];
            var cswRecords = layerStore.data.items[i].data.cswRecords;
            for (var j = 0; j < cswRecords.length; j++) {
			    if (cswRecords[j].data.onlineResources)
                    onlineResourcesForLayer = onlineResourcesForLayer.concat(cswRecords[j].data.onlineResources);
            }

            var layerNameArray = [];
            for (var j = 0; j < onlineResourcesForLayer.length; j++) {
                var layerName = onlineResourcesForLayer[j].data.name;
                var mapLayers = map.map.getLayersByName(layerName);

                if (layerNameArray.indexOf(layerName) < 0)
                {
                    layerNameArray.push(layerName);
                    if (mapLayers && mapLayers.length > 0) {
                        for (var k = 0; k < mapLayers.length; k++) {
                        // construct a useable z-index for the layer on the map
                        var zIndex = zIndex + 1;
                        map.map.setLayerZIndex(mapLayers[k], zIndex);
                        }
                    }
                }
            }
            l = l + 100;
        }

        // float the vector root containers to the top of the map so that they can be clicked on
        for (var i = 0; i < map.map.layers.length; i++) {
            var layer = map.map.layers[i];
            if (layer.id.indexOf('OpenLayers_Layer_Vector_RootContainer') != -1) {
                map.map.setLayerZIndex(layer, 20000 + i);
            }
        }
    },


    /**
     * Whenever our filter changes, update the rendered page
     */
    onFilterChanged : function(filterer, keys) {
        this.reRenderLayerDisplay(filterer, keys);
    },
    
    reRenderLayerDisplay : function(filterer, keys) {
        var renderer = this.get('renderer');      
        this.removeDataFromMap();                  
        renderer.displayData(this.getAllOnlineResources(), this.get('filterer'), Ext.emptyFn);
    },
    
   removeDataFromMap:function(){
       var renderer = this.get('renderer');       
       renderer.removeData();
       renderer.map.closeInfoWindow(this.get('id')); 
       this.get('source').set('active', false);
   },

    getCSWRecordsByKeywords : function(keyword){
        //Filter our results
        var results = [];
        var cswRecords=this.get('cswRecords');
        for(var i=0; i < cswRecords.length;i++){
            if(cswRecords[i].containsKeywords(keyword)){
                results.push(cswRecords[i]);
            }
        }
        return results;
    },
    
    getCSWRecordsByResourceURL : function(resourceURL){
        //Filter our results
        var results = [];
        var cswRecords=this.get('cswRecords');
        for(var i=0; i < cswRecords.length;i++){
            if(cswRecords[i].containsOnlineResourceUrl(resourceURL)){
                results.push(cswRecords[i]);
            }            
        }
        return results;
    },

    containsCSWService : function() {
        // If the layer is a known layer, then ask the KnownLayer
        // object if it contains a CSW service endpoint:
        if (this.get('sourceType') == portal.layer.Layer.KNOWN_LAYER) {
            return this.get('source').containsCSWService();
        }

        return false;
    }
});



/**
 * Factory class for creating instances of portal.layer.Layer.
 *
 * Instances are designed to be constructed from portal.cswCSWRecord
 * and portal.knownlayer.KnownLayer objects
 */
Ext.define('portal.layer.LayerFactory', {

    map : null,
    formFactory : null, //an implementation of portal.layer.filterer.FormFactory
    downloaderFactory : null, //an implementation of portal.layer.downloader.DownloaderFactory
    querierFactory : null, //an implementation of portal.layer.querier.QuerierFactory
    rendererFactory : null, //an implementation of portal.layer.renderer.RendererFactory

    /**
     * Creates a new instance of this factory.
     *
     * @param cfg an object in the form
     * {
     *  map : an instance of portal.util.gmap.GMapWrapper
     *  formFactory : an implementation of portal.layer.filterer.FormFactory
     *  downloaderFactory : an implementation of portal.layer.downloader.DownloaderFactory
     *  querierFactory : an implementation of portal.layer.querier.QuerierFactory
     *  rendererFactory : an implementation of portal.layer.renderer.RendererFactory
     * }
     */
    constructor : function(cfg) {
        this.map = cfg.map;
        this.formFactory = cfg.formFactory;
        this.downloaderFactory = cfg.downloaderFactory;
        this.querierFactory = cfg.querierFactory;
        this.rendererFactory = cfg.rendererFactory;

        this.callParent(arguments);
    },

    /**
     * Utility function for generating a new Layer from a set of values. Returns a new instance of portal.layer.Layer
     * @param id String based ID for this instance
     * @param source An instance of portal.csw.CSWRecord or portal.knownlayer.KnownLayer that is generating this layer
     * @param name A human readable name/title of this layer
     * @param description  A human readable description/abstract of this layer
     * @param renderer A concrete implementation of a portal.layer.renderer.Renderer
     * @param filterer A concrete implementation of a portal.layer.filterer.Filterer
     * @param downloader A concrete implementation of a portal.layer.downloader.Downloader
     * @param querier A concrete implementation of a portal.layer.querier.Querier
     * @param cswRecords A single instance or Array of portal.csw.CSWRecord
     */
    generateLayer : function(id, source, name, description, renderer, filterer, downloader, querier, cswRecords) {
        //Generate appropriate sourceType string
        var sourceType = null;
        if (source instanceof portal.knownlayer.KnownLayer) {
            sourceType = portal.layer.Layer.KNOWN_LAYER;
        } else {
            sourceType = portal.layer.Layer.CSW_RECORD;
        }

        //If we have a singleton, turn it into an array
        if (!Ext.isArray(cswRecords)) {
            cswRecords = [cswRecords];
        }

        //Create our instance
        var newLayer = Ext.create('portal.layer.Layer', {
            id : id,
            sourceType : sourceType,
            source : source,
            name : name,
            description : description,
            renderer : renderer,
            filterer : filterer,
            downloader : downloader,
            querier : querier,
            cswRecords : cswRecords,
            loading : false
        });

        //Wire up references to our layer
        renderer.parentLayer = newLayer;

        //Wire up our events so that the layer is listening for changes in its components
        renderer.on('renderstarted', Ext.bind(newLayer.onRenderStarted, newLayer));
        renderer.on('renderfinished', Ext.bind(newLayer.onRenderFinished, newLayer));
        renderer.on('visibilitychanged', Ext.bind(newLayer.onVisibilityChanged, newLayer));
        filterer.on('change', Ext.bind(newLayer.onFilterChanged, newLayer));

        //Create our filter form
        var formFactoryResponse = this.formFactory.getFilterForm(newLayer);
        if (formFactoryResponse) {
            newLayer.set('filterForm', formFactoryResponse.form);
        }
        //VT: Since the new rowExpander design, We do not renderOnAdd for non filtering support layer.
        //newLayer.set('renderOnAdd', !formFactoryResponse.supportsFiltering);
        newLayer.set('renderOnAdd', false);
        
        return newLayer;
    },

    /**
     * Generates a new instance of portal.layer.Layer from an existing KnownLayer object. Appropriate
     * renderers, queriers etc will be generated according to knownLayer's contents
     *
     * @param knownLayer an instance of portal.knownlayer.KnownLayer
     */
    generateLayerFromKnownLayer : function(knownLayer) {
        var id = knownLayer.get('id');
        var source = knownLayer;
        var description = knownLayer.get('description');
        var name = knownLayer.get('name');
        var cswRecords = knownLayer.get('cswRecords');

        //Create our objects for interacting with this layer
        var renderer = this.rendererFactory.buildFromKnownLayer(knownLayer);
        var querier = this.querierFactory.buildFromKnownLayer(knownLayer);
        var filterer = Ext.create('portal.layer.filterer.Filterer', {});
        var downloader = this.downloaderFactory.buildFromKnownLayer(knownLayer);

        return this.generateLayer(id, source, name, description, renderer, filterer, downloader, querier, cswRecords);
    },

    /**
     * Generates a new instance of portal.layer.Layer from an existing CSWRecord object. Appropriate
     * renderers, queriers etc will be generated according to CSWRecord's contents
     *
     * @param cswRecord an instance of portal.csw.CSWRecord
     */
    generateLayerFromCSWRecord : function(cswRecord) {
        
        //VT: a extension of CSWRecord to handle KML
        if(cswRecord.get('resourceProvider')=='kml'){
            return this.generateLayerFromKMLSource(cswRecord);
        }
        
        var id = cswRecord.get('id');
        var source = cswRecord;
        var description = cswRecord.get('description');
        var name = cswRecord.get('name');
        var cswRecords = cswRecord;

        //Create our objects for interacting with this layer
        var renderer = this.rendererFactory.buildFromCswRecord(cswRecord);
        var querier = this.querierFactory.buildFromCswRecord(cswRecord);
        var filterer = Ext.create('portal.layer.filterer.Filterer', {});
        var downloader = this.downloaderFactory.buildFromCswRecord(cswRecord);

        return this.generateLayer(id, source, name, description, renderer, filterer, downloader, querier, cswRecords);
    },
    
    generateLayerFromKMLSource : function(cswRecord){
        var id = cswRecord.get('id');
        var source = cswRecord;
        var description = 'A layer generated from a EPSG:4326 KML file';
        var name = cswRecord.get('name');
        var cswRecords = cswRecord;
        if (!Ext.isArray(cswRecords)) {
            cswRecords = [cswRecords];
        }
        var filterer = Ext.create('portal.layer.filterer.Filterer', {})
        
        var renderer = this.rendererFactory.buildFromKMLRecord(cswRecord);
        
        
        var querier = Ext.create('portal.layer.querier.csw.CSWQuerier', {
            map : this.map
            });
        
        //Create our instance
        var newLayer = Ext.create('portal.layer.Layer', {
            id : id,
            sourceType : portal.layer.Layer.KML_RECORD,
            source : source,
            name : name,
            description : description,
            renderer : renderer,
            filterer : filterer, 
            querier : querier,
            cswRecords : cswRecords,
            loading : false
        });

        //Wire up references to our layer
        renderer.parentLayer = newLayer;

        //Wire up our events so that the layer is listening for changes in its components
        renderer.on('renderstarted', Ext.bind(newLayer.onRenderStarted, newLayer));
        renderer.on('renderfinished', Ext.bind(newLayer.onRenderFinished, newLayer));
        renderer.on('visibilitychanged', Ext.bind(newLayer.onVisibilityChanged, newLayer));
        filterer.on('change', Ext.bind(newLayer.onFilterChanged, newLayer));

        //Create our filter form
        var formFactoryResponse = this.formFactory.getFilterForm(newLayer);
        newLayer.set('filterForm', formFactoryResponse.form);
        newLayer.set('renderOnAdd', true);

        return newLayer;

    }
});/**
 * A specialised Ext.grid.Panel instance for
 * displaying a store of portal.layer.Layer objects.
 * VT:Mark for deletion
 */
Ext.define('portal.widgets.panel.LayerPanel', {
    extend : 'Ext.grid.Panel',
    alias: 'widget.layerpanel',

    map : null, //instance of portal.util.gmap.GMapWrapper
    allowDebugWindow : false, //whether this panel will show the debug window if clicked by the user

    constructor : function(cfg) {
        var me = this;

        this.map = cfg.map;
        this.allowDebugWindow = cfg.allowDebugWindow ? true : false;
      

        this.removeAction = new Ext.Action({
            text : 'Remove Layer',
            iconCls : 'remove',
            handler : Ext.bind(function(cmp) {
                var sm = this.getSelectionModel();
                var selectedRecords = sm.getSelection();
                if (selectedRecords && selectedRecords.length > 0) {
                    var store = this.getStore();
                    store.remove(selectedRecords);
                    this.fireEvent('removelayerrequest', this, selectedRecords[0]);//only support single selection
                }
            }, this)
        });
        
        
        this.downloadLayerAction = new Ext.Action({
            text : 'Download Layer',
            iconCls : 'download',
            handler : Ext.bind(this._downloadClickHandler,this)
        });
        

        Ext.apply(cfg, {
            columns : [{
                //legend column
                xtype : 'clickcolumn',
                dataIndex : 'renderer',
                renderer : this._legendIconRenderer,
                width : 32,
                listeners : {
                    columnclick : Ext.bind(this._legendClickHandler, this)
                }
            },{
                //Loading icon column
                xtype : 'clickcolumn',
                dataIndex : 'loading',
                renderer : this._loadingRenderer,
                hasTip : true,
                tipRenderer : Ext.bind(this._loadingTipRenderer, this),
                width: 32,
                listeners : {
                    columnclick : Ext.bind(this._serviceInformationClickHandler, this)
                }
            },{
                //Layer name column
                xtype : 'clickcolumn',
                text : 'Layer Name',
                dataIndex : 'name',
                flex : 1,
                listeners : {
                    columnclick : Ext.bind(this._nameClickHandler, this)
                }
            },{
                //Visibility column
                xtype : 'renderablecheckcolumn',
                text : 'Visible',
                dataIndex : 'renderer',
                getCustomValueBool : function(header, renderer, layer) {
                    return renderer.getVisible();
                },
                setCustomValueBool : function(header, renderer, checked, layer) {
                    //update our bbox silently before updating visibility
                    if (checked) {
                        var filterer = layer.get('filterer');
                        filterer.setSpatialParam(me.map.getVisibleMapBounds(), true);
                    }

                    return renderer.setVisible(checked);
                },
                width : 40
            },{
                //Download column
                xtype : 'clickcolumn',
                dataIndex : 'renderer',
                width : 32,
                renderer : this._downloadRenderer,
                listeners : {                                                            
                    columnclick : function( column, record, recordIndex, cellIndex, e){
                        var menu = Ext.create('Ext.menu.Menu', {
                            items: [
                                    me.removeAction,
                                    me.downloadLayerAction
                                    ]                
                        });
                        menu.showAt(e.getXY());
                    }
                }
            }],
            plugins: [{
                ptype: 'rowexpander',
                rowBodyTpl : [
                    '<p>{description}</p><br>'
                ]
            },{
                ptype: 'celltips'
            },{
                ptype : 'rowcontextmenu',
                contextMenu : Ext.create('Ext.menu.Menu', {
                    items: [
                            this.removeAction,
                            this.downloadLayerAction
                            ]                
                })
            }],

            viewConfig:{
                plugins:[{
                    ptype: 'gridviewdragdrop',
                    dragText: 'Drag and drop to re-order'
                }],
                listeners: {
                    beforedrop: function(node, data, overModel, dropPosition,  dropFunction,  eOpts ){
                        if(data.records[0].data.renderer instanceof portal.layer.renderer.wms.LayerRenderer){
                            return true;
                        }else{
                            alert('Only wms layers can be reordered');
                            return false;
                        }
                    }
                }
            }
           
        });

        this.callParent(arguments);
    },

    /**
     * Renderer for the legend icon column
     */
    _legendIconRenderer : function(value, metaData, record, row, col, store, gridView) {
        if (!value) {
            return '';
        }

        var legend = value.getLegend();
        if (!legend) {
            return '';
        }

        return legend.getLegendIconHtml(record.getAllOnlineResources(), record.data.filterer);
    },

    /**
     * Renderer for the loading column
     */
    _loadingRenderer : function(value, metaData, record, row, col, store, gridView) {
        if (value) {
            return Ext.DomHelper.markup({
                tag : 'img',
                width : 16,
                height : 16,
                src: 'portal-core/img/loading.gif'
            });
        } else {
            return Ext.DomHelper.markup({
                tag : 'img',
                width : 16,
                height : 16,
                src: 'portal-core/img/notloading.gif'
            });
        }
    },

    /**
     * Renderer for download column
     */
    _downloadRenderer : function(value, metaData, record, row, col, store, gridView) {
      
            return Ext.DomHelper.markup({
                tag : 'a',
                href : 'javascript: void(0)',
                children : [{
                    tag : 'img',
                    width : 16,
                    height : 16,
                    src: 'portal-core/img/setting.png'
                }]
            });
      
    },

    /**
     * A renderer for generating the contents of the tooltip that shows when the
     * layer is loading
     */
    _loadingTipRenderer : function(value, layer, column, tip) {
        var renderer = layer.get('renderer');
        var update = function(renderStatus, keys) {
            tip.update(renderStatus.renderHtml());
        };

        //Update our tooltip as the underlying status changes
        renderer.renderStatus.on('change', update, this);
        tip.on('hide', function() {
            renderer.renderStatus.un('change', update); //ensure we remove the handler when the tip closes
        });

        return renderer.renderStatus.renderHtml();
    },

    /**
     * Raised whenever the name column is clicked
     */
    _nameClickHandler : function(column, layer, rowIndex, colIndex) {
        if (!this.allowDebugWindow) {
            return;
        }

        //Show off the rendering debug information
        var debugData = layer.get('renderer').getRendererDebuggerData();
        if (!debugData) {
            return;
        }

        //Raised whenever the underlying debug information changes
        //designed to update an already showing window
        var onChange = function(debugData, keys, eOpts) {
            eOpts.win.body.update(debugData.renderHtml());
            eOpts.win.doLayout();
        };

        //if the window gets closed - stop listening for change events
        var onClose = function(debugWin, eOpts) {
            debugData.un('change', onChange, this);
        };

        //Simplistic window
        var debugWin = Ext.create('Ext.window.Window', {
            title : 'Renderer Debug Information',
            autoScroll : true,
            width : 500,
            height : 300,
            modal : true,
            html : debugData.renderHtml()
        });

        debugData.on('change', onChange, this, {win : debugWin});
        debugWin.on('beforeclose', onClose, this);

        debugWin.show();
    },

    /**
     * Raised whenever the download column is clicked
     */
    _downloadClickHandler : function() {
        var layer = this.getSelectionModel().getSelection()[0];
        var downloader = layer.get('downloader');
        var renderer = layer.get('renderer');
        if (downloader && renderer.getHasData()) {
            //We need a copy of the current filter object (in case the user
            //has filled out filter options but NOT hit apply filter) and
            //the original filter objects
            var renderedFilterer = layer.get('filterer').clone();
            var currentFilterer = Ext.create('portal.layer.filterer.Filterer', {});
            var currentFilterForm = layer.get('filterForm');

            currentFilterer.setSpatialParam(this.map.getVisibleMapBounds(), true);
            currentFilterForm.writeToFilterer(currentFilterer);

            //Finally pass off the download handling to the appropriate downloader (if it exists)
            var onlineResources = layer.getAllOnlineResources();
            downloader.downloadData(layer, onlineResources, renderedFilterer, currentFilterer);

        }
    },

    /**
     * Show a popup containing info about the services that 'power' this layer
     */
    _serviceInformationClickHandler : function(column, record, rowIndex, colIndex) {
        var cswRecords = record.get('cswRecords');
        if (!cswRecords || cswRecords.length === 0) {
            return;
        }
        var popup = Ext.create('portal.widgets.window.CSWRecordDescriptionWindow', {
            cswRecords : cswRecords,
            parentRecord : record
        });
        popup.show();
    },

    /**
     * Raised whenever the legend column is clicked
     */
    _legendClickHandler : function(column, layer, rowIndex, colIndex) {
        //The callback takes our generated component and displays it in a popup window
        // this will be resized dynamically as legend content is added
        var legendCallback = function(legend, resources, filterer, success, form, layer){
            if (success && form) {
                var win = Ext.create('Ext.window.Window', {
                    title       : 'Legend: '+ layer.get('name'),
                    layout      : 'fit',
                    modal : true,
                    items: form
                });
                return win.show();
            }
        };

        var onlineResources = layer.getAllOnlineResources();
        var filterer = layer.get('filterer');
        var renderer = layer.get('renderer');
        var legend = renderer.getLegend(onlineResources, filterer);

        //VT: this style is just for the legend therefore no filter is required.
        var styleUrl = layer.get('renderer').parentLayer.get('source').get('proxyStyleUrl');

        Ext.Ajax.request({
            url: styleUrl,
            timeout : 180000,
            scope : this,
            success:function(response,opts){
                legend.getLegendComponent(onlineResources, filterer,response.responseText, true, Ext.bind(legendCallback, this, [layer], true));
            },
            failure: function(response, opts) {
                legend.getLegendComponent(onlineResources, filterer,"", true, Ext.bind(legendCallback, this, [layer], true));
            }
        });


    }
});
/**
 * An implementation of a portal.layer.Renderer for rendering WMS Layers
 * that belong to a set of portal.csw.CSWRecord objects.
 */
Ext.define('portal.layer.renderer.wms.LayerRenderer', {
    extend: 'portal.layer.renderer.Renderer',

    constructor: function(config) {
        this.legend = Ext.create('portal.layer.legend.wms.WMSLegend', {
            iconUrl : config.iconCfg ? config.iconCfg.url : 'portal-core/img/key.png',
            tryGetCapabilitiesFirst : config.tryGetCapabilitiesFirst
        });
        this.callParent(arguments);
    },

    /**
     * A function for displaying layered data from a variety of data sources. This function will
     * raise the renderstarted and renderfinished events as appropriate. The effect of multiple calls
     * to this function (ie calling displayData again before renderfinished is raised) is undefined.
     *
     * This function will re-render itself entirely and thus may call removeData() during the normal
     * operation of this function
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success) callback
     *
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : function(resources, filterer, callback) {
        this.removeData();
        var wmsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WMS);

        var urls = [];
        for (var i = 0; i < wmsResources.length; i++) {
            urls.push(wmsResources[i].get('url'));
        }
        this.renderStatus.initialiseResponses(urls, 'Loading...');
        
        var primitives = [];
        for (var i = 0; i < wmsResources.length; i++) {
            var wmsUrl = wmsResources[i].get('url');
            var wmsLayer = wmsResources[i].get('name');
            var wmsOpacity = filterer.getParameter('opacity');
            
            var filterParams = (Ext.Object.toQueryString(filterer.getMercatorCompatibleParameters()));
            var proxyUrl = this.parentLayer.get('source').get('proxyStyleUrl');

            if(proxyUrl){
                var styleurl =  proxyUrl = Ext.urlAppend(proxyUrl,filterParams);
                Ext.Ajax.request({
                    url: Ext.urlAppend(styleurl),
                    timeout : 180000,
                    scope : this,
                    success: Ext.bind(this._getRenderLayer,this,[wmsResources[i], wmsUrl, wmsLayer, wmsOpacity, filterer],true),
                    failure: function(response, opts) {                    
                        this.fireEvent('renderfinished', this);
                        console.log('server-side failure with status code ' + response.status);
                    }
                });
            } else {
                this._getRenderLayer(null,null,wmsResources[i], wmsUrl, wmsLayer, wmsOpacity, filterer);
            }

        }


        this.hasData = true;

    },
    
    _getRenderLayer : function(response,opts,wmsResource, wmsUrl, wmsLayer, wmsOpacity,filterer){
    	
    	if(wmsOpacity === undefined){
             wmsOpacity = filterer.parameters.opacity;
        }
        var sld_body = "";
        if (response !== null) {
            var sld_body = response.responseText;
            this.sld_body = sld_body;
            if(sld_body.indexOf("<?xml version=")!=0){
                sld_body = null;
                this.sld_body = sld_body;
            }
        }
    
        var layer=this.map.makeWms(undefined, undefined, wmsResource, this.parentLayer, wmsUrl, wmsLayer, wmsOpacity,sld_body)

        layer.getWmsLayer().events.register("loadstart",this,function(){
            var listOfStatus=this.renderStatus.getParameters();
            for(key in listOfStatus){
                if(this._getDomain(key)==this._getDomain(layer.getWmsUrl())){
                    this.renderStatus.updateResponse(key, "Loading WMS");
                    this.fireEvent('renderstarted', this, wmsResource, filterer);
                    break
                }
            }

        });

        //VT: Handle the after wms load clean up event.
        layer.getWmsLayer().events.register("loadend",this,function(evt){
            this.fireEvent('renderfinished', this);
            var listOfStatus=this.renderStatus.getParameters();
            this._updateStatusforWMS(layer.getWmsUrl(),"WMS Loaded");                        
        });
        
        var primitives = [];
        primitives.push(layer);
        this.primitiveManager.addPrimitives(primitives);
        
    },
    
    _updateStatusforWMS : function(updateKey,newValue){
        for(key in this.renderStatus.getParameters()){
            if(this._getDomain(key)==this._getDomain(updateKey)){
                this.renderStatus.updateResponse(key, newValue);
                break
            }
        }
    },
    
    _getDomain : function(data) {
        return portal.util.URL.extractHostNSubDir(data,1);
      },


    /**
     * A function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : function(resources, filterer) {
        return this.legend;
    },

    /**
     * A function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : function() {
        this.primitiveManager.clearPrimitives();
    },

    /**
     * You can't abort a WMS layer from rendering as it does so via img tags
     */
    abortDisplay : Ext.emptyFn
});/**
 * An Ext.data.Store specialisation for storing
 * portal.layer.Layer objects.
 */
Ext.define('portal.layer.LayerStore', {
    extend: 'Ext.data.Store',
    
    addingLayer: false, // Set to true when adding a layer
    
    /**
     * Creates an empty store - no configuration options
     */
    constructor : function(config) {        
        this.callParent([{
            model : 'portal.layer.Layer',
            data : []
        }]);
    }
});
/**
 * An abstract class for providing the ability
 * for specific portal.layer.Renderer instances
 * to present to the user a Legend that describes
 * what it is that they are rendering on the map.
 *
 */
Ext.define('portal.layer.legend.Legend', {
    extend: 'Ext.util.Observable',
    requires : ['portal.util.UnimplementedFunction'],

    constructor: function(config){

        // Copy configured listeners into *this* object so that the base class's
        // constructor will add them.
        this.listeners = config.listeners;

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * An abstract function for generating a component representing
     * the legend that can be displayed to the user
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.legend.Legend this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success, portal.layer.legend.BaseComponent legendGui) callback
     *
     * returns - void
     *
     * resources - an array of data sources that were used to render data
     * filterer - custom filter that was applied when rendering the specified data sources
     * callback - Will be called when the legend creation process is completed and passed an instance of this Legend and the parameters used to call this function
     * staticLegendUrl - a url to a canned legend image
     */
    getLegendComponent : portal.util.UnimplementedFunction,

    /**
     * An abstract function for generating a html snippet for some form of icon
     * that will be used to represent an overview of this legend.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - string
     *
     * resources - an array of data sources that were used to render data
     * filterer - custom filter that was applied when rendering the specified data sources
     */
    getLegendIconHtml : portal.util.UnimplementedFunction

});/**
 * MapStateSerializer
 *
 * A 'class' for taking in various elements of the map state and serializing
 * them into a simple string that can be stored. Deserialization is also supported
 *
 * Currently custom map layers are unsupported.
 */
Ext.define('portal.util.permalink.MapStateSerializer', {

    /**
     * An object with the schema {
     *      center : {
     *          lng : Number
     *          lat : Number
     *      },
     *      zoom : Number
     * }
     */
    mapState : null,

    /**
     * An array of objects which have one of the following schemas
     *
     *  {
     *      source : String //Always set to 'KnownLayer'
     *      id : String,
     *      filter : Object,
     *      visible : Boolean
     *  }
     *
     *  {
     *      source : String //Always set to 'CSWRecord'
     *      filter : Object,
     *      visible : Boolean,
     *      onlineResources : [{
     *          url                 : String
     *          onlineResourceType  : String
     *          name                : String
     *          description         : String
     *      }]
     *  }
     */
    serializedLayers : null,

    /**
     * Instance of portal.util.permalink.serializers.BaseSerializer that
     * will be used for SERIALIZATION.
     *
     * Deserialization will still occur from baseSerializers list
     */
    serializer : null,

    /**
     * An object containing portal.util.permalink.serializers.BaseSerializer keyed
     * by their reported versions
     */
    baseSerializers : null,

    constructor : function() {
        this.mapState = {};
        this.serializedLayers = [];

        //Build our list of serializers
        this.baseSerializers = {};
        var tmpSerializer = Ext.create('portal.util.permalink.serializers.SerializerV0');
        this.baseSerializers[tmpSerializer.getVersion()] = tmpSerializer;
        tmpSerializer = Ext.create('portal.util.permalink.serializers.SerializerV1');
        this.baseSerializers[tmpSerializer.getVersion()] = tmpSerializer;
        tmpSerializer = Ext.create('portal.util.permalink.serializers.SerializerV2');
        this.baseSerializers[tmpSerializer.getVersion()] = tmpSerializer;
        tmpSerializer = Ext.create('portal.util.permalink.serializers.SerializerV3');
        this.baseSerializers[tmpSerializer.getVersion()] = tmpSerializer;
        tmpSerializer = Ext.create('portal.util.permalink.serializers.SerializerV4');
        this.baseSerializers[tmpSerializer.getVersion()] = tmpSerializer;
        this.serializer = tmpSerializer; //we serialize using the latest version

        this.callParent(arguments);
    },

    /**
     * Adds the specified portal.util.gmap.GMapWrapper state to this serializer
     */
    addMapState : function(map) {
        this.mapState = {
            center : {
                lat : map.getCenter().getLatitude(),
                lng : map.getCenter().getLongitude()
            },
            zoom : map.getZoom()
        };
    },

    /**
     * Internal use only - returns an array of objects representing a bare minimum OnlineResource
     */
    _serializeOnlineResources : function(orArray) {
        var serialized = [];
        for (var i = 0; i < orArray.length; i++) {
            serialized.push({
                url : orArray[i].get('url'),
                type : orArray[i].get('type'),
                name : orArray[i].get('name'),
                description : orArray[i].get('description')
            });
        }
        return serialized;
    },

    /**
     * Internal use only - returns an object representing a bare minimum activeLayerRecord
     */
    _serializeLayer : function(layer) {
        var source = layer.get('sourceType');
        var filterer = layer.get('filterer');
        var renderer = layer.get('renderer');

        //Known layers have a persistable ID, CSWRecords do NOT
        //This means we have to do a 'best effort' to identify a CSWRecord
        if (source === portal.layer.Layer.KNOWN_LAYER) {
            return {
                id : layer.get('id'), //This is only persistent for KnownLayers
                filter : filterer.getParameters(),
                source : source               
            };            
        } else if (source === portal.layer.Layer.CSW_RECORD || source === 'search') {
            var cswRecord = layer.get('cswRecords')[0];

            return {
                filter : filterer.getParameters(),
                source : source,   
                customlayer : cswRecord.get('customlayer'),
                onlineResources : this._serializeOnlineResources(cswRecord.get('onlineResources'))
            };
        }

        return null;
    },

    /**
     * Extracts all active layers from the map and adds them
     * to the stored map state.
     *
     * @param map the OL map wrapper. Contains a portal.layer.LayerStore.
     */
    addLayers : function(map) {
        // get the map's active layer store
        var activeLayerStore = ActiveLayerManager.getActiveLayerStore(map);
        if (activeLayerStore) {
            for (var i = 0; i < activeLayerStore.getCount(); i++) {
                var layer = activeLayerStore.getAt(i);

                //VT: Unable to support KML perm link at this stage because of the source and size of the kml file.
                if (!layer || layer.get('sourceType') === portal.layer.Layer.KML_RECORD) {
                    continue;
                }

                var serializedLayer = this._serializeLayer(layer);
                this.serializedLayers.push(serializedLayer);
            }
        }
    },

    /**
     * Generates a string that represents the entire state of this serializer
     * 
     * callback - function(string state, string version) will be passed the serialisation string asynchronously and the identifiable version of the serialiser used in the encoding
     */
    serialize : function(callback) {      
        this.serializer.serialize(this.mapState, this.serializedLayers, Ext.bind(function(stateStr) {            
            callback(portal.util.Base64.encode(stateStr), this.serializer.getVersion());
        }, this));
    },

    //Given a serialization string this function attempts to calculate the 'schema' of the serialization object for backwards compatiblity
    _guessSerializationVersion : function(serializationStr) {
        //Does this even remotely resemble JSON??
        if (serializationStr &&
                serializationStr.charAt(0) === '{' &&
                serializationStr.charAt(serializationStr.length - 1) === '}') {
            var serializationObj = Ext.JSON.decode(serializationStr);
            
            if (Ext.isNumber(serializationObj.v)) {
                return serializationObj.v;
            }
            
            //This is to support the original serialization object which had no version stamp
            return 0;
        } else {
            //Then it's probably LZMA encoded
            return 3;
        }
    },

    /**
     * Attempts to deserialize (asynchronously) the specified serializationString and 
     * apply its state to this object.
     *
     * serializationString - String - A serialization string generated by a prior call to serialize
     * serializationVersion - String - The serializer version used to encode serializationString. If null, this will be guessed  
     * callback - function(boolean, String) - Passed a boolean with success/failure AFTER deserialisation has finished
     */
    deserialize : function(serializationString, serializationVersion, callback) {
        var b64Decoded = portal.util.Base64.decode(serializationString);
        
        if (!serializationVersion) {
            serializationVersion = this._guessSerializationVersion(b64Decoded);
        }
        
        var deserializer = this.baseSerializers[serializationVersion];
        if (!deserializer) {
            callback(false, 'Unsupported serialization version');
            return;
        }

        deserializer.deserialize(b64Decoded, Ext.bind(function(deserializedState) {
            this.mapState = deserializedState.mapState;
            this.serializedLayers = deserializedState.serializedLayers;
            
            callback(true);
        }, this));
    }
});
/**
 * Represents a simple point based marker as implemented by the OpenLayers API
 */
Ext.define('portal.map.openlayers.primitives.Marker', {

    extend : 'portal.map.primitives.Marker',

    config : {
        /**
         * Instance of OpenLayers.Feature.Vector
         */
        vector : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.Marker's constructor options
     *
     * tooltip : String - Tooltip to show on mouseover of this marker (if supported).
     * point : portal.map.Point - location of this marker
     * icon : portal.map.Icon - Information about the icon used to render this marker.
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        var point = this.getPoint();
        var icon = this.getIcon();

        //Style info about the icon
        var iconWidth = undefined;
        var iconHeight = undefined;
        var iconOffsetX = undefined;
        var iconOffsetY = undefined;
        if (icon && Ext.isNumber(icon.getWidth()) && Ext.isNumber(icon.getHeight())) {
            iconWidth = icon.getWidth();
            iconHeight = icon.getHeight();
        }
        if (icon && Ext.isNumber(icon.getAnchorOffsetX()) && Ext.isNumber(icon.getAnchorOffsetY())) {
            iconOffsetX = (0 - icon.getAnchorOffsetX());
            iconOffsetY = (0 - icon.getAnchorOffsetY());
        }

        //Construct our feature
        var olPoint = new OpenLayers.Geometry.Point(point.getLongitude(), point.getLatitude());
        var vector = new OpenLayers.Feature.Vector(olPoint, {
            portalBasePrimitive : this
        }, {
            externalGraphic : icon.getUrl(),
            graphicWidth : iconWidth,
            graphicHeight : iconHeight,
            graphicXOffset : iconOffsetX,
            graphicYOffset : iconOffsetY,
            cursor : 'pointer',
            graphicTitle : cfg.tooltip

        });

        this.setVector(vector);


    }
});/**
 * Represents a simple point based marker as implemented by the Gmap API
 */
Ext.define('portal.map.gmap.primitives.Marker', {

    extend : 'portal.map.primitives.Marker',

    config : {
        /**
         * Instance of GMap2 Marker
         */
        marker : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.Marker's constructor options
     *
     * tooltip : String - Tooltip to show on mouseover of this marker (if supported).
     * point : portal.map.Point - location of this marker
     * icon : portal.map.Icon - Information about the icon used to render this marker.
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        var point = this.getPoint();
        var icon = this.getIcon();

        var latLng = new GLatLng(point.getLatitude(), point.getLongitude());
        var gIcon = new GIcon(G_DEFAULT_ICON, icon.getUrl());
        gIcon.shadow = null;
        if (Ext.isNumber(icon.getWidth()) && Ext.isNumber(icon.getHeight())) {
            gIcon.iconSize = new GSize(icon.getWidth(), icon.getHeight());
        }
        if (Ext.isNumber(icon.getAnchorOffsetX()) && Ext.isNumber(icon.getAnchorOffsetY())) {
            gIcon.iconAnchor = new GPoint(icon.getAnchorOffsetX(), icon.getAnchorOffsetY());
            gIcon.infoWindowAnchor = new GPoint(icon.getAnchorOffsetX(), icon.getAnchorOffsetY());
        }

        var marker = new GMarker(latLng, {icon: gIcon, title: cfg.tooltip});

        //Overload marker with useful info
        marker._portalBasePrimitive = this;

        this.setMarker(marker);
    }
});/**
 * Represents a simple point based marker
 */
Ext.define('portal.map.primitives.Marker', {

    extend : 'portal.map.primitives.BasePrimitive',

    config : {
        /**
         * String - Tooltip to show on mouseover of this marker (if supported).
         */
        tooltip : '',
        /**
         * portal.map.Point - Location of this marker
         */
        point : null,
        /**
         * portal.map.Icon - Information about the icon used to render this marker.
         */
        icon : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * tooltip : String - Tooltip to show on mouseover of this marker (if supported).
     * point : portal.map.Point - location of this marker
     * icon : portal.map.Icon - Information about the icon used to render this marker.
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        this.setTooltip(cfg.tooltip);
        this.setPoint(cfg.point);
        this.setIcon(cfg.icon);
    }
});/**
 * A simple data structure resembling a map that has events for
 * whenever key(s) in said map change
 *
 * events :
 *      change(portal.layer.filterer.Filterer this, String[] keys)
 *          Fired whenever the map changes, passed an array of all keys that have changed.
 */
Ext.define('portal.util.ObservableMap', {
    extend: 'Ext.util.Observable',

    parameters : null,

    constructor: function(config){
       

        // Copy configured listeners into *this* object so that the base class's
        // constructor will add them.
        this.listeners = config.listeners;
        this.parameters = {};

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Gets the set of parameters configured within this map as
     * a simple javascript object with key/value pairs
     *
     * returns - a javascript object
     */
    getParameters : function() {
        return Ext.apply({}, this.parameters); //return a copy of the internal object
    },

    /**
     * Given a set of parameters as a plain old javascript object of
     * key/value pairs, apply it's contents to this map.
     *
     * This is a useful function if you want to set multiple parameters
     * and only raise a single event
     *
     * parameters - a plain old javascript object
     * clearFirst - [Optional] if true, then the internal map will be cleared BEFORE any values are added
     */
    setParameters : function(parameters, clearFirst) {
        //keep track of the list of keys that changed
        //Initially only the values in parameters are the values that are changing
        var changedParameters = Ext.apply({}, parameters);

        //We can optionally wipe out all parameters during this function
        if (clearFirst) {
            //However, if we are clearing the map first then a lot of values will be changing
            changedParameters = Ext.apply(changedParameters, this.parameters);
            this.parameters = {}; //clearing first is really easy
        }

        //Apply parameter values to the internal map
        this.parameters = Ext.apply(this.parameters, parameters);

        //Enumerate our list of changed parameters to pass the values
        //to whatever event handlers are listening
        var key;
        var keyList = [];
        for (key in changedParameters) {
            keyList.push(key);
        }
        this.fireEvent('change', this, keyList);
    },

    /**
     * Sets a single parameter of this map
     *
     * key - a string key whose value will be set. Will override any existing key of the same name
     * value - The object value to set
     * quiet[optional] - setting to true won't fire any events
     */
    setParameter : function(key, value, quiet){
        this.parameters[key] = value;
        if(quiet){
            return;
        }
        this.fireEvent('change', this, [key]);
    },

    /**
     * Gets the value of the specified key as an Object
     *
     * key - A string key whose value will be fetched.
     *
     * returns - a javascript object matching key
     */
    getParameter : function(key) {
        return this.parameters[key];
    },

    /**
     * Returns a shallow copy of all of this map's objects and keys.
     */
    clone : function() {
        var clonedObj = Ext.create('portal.util.ObservableMap', {});
        Ext.apply(clonedObj.parameters, this.parameters);
        return clonedObj;
    }
});/**
 * an OnlineResource is a 'fundamental' unit of all CSWRecords, it represents
 * a resource available somewhere in the web. An OnlineResource is basically
 * a URL coupled with identifying information to better understand what that URL
 * represents.
 *
 * The name field will typically represent a descriptive name but as a rule of
 * thumb will represent something more rigorous for some values of type:
 *      WFS - name will represent the typeName
 *      WMS - name will represent the layerName
 *      WCS - name will represent the coverageName
 *      OPeNDAP - name will represent the variable name
 */
Ext.define('portal.csw.OnlineResource', {
    extend: 'Ext.data.Model',

    //Static value representations of the 'type' field
    statics : {
        WMS : 'WMS', //represents a Web Map Service
        WFS : 'WFS', //represents a Web Feature Service
        WCS : 'WCS', //represents a Web Coverage Service
        WWW : 'WWW', //represents a regular HTTP web link
        OPeNDAP : 'OPeNDAP', //represents an OPeNDAP service
        FTP : 'FTP', //represents a File Transfer Protocol service
        SOS : 'SOS', //represents a SOS service
        UNSUPPORTED : 'Unsupported', //The backend doesn't recognise the type of service/protocol
        IRIS : 'IRIS', // IRIS web service
        CSWService : 'CSWService', // A CSW Service such as a GeoNetwork endpoint.
        NCSS : 'NCSS', // A NetCDF Subset Service.

        /**
         * Utility for turning the various portal.csw.OnlineResource.* enums into an English readable string.
         * Returns null if type isn't recognised
         */
        typeToString : function(type,version) {
            switch (type) {
            case portal.csw.OnlineResource.WWW:
            case portal.csw.OnlineResource.FTP:
                return 'Web Link';
            case portal.csw.OnlineResource.WFS:
                return 'OGC Web Feature Service 1.1.0';
            case portal.csw.OnlineResource.WMS:
                if(version=="1.3.0"){
                    return 'OGC Web Map Service 1.3.0';
                }else{
                    return 'OGC Web Map Service 1.1.1';
                }
            case portal.csw.OnlineResource.WCS:
                return 'OGC Web Coverage Service 1.0.0';
            case portal.csw.OnlineResource.OPeNDAP:
                return 'OPeNDAP Service';
            case portal.csw.OnlineResource.SOS:
                return 'Sensor Observation Service 2.0.0';
            case portal.csw.OnlineResource.IRIS:
                return 'IRIS Web Service';
            case portal.csw.OnlineResource.CSWService:
                return 'CSW Service';
            case portal.csw.OnlineResource.NCSS:
                return 'NetCDF Subset Service';
            // don't display a group for "Unsupported" resources, even though there might be information in there        
            case portal.csw.OnlineResource.UNSUPPORTED:
            default:
                return null;
            }
        },

        /**
         * Static utility function for extracting a subset of OnlineResources from an array
         * according to a variety of filter options
         *
         * name - [Set to undefined to not filter] The name to filter by
         * description - [Set to undefined to not filter] The description to filter by
         * url - [Set to undefined to not filter] The url to filter by
         * strict - if set to true will filter the full url else only filter the host. Defaults to true
         * array - An array of portal.csw.OnlineResource objects
         */
        getFilteredFromArray : function(array, type, name, description, url, strict) {
            var filtered = [];
            if(!array){
                return filtered;
            }
            for (var i = 0; i < array.length; i++) {
                var cmp = array[i];

                if (type !== undefined && cmp.get('type') !== type) {
                    continue;
                }

                if (name !== undefined && cmp.get('name') !== name) {
                    continue;
                }

                if (description !== undefined && cmp.get('description') !== description) {
                    continue;
                }

                if (url !== undefined && (strict === true || strict === undefined) && cmp.get('url') !== url) {
                    continue;
                }

                if (url !== undefined && strict === false && this.getHostname(cmp.url) !== this.getHostname(url)) {
                    var re = new RegExp('^(?:f|ht)tp(?:s)?\://([^/]+)', 'im');
                    var hostName = cmp.get('url').match(re)[1].toString();

                    if (url !== hostName) {
                        continue;
                    }
                }

                filtered.push(cmp);
            }
            return filtered;
        }
    },

    fields: [
        {name: 'url', type: 'string'}, //A URL representing the location of the remote resource
        {name: 'name', type: 'string'}, //A name for this resource - it's use will vary depending on type (see comments at top of page)
        {name: 'description', type: 'string'}, //A human readable description of this resource
        {name: 'type', type: 'string'}, //An enumerated type
        {name: 'version' , type:'string'}
    ]
});

/**
 * A Panel specialisation for allowing the user to browse
 * the online resource contents of a set of portal.csw.OnlineResource
 * objects.
 */
Ext.define('portal.widgets.panel.OnlineResourcePanel', {
    extend : 'Ext.grid.Panel',
    alias : 'widget.onlineresourcepanel',

    cswRecords : null, //Array of portal.csw.CSWRecord objects

    /**
     * Accepts all Ext.grid.Panel options as well as
     * {
     *  cswRecords : single instance of array of portal.csw.CSWRecord objects
     *  parentRecord: The KnownLayer/KML/CSWRecord that owns these records
     *  nagiosErrorIcon: Error icon to show when rendering resources from a host that nagios has declared as "down"
     *  allow
     * }
     */
    constructor : function(cfg) {
        // Ensures this.cswRecords is an array:
        this.cswRecords = [].concat(cfg.cswRecords);
        
        this.nagiosErrorIcon = Ext.isEmpty(cfg.nagiosErrorIcon) ? 'portal-core/img/warning.png' : cfg.nagiosErrorIcon;

        //Generate our flattened 'data items' list for rendering to the grid
        var dataItems = portal.widgets.panel.OnlineResourcePanelRow.parseCswRecords(this.cswRecords);

        var groupingFeature = Ext.create('Ext.grid.feature.Grouping',{
            groupHeaderTpl: '{name} ({[values.rows.length]} {[values.rows.length > 1 ? "Items" : "Item"]})'
        });

        //The following two Configs variables can be set by the owner
        var sortable = true;
        var hideHeaders = true;
        if (typeof(cfg.hideHeaders) !== 'undefined' && cfg.hideHeaders != null) {
            hideHeaders = cfg.hideHeaders;
        }
        if (typeof(cfg.sortable) !== 'undefined' && cfg.sortable != null) {
            sortable = cfg.sortable;
        }
        
        //Build up our set of "problem" hosts for marking with a special icon
        this.problemHosts = [];
        if (cfg.parentRecord && 
            cfg.parentRecord instanceof portal.knownlayer.KnownLayer && 
            cfg.parentRecord.containsNagiosFailures()) {
            this.problemHosts = cfg.parentRecord.get('nagiosFailingHosts');
        }

        //We allow the owner to specify additional columns
        var columns = [{
            //Title column
            dataIndex: 'onlineResource',
            menuDisabled: true,
            sortable: sortable,
            flex: 1,
            renderer: Ext.bind(this._titleRenderer, this)
        },{
            width: 55,
            dataIndex: 'onlineResource',
            menuDisabled: true,
            renderer: Ext.bind(this._errorRenderer, this)
        },{
            dataIndex: 'onlineResource',
            width: 140,
            renderer: Ext.bind(this._previewRenderer, this)
        }];
        if (cfg.columns) {
            columns = columns.concat(cfg.columns);
        }

        //Build our configuration object
        Ext.apply(cfg, {
            selModel: cfg.selModel,
            features : [groupingFeature],
            store : Ext.create('Ext.data.Store', {
                groupField : 'group',
                model : 'portal.widgets.panel.OnlineResourcePanelRow',
                data : dataItems
            }),
            plugins : [{
                ptype : 'selectablegrid'
            }],
            hideHeaders : hideHeaders,
            columns: columns,
            viewConfig: {
                enableTextSelection: true
              }
        });

        this.callParent(arguments);
    },
    
    _errorRenderer : function(value, metaData, record, row, col, store, gridView) {
        var onlineResource = record.get('onlineResource');
        var url = onlineResource.get('url');
        var matchesError = false;
        Ext.each(this.problemHosts, function(host) {
            if (url.indexOf(host) >= 0) {
                matchesError = true;
                return false;
            }
        })
        
        if (matchesError) {
            return Ext.DomHelper.markup({
                tag : 'img',
                src: this.nagiosErrorIcon,
                width: 32,
                height: 32,
                style: 'margin-top:6px;',
                title: 'This service is reported to be experiencing issues at the moment. Some aspects of this layer may not load/work.'
            });
        }
        
        return '';
    },

    _titleRenderer : function(value, metaData, record, row, col, store, gridView) {
        var onlineResource = record.get('onlineResource');
        var cswRecord = record.get('cswRecord');
        var name = onlineResource.get('name');
        var url = onlineResource.get('url');
        var description = onlineResource.get('description');

        //Ensure there is a title (even it is just '<Untitled>'
        if (!name || name.length === 0) {
            name = '&gt;Untitled&lt;';
        }

        //Truncate description
        var maxLength = 190;
        if (description.length > maxLength) {
            description = description.substring(0, maxLength) + '...';
        }

        //Render our HTML
        switch(onlineResource.get('type')) {
        case portal.csw.OnlineResource.WWW:
        case portal.csw.OnlineResource.FTP:
        case portal.csw.OnlineResource.IRIS:
        case portal.csw.OnlineResource.UNSUPPORTED:
            return Ext.DomHelper.markup({
                tag : 'div',
                children : [{
                    tag : 'a',
                    target : '_blank',
                    href : url,
                    children : [{
                        tag : 'b',
                        html : name
                    }]
                },{
                    tag : 'br'
                },{
                    tag : 'span',
                    style : {
                        color : '#555'
                    },
                    html : description
                }]
            });
        default:
            return Ext.DomHelper.markup({
                tag : 'div',
                children : [{
                    tag : 'b',
                    html : name
                },{
                    tag : 'br'
                },{
                    tag : 'span',
                    style : {
                        color : '#555'
                    },
                    children : [{
                        html : url
                    },{
                        html : description
                    }]
                }]
            });
        }
    },

    _previewRenderer : function(value, metaData, record, row, col, store, gridView) {
        var onlineRes = record.get('onlineResource');
        var cswRecord = record.get('cswRecord');
        var url = onlineRes.get('url');
        var name = onlineRes.get('name');
        var description = onlineRes.get('description');

        //We preview types differently
        switch(onlineRes.get('type')) {
        case portal.csw.OnlineResource.WFS:
            var getFeatureUrl = url + this.internalURLSeperator(url) + 'SERVICE=WFS&REQUEST=GetFeature&VERSION=1.1.0&maxFeatures=5&typeName=' + name;
            return Ext.DomHelper.markup({
                tag : 'a',
                target : '_blank',
                href : getFeatureUrl,
                html : 'First 5 features'
            });
        case portal.csw.OnlineResource.WCS:
            var describeCoverageUrl = url + this.internalURLSeperator(url) + 'SERVICE=WCS&REQUEST=DescribeCoverage&VERSION=1.0.0&coverage=' + name;
            return Ext.DomHelper.markup({
                tag : 'a',
                target : '_blank',
                href : describeCoverageUrl,
                html : 'DescribeCoverage response'
            });
        case portal.csw.OnlineResource.OPeNDAP:
            return Ext.DomHelper.markup({
                tag : 'a',
                target : '_blank',
                href : url + '.html',
                html : 'OPeNDAP Data access form'
            });
        case portal.csw.OnlineResource.SOS:
            var getObservations = url + this.internalURLSeperator(url) + 'SERVICE=SOS&REQUEST=GetObservation&VERSION=2.0.0&OFFERING=' + escape(name) + '&OBSERVEDPROPERTY=' + escape(description) + '&RESPONSEFORMAT=' + escape('http://www.opengis.net/om/2.0');
            return Ext.DomHelper.markup({
                tag : 'a',
                target : '_blank',
                href : getObservations,
                html : 'Observations for ' + description
            });
        case portal.csw.OnlineResource.WMS:

            //To generate the url we will need to use the bounding box to make the request
            //To avoid distortion, we also scale the width height independently
            var geoEls = cswRecord.get('geographicElements');
            if (geoEls && geoEls.length > 0) {
                var superBbox = geoEls[0];
                for (var i = 1; i < geoEls.length; i++) {
                    superBbox = superBbox.combine(geoEls[i]);
                }

                //Set our width to a constant and scale the height appropriately
                var heightRatio = (superBbox.northBoundLatitude - superBbox.southBoundLatitude) /
                                  (superBbox.eastBoundLongitude - superBbox.westBoundLongitude);
                var width = 512;
                var height = Math.floor(width * heightRatio);

                var thumbWidth = width;
                var thumbHeight = height;

                //Scale our thumbnail appropriately
                if (thumbWidth > 128) {
                    thumbWidth = 128;
                    thumbHeight = thumbWidth * heightRatio;
                }

                var getMapUrl = '';
                if(cswRecord.get('version')=='1.3.0'){
                    getMapUrl = portal.map.primitives.BaseWMSPrimitive.getWms_130_Url(url, name, superBbox, width, height);
                    console.log("1.1.1:" + portal.map.primitives.BaseWMSPrimitive.getWmsUrl(url, name, superBbox, width, height));
                    console.log("1.3.0:"+portal.map.primitives.BaseWMSPrimitive.getWms_130_Url(url, name, superBbox, width, height));
                }else{
                    getMapUrl = portal.map.primitives.BaseWMSPrimitive.getWmsUrl(url, name, superBbox, width, height);
                }

                return Ext.DomHelper.markup({
                    tag : 'a',
                    target : '_blank',
                    href : getMapUrl,
                    children : [{
                        tag : 'img',
                        width : thumbWidth,
                        height : thumbHeight,
                        alt : 'Loading preview...',
                        src : getMapUrl
                    }]
                });
            }
            return 'Unable to preview WMS';
        default :
            return '';
        }
    },

    /**
     * Given a URL this will determine the correct character that can be appended
     * so that a number of URL parameters can also be appended
     *
     * See AUS-1931 for why this function should NOT exist
     */
    internalURLSeperator : function(url) {
        var lastChar = url[url.length - 1];
        if (lastChar == '?') {
            return '';
        } else if (lastChar === '&') {
            return '';
        } else if (url.indexOf('?') >= 0) {
            return '&';
        } else {
            return '?';
        }
    }
});
/**
 * Convenience class for representing the rows in the OnlineResourcesPanel
 */
Ext.define('portal.widgets.panel.OnlineResourcePanelRow', {
    extend : 'Ext.data.Model',

    statics : {
        /**
         * Turns an array of portal.csw.CSWRecord objects into an equivalent array of
         * portal.widgets.panel.OnlineResourcePanelRow objects
         */
        parseCswRecords : function(cswRecords) {
            var dataItems = [];
            for (var i = 0; i < cswRecords.length; i++) {
                var onlineResources = cswRecords[i].getAllChildOnlineResources();
                for (var j = 0; j < onlineResources.length; j++) {

                    //ensure we have a type we want to describe
                    var group = portal.csw.OnlineResource.typeToString(onlineResources[j].get('type'),onlineResources[j].get('version'));
                    if (!group) {
                        continue; //don't include anything else
                    }

                    dataItems.push(Ext.create('portal.widgets.panel.OnlineResourcePanelRow',{
                        group : group,
                        onlineResource : onlineResources[j],
                        cswRecord : cswRecords[i]
                    }));
                }
            }

            return dataItems;
        }
    },

    fields: [
             {name : 'group', type: 'string'},
             {name : 'onlineResource', type: 'auto'},
             {name : 'cswRecord', type: 'auto'}
    ]
});/**
 * An Ext.data.Types extension for portal.csw.OnlineResource
 *
 * See http://docs.sencha.com/ext-js/4-0/#!/api/Ext.data.Types
 */
Ext.define('portal.csw.OnlineResourceType', {
    singleton: true,
    requires: ['Ext.data.SortTypes',
               'Ext.data.Types']
}, function() {
    Ext.apply(portal.csw.OnlineResourceType, {
        convert: function(v, data) {
            if (Ext.isArray(v)) {
                var newArray = [];
                for (var i = 0; i < v.length; i++) {
                    newArray.push(this.convert(v[i]));
                }
                return newArray;
            } else if (v instanceof portal.csw.OnlineResource) {
                return v;
            }else if (Ext.isObject(v)) {
                return Ext.create('portal.csw.OnlineResource', v);
            }

            return null;
        },
        sortType: Ext.data.SortTypes.none,
        type: 'portal.csw.OnlineResource'
    });
});/**
 * Class for providing an download interface into an OPeNDAP service
 */
Ext.define('portal.layer.downloader.coverage.OPeNDAPDownloader', {
    extend : 'portal.layer.downloader.Downloader',

    map : null,

    /**
     * Utility function for extracting all selected parameters as a config object
     */
    _getOPeNDAPParameters : function (win) {

        //Generates parameters recursively
        //as an array of constraints
        var generateConstraints = function(component) {
            if (!component) {
                return null;
            }

            if (component.initialConfig.variableType === 'axis') {
                var fromField = component.getComponent(0);
                var toField = component.getComponent(1);

                var obj = {
                    type        : component.initialConfig.variableType,
                    name        : component.initialConfig.name
                };

                if (component.initialConfig.usingDimensionBounds) {
                    obj.dimensionBounds = {
                        from        : parseFloat(fromField.getValue()),
                        to          : parseFloat(toField.getValue())
                    };
                } else {
                    obj.valueBounds = {
                        from        : parseFloat(fromField.getValue()),
                        to          : parseFloat(toField.getValue())
                    };
                }

                return obj;
            } else if (component.initialConfig.variableType === 'grid') {
                var childAxes = [];
                for (var i = 0; i < frm.items.getCount(); i++) {
                    var child = generateConstraints(component.items.get(i));
                    if (child) {
                        childAxes.push(child);
                    }
                }
                return {
                    type        : component.initialConfig.variableType,
                    name        : component.initialConfig.name,
                    axes        : childAxes
                };
            }

            return null;
        };

        var frm = win.getComponent('bounding-form');
        var fldSet = frm.getComponent('bounding-fieldset');
        var params = {
            opendapUrl : fldSet.getComponent('url').getValue(),
            downloadFormat : fldSet.getComponent('format').getValue()
        };

        //Generate constraints component
        var variableConstraints = [];
        for (var i = 0; i < frm.items.getCount(); i++) {
            var component = frm.items.get(i);

            if(component && !component.disabled && (component.checkboxToggle !== true || component.collapsed === false)) {
                var constraint = generateConstraints(component);
                if (constraint) {
                    variableConstraints.push(constraint);
                }
            }
        }

        params.constraints = Ext.JSON.encode({
            constraints : variableConstraints
        });

        return params;
    },

    /**
     * Updates the loading status of window to the specified message. If the message is null,
     * the loading status will be hidden
     */
    _updateLoadingStatus : function(win, newMessage) {
        var loadingLabel = win.getComponent('bounding-form').getComponent('bounding-fieldset').getComponent('loading');

        if (newMessage) {
            loadingLabel.setText(newMessage);
            loadingLabel.show();
        } else {
            loadingLabel.hide();
            loadingLabel.setText('');
        }
    },

    /**
     * Recursively generates a field set for a given variable
     */
    _generateVariableFieldSet : function(variable) {
        if (variable.type === 'axis') {
            var bounds;
            var title;
            var usingDimensionBounds;

            if (variable.valueBounds) {
                bounds = variable.valueBounds;
                title = variable.name + '[' + bounds.from + ', ' + bounds.to + ']' + ' - ' + variable.units;
                if(variable.name==='lat'){
                    var currentVisibleBounds = this.map.getVisibleMapBounds();
                    bounds.fromValue = (currentVisibleBounds.southBoundLatitude > bounds.from)?currentVisibleBounds.southBoundLatitude:bounds.from ;
                    bounds.toValue = (currentVisibleBounds.northBoundLatitude < bounds.to)?currentVisibleBounds.northBoundLatitude:bounds.to;
                }else if(variable.name==='lon'){
                    var currentVisibleBounds = this.map.getVisibleMapBounds();
                    currentVisibleBounds.eastBoundLongitude = portal.util.BBox.datelineCorrection(currentVisibleBounds.eastBoundLongitude,"EPSG:4326")
                    bounds.fromValue = (currentVisibleBounds.westBoundLongitude > bounds.from)?currentVisibleBounds.westBoundLongitude:bounds.from ;
                    bounds.toValue = (currentVisibleBounds.eastBoundLongitude < bounds.to)?currentVisibleBounds.eastBoundLongitude:bounds.to;
                }
                usingDimensionBounds = false;
            } else {
                bounds = variable.dimensionBounds;
                title = variable.name + '[' + bounds.from + ', ' + bounds.to + ']';
                usingDimensionBounds = true;
            }

            return {
                xtype       : 'fieldset',
                name        : variable.name,
                variableType: variable.type,
                title       : title,
                usingDimensionBounds : usingDimensionBounds,
                items       : [{
                    xtype       : 'numberfield',
                    fieldLabel  : 'From',
                    allowBlank  : false,
                    value       : bounds.fromValue,
                    minValue    : bounds.from,
                    maxValue    : bounds.to,
                    allowDecimals : !usingDimensionBounds,
                    //.62049699996703
                    decimalPrecision : 15,
                    anchor      : '-50'
                }, {
                    xtype       : 'numberfield',
                    fieldLabel  : 'To',
                    allowBlank  : false,
                    value       : bounds.toValue,
                    minValue    : bounds.from,
                    maxValue    : bounds.to,
                    allowDecimals : !usingDimensionBounds,
                    decimalPrecision : 15,
                    anchor      : '-50'
                }]
            };
        } else if (variable.type === 'grid') {
            var items = [];
            for (var i = 0; i < variable.axes.length; i++) {
                items.push(this._generateVariableFieldSet(variable.axes[i]));
            }

            return {
                xtype           : 'fieldset',
                title           : variable.name + ' - ' + variable.units,
                name            : variable.name,
                variableType    : variable.type,
                checkboxToggle  : true,
                items           : items
                //Listeners that enabled/disabled fieldsets on expand/collapse have been
                //removed because this is not compatible with IE7. It causes the checkbox
                //itself to become disabled.
            };
        }

        throw ('Unable to parse type=' + variable.type);
    },

    /**
     * Given a list of variables, this function will add the representation of those variable constraints
     * to the specified window
     */
    _variableListToWindow : function (win, variables) {
        var frm = win.getComponent('bounding-form');

        for (var i = 0; i < variables.length; i++) {
            var variableFldSet = this._generateVariableFieldSet(variables[i]);
            frm.add(variableFldSet);
        }

        win.doLayout();
    },

    /**
     * Called when the window skeleton first opens and is rendered
     */
    _onWindowOpen : function(win, eOpts, opendapUrl, variableName) {
        //Download our variable list - this will be used to generate our form parameters
        portal.util.Ajax.request({
            url     : 'opendapGetVariables.do',
            scope : this,
            params  : {
                opendapUrl : opendapUrl,
                variableName : variableName
            },
            callback : function(success, data, message) {
                //Check for errors
                if (!success) {
                    this._updateLoadingStatus(win, 'Error: ' + message);
                    return;
                }

                //Remove loading
                this._updateLoadingStatus(win, null);

                //Update our form with the downloaded variables
                this._variableListToWindow(win, data);
            }
        });
    },

    /**
     * Overridden method, See parent class for details.
     */
    downloadData : function(layer, resources, renderedFilterer, currentFilterer) {
        this.map=layer.get('renderer').map;
        var opendapResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.OPeNDAP);
        if (opendapResources.length === 0) {
            return;
        }
        var opendapResource = opendapResources[0];  //we are only providing a download for 1 resource
        var opendapUrl = opendapResource.get('url');
        var variableName = opendapResource.get('name');

        // See whether or not there is an FTP resource:
        var ftpResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.FTP);
        var ftpURL = ftpResources.length > 0 ? ftpResources[0].get('url') : '';

        //Our objective is to build up a list of field sets
        var fieldSetsToDisplay = [];

        var formatsStore = Ext.create('Ext.data.Store', {
            fields   : ['format'],
            proxy : {
                type : 'ajax',
                url : 'opendapGetSupportedFormats.do',
                reader : {
                    type : 'array'
                }
            },
            autoLoad : true
        });

        //Create our popup with no variable fieldsets. These will be added later
        var win = Ext.create('Ext.window.Window', {
            layout : 'fit',
            modal : true,
            buttonAlign : 'right',
            title : 'OPeNDAP Download',
            height : 600,
            width : 500,
            items:[{
                // Bounding form
                xtype :'form',
                itemId : 'bounding-form',
                items : [{
                    xtype : 'fieldset',
                    itemId : 'bounding-fieldset',
                    title : 'Required Information',
                    items : [{
                        xtype : 'textfield',
                        itemId : 'url',
                        fieldLabel : 'URL',
                        value : opendapUrl,
                        name : 'opendapUrl',
                        readOnly : true,
                        anchor : '-50'
                    },{
                        xtype : 'combo',
                        itemId : 'format',
                        name : 'format',
                        fieldLabel : 'Format',
                        emptyText : '',
                        forceSelection : true,
                        allowBlank : false,
                        mode : 'local',
                        store : formatsStore,
                        typeAhead : true,
                        triggerAction : 'all',
                        displayField : 'format',
                        anchor : '-50',
                        valueField : 'format'
                    },{
                        xtype : 'label',
                        itemId : 'loading',
                        text : 'Loading...'
                    }]
                }]
            }],
            buttons:[{
                xtype : 'button',
                text : 'Download',
                iconCls : 'download',
                scope : this,
                handler: function(button) {
                    var win = button.ownerCt.ownerCt;
                    var frm = win.getComponent('bounding-form');
                    var loadingLabel = frm.getComponent('bounding-fieldset').getComponent('loading');

                    //Our form must be valid + finished loading
                    if (!frm.getForm().isValid() || !loadingLabel.isHidden()) {
                        Ext.Msg.alert('Invalid Fields','One or more fields are invalid');
                        return;
                    }

                    //POSTing JSON through the FileDownloader seems to cause some issues with FF
                    //This is our weird workaround
                    var params = this._getOPeNDAPParameters(win);

                    var url = 'opendapMakeRequest.do?constraints=' + escape(params.constraints) +
                        (ftpURL ? '&' + Ext.Object.toQueryString({ftpURL: ftpURL}) : '');

                    delete params.constraints;

                    portal.util.FileDownloader.downloadFile(url, params);
                }
            }],
            listeners : {
                afterrender : Ext.bind(this._onWindowOpen, this, [opendapUrl, variableName], true)
            }
        });

        win.show();
    }
});/**
 * Concrete implementation for OpenLayers
 */
Ext.define('portal.map.openlayers.OpenLayersMap', {
    extend : 'portal.map.BaseMap',

    map : null, //Instance of OpenLayers.Map
    vectorLayers : [],    
    selectControl : null,
//    mapCreatedEventListeners : [],  // Listeners (functions) to call once the map has been created
    layerSwitcher : null,           // Keep as a global so can check if has been created

    constructor : function(cfg) {
        this.callParent(arguments);
        
        // If the portal (eg. Geoscience Portal but NOT it as GP already handles this) doesn't call 
        // renderBaseMap() then the OpenLayers LayerSwitcher won't appear so we set a timeout to 
        // create it if it hasn't
        if (!cfg.portalIsHandlingLayerSwitcher) {
            Ext.defer(this._callDrawOpenLayerSwitcher, 2000, this);
        }
    },
    
    _callDrawOpenLayerSwitcher : function() {
        var meMethod=this._callDrawOpenLayerSwitcher;
        if (this.map) {
            this._drawOpenLayerSwitcher();
        } else {
            // Wait until it is ready
            Ext.defer(meMethod, 250, this);
        }
    },

    /////////////// Unimplemented functions

    /**
     * See parent class for information.
     */
    makeMarker : function(id, tooltip, sourceCswRecord, sourceOnlineResource, sourceLayer, point, icon) {
        return Ext.create('portal.map.openlayers.primitives.Marker', {
            id : id,
            tooltip : tooltip,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            point : point,
            icon : icon
        });
    },

    /**
     * See parent class for information.
     */
    makePolygon : function(id, sourceCswRecord, sourceOnlineResource, sourceLayer, points, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity) {
        return Ext.create('portal.map.openlayers.primitives.Polygon', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            points : points,
            strokeColor : strokeColor,
            strokeWeight : strokeWeight,
            strokeOpacity : strokeOpacity,
            fillColor : fillColor,
            fillOpacity : fillOpacity
        });
    },

    /**
     * See parent class for information.
     */
    makePolyline : function(id, sourceCswRecord,sourceOnlineResource, sourceLayer, points, strokeColor, strokeWeight, strokeOpacity) {
        return Ext.create('portal.map.openlayers.primitives.Polyline', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            points : points,
            strokeColor : strokeColor,
            strokeWeight : strokeWeight,
            strokeOpacity : strokeOpacity
        });
    },

    /**
     * See parent class for information.
     */
    makeWms : function(id, sourceCswRecord, sourceOnlineResource, sourceLayer, wmsUrl, wmsLayer, opacity,sld_body) {
        return Ext.create('portal.map.openlayers.primitives.WMSOverlay', {
            id : id,
            layer : sourceLayer,
            onlineResource : sourceOnlineResource,
            cswRecord : sourceCswRecord,
            wmsUrl : wmsUrl,
            wmsLayer : wmsLayer,
            opacity : opacity,
            map : this.map,
            sld_body : sld_body
        });
    },

    _makeQueryTargetsPolygon : function(polygon, layerStore, longitude, latitude) {
        var queryTargets = [];
        var lonLat = new OpenLayers.LonLat(longitude, latitude);
        lonLat=lonLat.transform('EPSG:4326','EPSG:3857');

        //Iterate all features on the map, those that intersect the given lat/lon should
        //have query targets generated for them as it isn't clear which one the user meant
        //to click
        for(var j = 0; j < this.vectorLayers.length;j++){
            var vectorLayer = this.vectorLayers[j];
            for (var i = 0; i < vectorLayer.features.length; i++) {
                var featureToTest = vectorLayer.features[i];
                if (featureToTest.geometry.atPoint(lonLat)) {
                    var primitiveToTest = featureToTest.attributes['portalBasePrimitive'];
                    if (primitiveToTest) {
                        var id = primitiveToTest.getId();
                        var onlineResource = primitiveToTest.getOnlineResource();
                        var layer = primitiveToTest.getLayer();
                        var cswRecord = primitiveToTest.getCswRecord();
    
                        queryTargets.push(Ext.create('portal.layer.querier.QueryTarget', {
                            id : id,
                            lat : latitude,
                            lng : longitude,
                            onlineResource : onlineResource,
                            layer : layer,
                            cswRecord : cswRecord,
                            explicit : true
                        }));
                                              
                    }
                }
            }
        }

        return queryTargets;
    },

    _makeQueryTargetsVector : function(primitive, longitude, latitude) {
        var id = primitive.getId();
        var onlineResource = primitive.getOnlineResource();
        var layer = primitive.getLayer();
        var cswRecord = primitive.getCswRecord();                

        return [Ext.create('portal.layer.querier.QueryTarget', {
            id : id,
            lat : latitude,
            lng : longitude,
            onlineResource : onlineResource,
            layer : layer,
            cswRecord : cswRecord,
            explicit : true
        })];
    },

    _makeQueryTargetsMap : function(layerStore, longitude, latitude) {
        var queryTargets = [];
        if (!layerStore) {
            return queryTargets;
        }
        
        //Iterate everything with WMS/WCS - no way around this :(
        for (var i = 0; i < layerStore.getCount(); i++) {
            var layer = layerStore.getAt(i);
          
            var cswRecords = layer.get('cswRecords');
            for(var j = 0; j < cswRecords.length; j++){

                var cswRecord = cswRecords[j];

                //ensure this click lies within this CSW record
                var containsPoint = false;
                var geoEls = cswRecord.get('geographicElements');
                for (var k = 0; k < geoEls.length; k++) {
                    if (geoEls[k] instanceof portal.util.BBox &&
                        geoEls[k].contains(latitude, longitude)) {
                        containsPoint = true;
                        break;
                    }
                }

                //If it doesn't, don't consider this point for examination
                if (!containsPoint || layer.visible==false) {
                    continue;
                }

                //Finally we don't include WMS query targets if we
                //have WCS queries for the same record
                var allResources = cswRecord.get('onlineResources');
                var wmsResources = portal.csw.OnlineResource.getFilteredFromArray(allResources, portal.csw.OnlineResource.WMS);
                var wcsResources = portal.csw.OnlineResource.getFilteredFromArray(allResources, portal.csw.OnlineResource.WCS);


                //VT: if layerswitcher layer visibility is set to false, then do not query that layer as well.
                if (wmsResources[0]) {
                    var layerSwitcherVisible=true;
                    var layerName=wmsResources[0].get('name');

                    // We loop over the available to controls to find the layer switcher:
                    var layerSwitcher = null;
                    for (var y=0; y < this.map.controls.length; y++) {
                        if (this.map.controls[y] instanceof OpenLayers.Control.LayerSwitcher) {
                            layerSwitcher = this.map.controls[y];
                            break;
                        }
                    }

                    var layerSwitcherState = layerSwitcher.layerStates;
                    for (var z = 0; z < layerSwitcherState.length; z++) {
                        if (layerSwitcherState[z].name === layerName) {
                            layerSwitcherVisible=layerSwitcherState[z].visibility;
                            break;
                        }
                    }

                    if (!layerSwitcherVisible) {
                        continue;
                    }
                }


                var resourcesToIterate = [];
                if (wcsResources.length > 0) {
                    resourcesToIterate = wcsResources;
                } else {
                    resourcesToIterate = wmsResources;
                }

                //Generate our query targets for WMS/WCS layers
                for (var k = 0; k < resourcesToIterate.length; k++) {
                    var type = resourcesToIterate[k].get('type');
                    if (type === portal.csw.OnlineResource.WMS ||
                        type === portal.csw.OnlineResource.WCS) {

                        var serviceFilter = layer.get('filterer').getParameters().serviceFilter;
                        if (serviceFilter) {
                            if (Ext.isArray(serviceFilter)) {
                                serviceFilter = serviceFilter[0];
                            }
                            // layers get filtered based on the service provider
                            // or from a single provider and based on the layer name
                            if (resourcesToIterate[k].get('name') != serviceFilter &&
                                    this._getDomain(resourcesToIterate[k].get('url')) != this._getDomain(serviceFilter)) {
                                continue;
                            }
                        }

                        queryTargets.push(Ext.create('portal.layer.querier.QueryTarget', {
                            id : '',
                            lat : latitude,
                            lng : longitude,
                            cswRecord   : cswRecord,
                            onlineResource : resourcesToIterate[k],
                            layer : layer,
                            explicit : true
                        }));
                                                
                    }
                }
            }
        }

        return queryTargets;
    },

    /**
     * Handler for click events
     *
     * @param vector [Optional] OpenLayers.Feature.Vector the clicked feature (if any)
     * @param e Event The click event that caused this handler to fire
     */
    _onClick : function(vector, e) {
        var primitive = vector ? vector.attributes['portalBasePrimitive'] : null;
        var lonlat = this.map.getLonLatFromViewPortPx(e.xy);
        lonlat = lonlat.transform('EPSG:3857','EPSG:4326');
        var longitude = lonlat.lon;
        var latitude = lonlat.lat;
        var layer = primitive ? primitive.getLayer() : null;

        var queryTargets = [];
        if (primitive && layer && primitive instanceof portal.map.openlayers.primitives.Polygon) {
            queryTargets = this._makeQueryTargetsPolygon(primitive, this.layerStore, longitude, latitude);
        } else if (primitive && layer) {
            queryTargets = this._makeQueryTargetsVector(primitive, longitude, latitude);
        } else {
            queryTargets = this._makeQueryTargetsMap(this.layerStore, longitude, latitude);
        }

        this.fireEvent('query', this, queryTargets);
    },

    /**
     * If the removal of a layer has the same ID has a info window opened, close it.
     */
    closeInfoWindow: function(layerid){
        if(layerid === this.openedInfoLayerId && this.map.popups[0]){
            this.map.removePopup(this.map.popups[0]);
        }
    },

    _onPrimitivesAdded : function(primManager) {
        this.selectControl.setLayer(primManager.allLayers);
    },
    
    _handleDrawCtrlAddFeature : function(e) {
        var ctrl = e.object;
        var feature = e.feature;

        //Remove box after it's added (delayed by 3 seconds so the user can see it)
        var task = new Ext.util.DelayedTask(Ext.bind(function(feature){
            this._drawCtrlVectorLayer.removeFeatures([feature]);
        }, this, [feature]));
        task.delay(3000);

        //raise the data selection event
        var originalBounds = feature.geometry.getBounds();
        var bounds = originalBounds.transform('EPSG:3857','EPSG:4326').toArray();
        var bbox = Ext.create('portal.util.BBox', {
            northBoundLatitude : bounds[3],
            southBoundLatitude : bounds[1],
            eastBoundLongitude : bounds[2],
            westBoundLongitude : bounds[0]
        });

        //Iterate all active layers looking for data sources (csw records) that intersect the selection
        var intersectedRecords = this.getLayersInBBox(bbox);
        this.fireEvent('dataSelect', this, bbox, intersectedRecords);

        //Because click events are still 'caught' even if the click control is deactive, the click event
        //still gets fired. To work around this, add a tiny delay to when we reactivate click events
        var task = new Ext.util.DelayedTask(Ext.bind(function(ctrl){
            ctrl.deactivate();
        }, this, [ctrl]));
        task.delay(50);
    },

    /**
     * Renders this map to the specified Ext.container.Container.
     *
     * Also sets the rendered property.
     *
     * function(container)
     *
     * @param container The container to receive the map
     */
    renderToContainer : function(container,divId) {
        //VT: manually set the id.
        var containerId = divId;
        var me = this;

        this.map = new OpenLayers.Map({
            div: containerId,
            projection: 'EPSG:3857',
            controls : [
                new OpenLayers.Control.Navigation(),
                new OpenLayers.Control.PanZoomBar({zoomStopHeight:8}),
                new OpenLayers.Control.MousePosition({
                    "numDigits": 2,
                    displayProjection: new OpenLayers.Projection("EPSG:4326"),
                    prefix: '<a target="_blank" href="http://spatialreference.org/ref/epsg/4326/">Map coordinates (WGS84 decimal degrees)</a>: ' ,
                    suffix : ' / lat lng',
                    emptyString : '<a target="_blank" href="http://spatialreference.org/ref/epsg/4326/">Map coordinates (WGS84 decimal degrees): </a> Out of bound',
                    element : Ext.get('latlng').dom,
                    formatOutput: function(lonLat) {
                        var digits = parseInt(this.numDigits);
                        var newHtml =
                            this.prefix +
                            lonLat.lat.toFixed(digits) +
                            this.separator +
                            lonLat.lon.toFixed(digits) +
                            this.suffix;
                        return newHtml;
                     }
                })
            ],
            layers: [
                     new OpenLayers.Layer.WMS (
                         "World Political Boundaries",
                         "http://services.ga.gov.au/site_1/services/World_Political_Boundaries_WM/MapServer/WMSServer",
                         {layers: 'Countries'}
                     ),
                     new OpenLayers.Layer.Google(
                             "Google Hybrid",
                             {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
                     ),
                     new OpenLayers.Layer.Google(
                         "Google Physical",
                         {type: google.maps.MapTypeId.TERRAIN}
                     ),
                     new OpenLayers.Layer.Google(
                         "Google Streets", // the default
                         {numZoomLevels: 20}
                     ),
                     new OpenLayers.Layer.Google(
                    		 // Name of the layer that will be set as default. If removed or changed, also
                    		 // change code below which sets as default
                             "Google Satellite",
                             {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
                     )
                 ],
                 center: new OpenLayers.LonLat(133.3, -26)
                     // Google.v3 uses web mercator as projection, so we have to
                     // transform our coordinates
                     .transform('EPSG:4326', 'EPSG:3857'),
                 zoom: 4
        });

        var selLayer = this.map.getLayersByName("Google Satellite");
        if (selLayer !== null) {        
            this.map.setBaseLayer(selLayer[0]);
        }
        
        // Creation and rendering of LayerSwitcher moved to renderBaseMap()

        this.highlightPrimitiveManager = this.makePrimitiveManager(true);
        this.container = container;
        this.rendered = true;
        
        //VT: adds a customZoomBox which fires a afterZoom event.
        var zoomBoxCtrl = new OpenLayers.Control.ZoomBox({alwaysZoom:true,zoomOnClick:false});
        var panCtrl = new OpenLayers.Control.Navigation();
        var customNavToolBar = OpenLayers.Class(OpenLayers.Control.NavToolbar, {
            initialize: function(options) {
                OpenLayers.Control.Panel.prototype.initialize.apply(this, [options]);
                this.addControls([panCtrl, zoomBoxCtrl])
            }
        });

        //VT: once we catch the afterZoom event, reset the control to panning.
        var customNavTb=new customNavToolBar();
        customNavTb.controls[1].events.on({
            "afterZoom": function() {
                me.fireEvent('afterZoom', this);                             

               // reset the control to panning.
               customNavTb.defaultControl=customNavTb.controls[0];
               customNavTb.activateControl(customNavTb.controls[0]);
               me._activateClickControl();
            }
        });

        this.map.addControl(customNavTb);

        //If we are allowing data selection, add an extra control to the map
        if (this.allowDataSelection) {
            //This panel will hold our Draw Control. It will also custom style it
            var panel = new OpenLayers.Control.Panel({
                createControlMarkup: function(control) {
                    var button = document.createElement('button'),
                        iconSpan = document.createElement('span'),
                        activeTextSpan = document.createElement('span');
                        inactiveTextSpan = document.createElement('span');

                    iconSpan.innerHTML = '&nbsp;';
                    button.appendChild(iconSpan);

                    activeTextSpan.innerHTML = control.activeText;
                    Ext.get(activeTextSpan).addCls('active-text');
                    button.appendChild(activeTextSpan);

                    inactiveTextSpan.innerHTML = control.inactiveText;
                    Ext.get(inactiveTextSpan).addCls('inactive-text');
                    button.appendChild(inactiveTextSpan);

                    button.setAttribute('id', control.buttonId);

                    return button;
                }
            });
            
            this._drawCtrlVectorLayer = this._getNewVectorLayer();
                       
            var drawFeatureCtrl = new OpenLayers.Control.DrawFeature(this._drawCtrlVectorLayer, OpenLayers.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 4,
                    irregular: true
                },
                title:'Draw a bounding box to select data in a region.',
                activeText: 'Click and drag a region of interest',
                buttonId : 'gmap-subset-control',
                inactiveText : 'Select Data'
            });
            panel.addControls([drawFeatureCtrl]);

            //We need to ensure the click controller and other controls aren't active at the same time
            drawFeatureCtrl.events.register('activate', {}, function() {
                me._deactivateClickControl();
                Ext.each(customNavTb.controls, function(ctrl) {
                   ctrl.deactivate();
                });
            });
            drawFeatureCtrl.events.register('deactivate', {}, function() {
                me._activateClickControl();
            });
            Ext.each(customNavTb.controls, function(ctrl) {
                ctrl.events.register('activate', {}, function() {
                    drawFeatureCtrl.deactivate();
                });
            });

            //We need to listen for when a feature is drawn and act accordingly
            drawFeatureCtrl.events.register('featureadded', {}, Ext.bind(function(e) {this._handleDrawCtrlAddFeature(e);}, this));


            this.map.addControl(panel);
        }

        //Finally listen for resize events on the parent container so we can pass the details
        //on to Openlayers.
        container.on('resize', function() {
            this.map.updateSize();
        }, this);
        
        //Finally listen for boxready events on the parent container so we can pass the details
        //on to Openlayers.
        container.on('boxready', function() {
            this.map.updateSize();
        }, this);        
    },

    // Draw the OpenLayers layers (eg. "Google Street View"/"Google Satellite") Controls (GPT-40 Active Layers)
    renderBaseMap : function(divId) {
        var me = this;
//        console.log("renderBaseMap - LAYERS")
        // Setup if map is defined else let map know to send this an event once created
        if (this.map) {
//            console.log("renderBaseMap - LAYERS - map NOT null: " + this.map + " - create LayerSwitcher (div: "+divId+")");
            this._drawOpenLayerSwitcher(divId);
        } else {
//            console.log("renderBaseMap - LAYERS - map IS undefined - setup callback (div: "+divId+")");
            // This object has a listener for 'mapcreated' events that are fired
            portal.events.AppEvents.addListener(me, {callback:this.renderBaseMap, divId:divId});
        }
    },
    
    listeners : {
        mapcreated : function (args) {
            // Expect - arg1: functionToCall, arg2: divId
            var theFunction = args.callback;
            var theId = args.divId;
            var me = this;
            
//           console.log("OpenLayersMap - listener - mapCreated - theId: " +theId+", function: ", theFunction);
           theFunction.apply(me, [theId]);
        }
    },
    
    _drawOpenLayerSwitcher : function(divId) {
        if (! this.layerSwitcher) {
            if (divId) {
                this.layerSwitcher = new OpenLayers.Control.LayerSwitcher({
                    'div': OpenLayers.Util.getElement(divId),
                    'ascending':false
                });
            } else {
                // No div so it will appear on the map if the portal code doesn't call this
                this.layerSwitcher = new OpenLayers.Control.LayerSwitcher({
                    'ascending':false
                });
            }
            
            this.map.addControl(this.layerSwitcher);
            this.layerSwitcher.maximizeControl();
        }
    },
    
    _getNewVectorLayer : function(){
        var vectorLayer = new OpenLayers.Layer.Vector("Vectors", {
            preFeatureInsert: function(feature) {
                // Google.v3 uses web mercator as projection, so we have to
                // transform our coordinates

                var bounds = feature.geometry.getBounds();

                //JJV - Here be dragons... this is a horrible, horrible workaround. I am so very sorry :(
                //Because we want to let portal core *think* its in EPSG:4326 and because our base map is in EPSG:3857
                //we automagically transform the geometry on the fly. That isn't a problem until you come across
                //various openlayers controls that add to the map in the native projection (EPSG:3857). To workaround this
                //we simply don't transform geometry that's already EPSG:3857. The scary part is how we go about testing for that...
                //The below should work except for tiny bounding boxes off the west coast of Africa
                if (bounds.top <= 90 && bounds.top >= -90) {
                    feature.geometry.transform('EPSG:4326','EPSG:3857');
                }
            },
            displayInLayerSwitcher : false
        });
        this.map.addLayer(vectorLayer);
        return vectorLayer;
    },
    /**
     * Returns the currently visible map bounds as a portal.util.BBox object.
     *
     * function()
     */
    getVisibleMapBounds : function() {
        var bounds = this.map.getExtent().transform('EPSG:3857','EPSG:4326').toArray();
        
        if(bounds[2]>180){
            var exceedLong=bounds[2] - 180;
            bounds[2] = (180 - exceedLong)*-1;
        }

        // Nasty Maths - work out the precision....
        eastWestDelta = Math.abs(Math.max(bounds[0], bounds[2]) - Math.min(bounds[0], bounds[2]));
        northSouthDelta = Math.abs(Math.max(bounds[1], bounds[3]) - Math.min(bounds[1], bounds[3]));

        // y = np.floor(1/np.power(x,0.15))
        /*
         * 0.0001  :  4.0
         * 0.001  :  3.0
         * 0.01  :  2.0
         * 0.1  :  1.0
         * 1.0  :  1.0
         * 10.0  :  0.0
         * 100.0  :  0.0
         */


        calcEWprecis = Math.floor(1/Math.pow(eastWestDelta,0.16));
        calcNSprecis = Math.floor(1/Math.pow(northSouthDelta,0.16));

        if (calcEWprecis < 0) {
            calcEWprecis = 0;
        }
        if (calcNSprecis < 0) {
            calcNSprecis = 0;
        }

        north = bounds[3].toFixed(calcNSprecis);
        south = bounds[1].toFixed(calcNSprecis);
        east = bounds[2].toFixed(calcEWprecis);
        west = bounds[0].toFixed(calcEWprecis);

        return Ext.create('portal.util.BBox', {
            westBoundLongitude : west,
            southBoundLatitude : south,
            eastBoundLongitude : east,
            northBoundLatitude : north
        });
    },

    /**
     * Creates a new empty instance of the portal.map.PrimitiveManager class for use
     * with this map
     *
     * function()
     */
    makePrimitiveManager : function(noLazyGeneration) {
       
        var clickableLayers = this.vectorLayers
        var clickControl = new portal.map.openlayers.ClickControl(clickableLayers, {
            map : this.map,
            trigger : Ext.bind(this._onClick, this)
        });
                
        var controlList = this.map.getControlsByClass('portal.map.openlayers.ClickControl');
                
        //VT: update the map clickControl with the updated layer
        for(var i = 0; i < controlList.length; i++){
            this.map.removeControl(controlList[i]);                        
        }
        
        this.map.addControl(clickControl);
        clickControl.activate();
                                     
        return Ext.create('portal.map.openlayers.PrimitiveManager', {
            baseMap : this,
            vectorLayerGenerator: Ext.bind(function() {
                var newVectorLayer = this._getNewVectorLayer();
                this.vectorLayers.push(newVectorLayer);
                return newVectorLayer;
            }, this),
            noLazyGeneration: noLazyGeneration,
            listeners: {
                //See ANVGL-106 for why we need to forcibly reorder thse
                addprimitives : Ext.bind(function() {
                    //Move highlight layer to top
                    
                    var highlightLayer = this.highlightPrimitiveManager.vectorLayer;
                    if (highlightLayer) {
                        this.map.setLayerIndex(highlightLayer, this.map.layers.length);
                    }
                    //Move drawing layer to top
                    var ctrls = this.map.getControlsByClass('OpenLayers.Control.DrawFeature');
                    if (!Ext.isEmpty(ctrls)) {
                        this.map.setLayerIndex(ctrls[0].layer, this.map.layers.length);
                    }
                }, this)
            }
        });
    },

    /**
     * Opens an info window at a location with the specified content. When the window loads initFunction will be called
     *
     * function(windowLocation, width, height, content, initFunction)
     *
     * width - Number - width of the info window in pixels
     * height - Number - height of the info window in pixels
     * windowLocation - portal.map.Point - where the window will be opened from
     * content - Mixed - A HTML string representing the content of the window OR a Ext.container.Container object OR an Array of the previous types
     * initFunction - [Optional] function(portal.map.BaseMap map, Mixed content) a function that will be called when the info window actually opens
     */
    openInfoWindow : function(windowLocation, width, height, content,layer) {
        //Firstly create a popup with a chunk of placeholder HTML - we will render an ExtJS container inside that
        var popupId = Ext.id();
        var location = new OpenLayers.LonLat(windowLocation.getLongitude(), windowLocation.getLatitude());
        location = location.transform('EPSG:4326','EPSG:3857');
        var verticalPadding = content.length <= 1 ? 0 : 32; //If we are opening a padded popup, we need to pad for the header
        var horizontalPadding = 0;
        var paddedSize = new OpenLayers.Size(width + horizontalPadding, height + verticalPadding);
        var divId = Ext.id();
        var divHtml = Ext.util.Format.format('<html><body><div id="{0}" style="width: {1}px; height: {2}px;"></div></body></html>', divId, paddedSize.w, paddedSize.h);
        var popup = new OpenLayers.Popup.FramedCloud(popupId, location, paddedSize, divHtml, null, true, null);

        this.map.addPopup(popup, true);
        
        //Workaround
        //ExtJS needs events to bubble up to the window for them to work (it's where the event handlers live)
        //Unfortunately OpenLayers is too aggressive in consuming events occuring in a popup, so the events never make it.
        //So - to workaround this we capture relevant events in our parent div (sitting before the open layer popup handlers) 
        //and manually redirect them to the ExtJS handlers
        var node = Ext.get(divId).dom;
        var handler = function(e) {
            Ext.event.publisher.Dom.instance.onDelegatedEvent(e); //this is a private ExtJS function - it's likely to break on upgrade
            if (e.type !== 'click') {
                Ext.event.publisher.Gesture.instance.onDelegatedEvent(e);
            }
            return false;
        };
        node.addEventListener('mousedown', handler);
        node.addEventListener('mouseup', handler);
        node.addEventListener('mousemove', handler);
        node.addEventListener('click', handler);             

        //End workaround
        
        
        this.openedInfoLayerId=layer.get('id');
        //next create an Ext.Container to house our content, render it to the HTML created above
        if (!Ext.isArray(content)) {
            content = [content];
        }

        //We need a parent control to house the components, a regular panel works fine for one component
        //A tab panel will be required for many components
        if (content.length === 1) {
            Ext.create('Ext.panel.Panel', {
                width : paddedSize.w,
                height : paddedSize.h,
                autoScroll : true,
                layout: 'fit',
                renderTo : divId,
                border : false,
                items : content
            });
          //VT:Tracking
            portal.util.PiwikAnalytic.trackevent('Query','layer:'+layer.get('name'),'id:' + content[0].tabTitle);
            
        } else {
            var tabPanelItems = [];
            for (var i = 0; i < content.length; i++) {
                if (Ext.isString(content[i])) {
                    tabPanelItems.push({
                        title : '',
                        border : false,
                        layout: 'fit',
                        autoScroll : true,
                        html : content[i]
                    });
                    portal.util.PiwikAnalytic.trackevent('Query','layer:'+layer.get('name'),'id:Unknown');
                } else {
                    tabPanelItems.push({
                        title : content[i].tabTitle,
                        border : false,
                        layout: 'fit',
                        autoScroll : true,
                        items : [content[i]]
                    });
                    portal.util.PiwikAnalytic.trackevent('Query','layer:'+layer.get('name'),'id:' + content[i].tabTitle);
                }
            }

            Ext.create('Ext.tab.Panel', {
                width : paddedSize.w,
                height : paddedSize.h,
                renderTo : divId, 
                layout: 'fit',
                //plain : true,
                border : false,
                activeTab: 0,
                items : tabPanelItems
            });
        }
    },

    /**
     * Causes the map to scroll/zoom so that the specified bounding box is visible
     *
     * function(bbox)
     *
     * @param bbox an instance of portal.util.BBox
     */
    scrollToBounds : function(bbox) {
        var bounds = new OpenLayers.Bounds(bbox.westBoundLongitude, bbox.southBoundLatitude, bbox.eastBoundLongitude, bbox.northBoundLatitude);
        bounds.transform('EPSG:4326','EPSG:3857');
        this.map.zoomToExtent(bounds,true);
    },

    /**
     * Gets the numerical zoom level of the current map as a Number
     *
     * function()
     */
    getZoom : function() {
        return this.map.getZoom();
    },

    /**
     * Sets the numerical zoom level of the current map
     *
     * function(zoom)
     *
     * @param zoom Number based zoom level
     */
    setZoom : function(zoom) {
        this.map.zoomTo(zoom);
    },

    /**
     * Pans the map until the specified point is in the center
     *
     * function(point)
     *
     * @param point portal.map.Point to be centered on
     * @param crs - the crs of the point
     */
    setCenter : function(point,crs) {
        if(crs && crs=='EPSG:3857'){
            this.map.panTo(new OpenLayers.LonLat(point.getLongitude(), point.getLatitude()))
        }else{
            this.map.panTo((new OpenLayers.LonLat(point.getLongitude(), point.getLatitude()))
                .transform('EPSG:4326','EPSG:3857'));
        }
    },

    /**
     * Gets the location of the center point on the map as a portal.map.Point
     *
     * function()
     */
    getCenter : function() {
        var center = this.map.getCenter();
        center = center.transform('EPSG:3857','EPSG:4326');
        return Ext.create('portal.map.Point', {
            longitude : center.lon,
            latitude : center.lat
        });
    },

    /**
     * Gets a portal.map.TileInformation describing a specified spatial point
     *
     * function(point)
     *
     * @param point portal.map.Point to get tile information
     */
    getTileInformationForPoint : function(point) {
        var layer = this.map.baseLayer;
        var tileSize = this.map.getTileSize();
        //Get the bounds of the tile that encases point
        var lonLat = new OpenLayers.LonLat(point.getLongitude(), point.getLatitude());
            lonLat = lonLat.transform('EPSG:4326','EPSG:3857');
        var viewPortPixel = this.map.getViewPortPxFromLonLat(lonLat);

        var tileBounds = this.map.getExtent();//.transform('EPSG:3857','EPSG:4326');

        return Ext.create('portal.map.TileInformation', {
            width : this.map.size.w,
            height : this.map.size.h,
            offset : {  //Object - The point location within the tile being queried
                x : viewPortPixel.x ,
                y : viewPortPixel.y
            },
            tileBounds : Ext.create('portal.util.BBox', {
                eastBoundLongitude : tileBounds.right,
                westBoundLongitude : tileBounds.left,
                northBoundLatitude : tileBounds.top,
                southBoundLatitude : tileBounds.bottom
            })
        });
    },

    /**
     * Returns an portal.map.Size object representing the map size in pixels in the form
     *
     * function()
     */
    getMapSizeInPixels : function() {
        var size = this.map.getCurrentSize();
        return Ext.create('portal.map.Size', {
            width : size.w,
            height : size.h
        });
    },

    /**
     * See parent class for information
     */
    getPixelFromLatLng : function(point) {
        var lonlat=new OpenLayers.LonLat(point.getLongitude(), point.getLatitude());
        lonlat = lonlat.transform('EPSG:4326','EPSG:3857');
        var layerPixel = this.map.getLayerPxFromLonLat(lonlat);
        var viewportPixel = this.map.getViewPortPxFromLayerPx(layerPixel);

        return {
            x : viewportPixel.x,
            y : viewportPixel.y
        }
    },

    ////////////////// Base functionality

    /**
     * Opens a context menu on the map at the specified coordinates
     *
     * @param point portal.map.Point location to open menu
     * @param menu Ext.menu.Menu that will be shown
     */
    showContextMenuAtLatLng : function(point, menu) {
        var pixel = this.getPixelFromLatLng(point);
        menu.showAt(this.container.x + pixel.x, this.container.y + pixel.y);
    },
    
    /**
     * Add KML from String to the map layer.
     *
     * @param KMLString KML String
     */
    addKMLFromString : function(id,title, KMLString){
        var feature = this.getFeaturesFromKMLString(KMLString) 
        var vectorLayer = new OpenLayers.Layer.Vector(title,{
            projection: "EPSG:4326"
        });                               
        vectorLayer.addFeatures(feature);
        this.map.addLayer(vectorLayer);  
        this.map.zoomToExtent(vectorLayer.getDataExtent());
        return vectorLayer;

    },
    
    /**
     * Remove KML layer from the map
     *
     * @param the id of the KML layer to remove
     */
    removeKMLLayer : function(vectorlayer){           
        this.map.removeLayer(vectorlayer);
    },

    /**
     * Figure out whether we should automatically render this layer or not
     */
    _onLayerStoreAdd : function(store, layers) {
        for (var i = 0; i < layers.length; i++) {            
            var newLayer = layers[i];
            //Some layer types should be rendered immediately, others will require the 'Apply Filter' button
            //We trigger the rendering by forcing a write to the filterer object
            if (newLayer.get('deserialized')) {
                //Deserialized layers (read from permalink) will have their
                //filterer already fully configured.
                var filterer = newLayer.get('filterer');
                filterer.setParameters({}); //Trigger an update without chang
            } else if (newLayer.get('renderOnAdd')) {
                //Otherwise we will need to append the filterer with the current visible bounds
                var filterForm = newLayer.get('filterForm');
                var filterer = newLayer.get('filterer');

                //Update the filter with the current map bounds
                filterer.setSpatialParam(this.getVisibleMapBounds(), true);

                filterForm.writeToFilterer(filterer);
            } 

        }
    },
   
    _getDomain : function(data) {
        return portal.util.URL.extractHostNSubDir(data,1);
    },

    _activateClickControl : function() {
        var controlList = this.map.getControlsByClass('portal.map.openlayers.ClickControl');
        for(var i = 0; i < controlList.length; i++){
            controlList[i].activate();
        }
    },

    _deactivateClickControl : function() {
        var controlList = this.map.getControlsByClass('portal.map.openlayers.ClickControl');
        for(var i = 0; i < controlList.length; i++){
            controlList[i].deactivate();
        }
    }
});

/**
 * Class for transforming a W3C DOM Document into a GenericParserComponent
 * by utilising a number of 'plugin' factories.
 */
Ext.define('portal.layer.querier.wfs.Parser', {
    extend: 'Ext.util.Observable',

    /**
     * Builds a new Parser from a list of factories. Factories in factoryList will be tested before
     * the items in factoryNames
     *
     * {
     *  factoryNames : String[] - an array of class names which will be instantiated as portal.layer.querier.wfs.factories.BaseFactory objects
     *  factoryList : portal.layer.querier.wfs.factories.BaseFactory[] - an array of already instantiated factory objects
     * }
     */
    constructor : function(config) {
        //The following ordering is important as it dictates the order in which to try
        //factories for parsing a particular node
        this.factoryList = Ext.isArray(config.factoryList) ? config.factoryList : [];
        if (Ext.isArray(config.factoryNames)) {
            var cfg = {
                parser : this
            };

            for (var i = 0; i < config.factoryNames.length; i++) {
                this.factoryList.push(Ext.create(config.factoryNames[i], cfg));
            }
        }
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Iterates through all internal factories until domNode can be parsed into GenericParser.BaseComponent
     * @param domNode The W3C DOM node to parse
     * @param wfsUrl The original WFS URL where domNode was sourced from
     * @param rootCfg [Optional] An Object whose properties will be applied to the top level component parsed (a GenericParser.BaseComponent instance)
     */
    parseNode : function(domNode, wfsUrl, applicationProfile) {
        //In the event of an empty node, return an empty component
        if (!domNode) {
            return Ext.create('portal.layer.querier.BaseComponent', {});
        }

        for (var i = 0; i < this.factoryList.length; i++) {
            if (this.factoryList[i].supportsNode(domNode)) {
                return this.factoryList[i].parseNode(domNode, wfsUrl, applicationProfile);
            }
        }

        throw 'Unsupported node type';
    }
});/**
 * A basic window to show a permanent link to the current map state.
 *
 * It consists of a simple warning and the link itself
 *
 */
Ext.define('portal.widgets.window.PermanentLinkWindow', {
    extend : 'Ext.window.Window',

    /**
     * Extends Ext.window.Window and adds {
     *  state : String - state string to be encoded into the permalink
     * }
     */
    constructor : function(cfg) {
        var mapStateSerializer = cfg.mapStateSerializer;

        //Rewrite our current URL with the new state info (leave the other URL params intact)
        var urlParams = Ext.Object.fromQueryString(location.search.substring(1));
        urlParams.s = cfg.state;
        if (cfg.version) {
            urlParams.v = cfg.version;
        }
        var linkedUrl = location.href.split('?')[0];

        var params = Ext.Object.toQueryString(urlParams);

        //*HACK:* sssssshhhh dont tell anyone we don't care about escaping....
        linkedUrl = Ext.urlAppend(linkedUrl, decodeURIComponent(params));

        var htmlDescription = '<b>Warning:</b><br>' +
                              'This link will only save your selected layers and queries. The actual data received and displayed may be subject to change<br><br>';

        //If the URL gets too long it may not work with some common browsers or web servers
        // - http://stackoverflow.com/a/417184/941763
        if (linkedUrl.length > 8192) {
            htmlDescription += '<p><b>Note: </b>This permanent link is very long and will be unuseable with the Internet Explorer web browser. It may also cause problems for various web servers so it is recommended you test your permanent link before saving/sharing it.</p>';
        } else if (linkedUrl.length > 2047) {
            htmlDescription += '<p><b>Note: </b>This permanent link is rather long and will be unuseable with the Internet Explorer web browser.</p>';
        }

        Ext.apply(cfg, {
            title: 'Permanent Link',
            autoDestroy : true,
            modal : true,
            width : 500,
            autoHeight : true,
            items : [{
                xtype : 'panel',
                style : {
                    font : '12px tahoma,arial,helvetica,sans-serif'
                },
                html : htmlDescription
            }, {
                xtype : 'form',
                layout : 'form',
                autoHeight : true,
                items : [{
                    xtype : 'textfield',
                    id: 'linkField',
                    anchor : '100%',
                    fieldLabel : 'Paste this link',
                    labelStyle: 'font-weight:bold;',
                    value : linkedUrl,
                    readOnly : true,
                    listeners : {
                        afterrender: function(field) {
                            Ext.defer(function() {
                                field.focus(true, 100);
                            }, 1);
                         }
                    }
                }]
            }],
            // in the ancient tradition of implementing odd ideas as requested by users...
            // add a button that copies the text field content to the clipboard. Yuck.
            buttons: [{
                xtype: 'button',
                text: 'Copy to clipboard',
                handler: function(button) {
                    if (window.clipboardData && window.clipboardData.setData) {
                        return clipboardData.setData("Text", Ext.getCmp('linkField').value); 
                    } else {
                        document.getElementById('linkField-inputEl').focus();
                        document.getElementById('linkField-inputEl').select();
                        document.execCommand('copy');
                    }
                }
            }]
        });

        this.callParent(arguments);
    }
});/**
 * Utility functions for downloading files
 */
Ext.define('portal.util.PiwikAnalytic', {
    singleton: true
}, function() {


    portal.util.PiwikAnalytic.trackevent = function(catagory,action,label,value) {
        if(typeof _paq != 'undefined' ){
            if(value){
                _paq.push(['trackEvent', catagory, action, label,value]);
            } else {
                _paq.push(['trackEvent', catagory, action, label]);
            }
        }

    };


    portal.util.PiwikAnalytic.siteSearch = function(keyword,category,searchCount) {
        if(typeof _paq != 'undefined' ){                     
            _paq.push(['trackSiteSearch',keyword,category,searchCount]);
        }

    };
});/**
 * Represents a single point in WGS:84 space
 */
Ext.define('portal.map.Point', {

    config : {
        /**
         * String - URL of the icon image
         */
        srs : 'WGS:84',
        /**
         * Number - the latitude of this point (WGS:84)
         */
        latitude : 0,
        /**
         * Number - the longitude of this point (WGS:84)
         */
        longitude : 0
    },

    /**
     * Accepts the following
     *
     * latitude : Number - the latitude of this point (WGS:84)
     * longitude : Number - the longitude of this point (WGS:84)
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        this.setSrs(cfg.srs);
        this.setLatitude(cfg.latitude);
        this.setLongitude(cfg.longitude);
    }
});/**
 * Represents a simple closed polygon as implemented by the Gmap API
 */
Ext.define('portal.map.openlayers.primitives.Polygon', {

    extend : 'portal.map.primitives.Polygon',

    config : {
        /**
         * Instance of a OpenLayers.Feature.Vector
         */
        vector : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     * fillColor : String - HTML Style color string '#RRGGBB' for the fill of the polygon
     * fillOpacity : Number - the opacity of the fill in the range [0, 1]
     */
    constructor : function(cfg) {
        this.callParent(arguments);


        //Construct our geometry
        var olPoints = [];
        for (var i = 0; i < cfg.points.length; i++) {
            olPoints.push(new OpenLayers.Geometry.Point(cfg.points[i].getLongitude(), cfg.points[i].getLatitude()));
        }
        var olRing = new OpenLayers.Geometry.LinearRing(olPoints);
        var olPolygon = new OpenLayers.Geometry.Polygon(olRing);

        //Construct our feature
        var vector = new OpenLayers.Feature.Vector(olPolygon, {
            portalBasePrimitive : this
        }, {
            fill : true,
            fillColor : this.getFillColor(),
            fillOpacity : this.getFillOpacity(),
            stroke : true,
            strokeColor : this.getStrokeColor(),
            strokeOpacity : this.getStrokeOpacity(),
            strokeWidth : this.getStrokeWeight()
        });


        this.setVector(vector);
    }
});/**
 * Represents a simple closed polygon as implemented by the Gmap API
 */
Ext.define('portal.map.gmap.primitives.Polygon', {

    extend : 'portal.map.primitives.Polygon',

    config : {
        /**
         * Instance of a GPolygon
         */
        polygon : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     * fillColor : String - HTML Style color string '#RRGGBB' for the fill of the polygon
     * fillOpacity : Number - the opacity of the fill in the range [0, 1]
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        var latLngs = [];
        for (var i = 0; i < cfg.points.length; i++) {
            latLngs.push(new GLatLng(cfg.points[i].getLatitude(), cfg.points[i].getLongitude()));
        }

        var polygon = new GPolygon(latLngs, this.getStrokeColor(), this.getStrokeWeight(), this.getStrokeOpacity(), this.getFillColor(), this.getFillOpacity());
        polygon._portalBasePrimitive = this;

        this.setPolygon(polygon);
    }
});/**
 * Represents a simple closed polygon
 */
Ext.define('portal.map.primitives.Polygon', {

    extend : 'portal.map.primitives.BasePrimitive',

    config : {
        /**
         * portal.map.Point[] - the bounds of this polygon
         */
        points : null,
        /**
         * String - HTML Style color string '#RRGGBB' for the vertices of the polygon
         */
        strokeColor : '#00FF00',
        /**
         * Number - Width of the stroke in pixels
         */
        strokeWeight : 1,
        /**
         * Number - the opacity of the vertices in the range [0, 1]
         */
        strokeOpacity : 0.7,
        /**
         * String - HTML Style color string '#RRGGBB' for the fill of the polygon
         */
        fillColor  : '#00FF00',
        /**
         * Number - the opacity of the fill in the range [0, 1]
         */
        fillOpacity : 0.6
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     * fillColor : String - HTML Style color string '#RRGGBB' for the fill of the polygon
     * fillOpacity : Number - the opacity of the fill in the range [0, 1]
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        this.setPoints(cfg.points);
        this.setStrokeColor(cfg.strokeColor);
        this.setStrokeWeight(cfg.strokeWeight);
        this.setStrokeOpacity(cfg.strokeOpacity);
        this.setFillColor(cfg.fillColor);
        this.setFillOpacity(cfg.fillOpacity);
    }
});/**
 * Represents a simple Polyline (series of straight line segments) as implemented by the OpenLayers API
 */
Ext.define('portal.map.openlayers.primitives.Polyline', {

    extend : 'portal.map.primitives.Polyline',

    config : {
        /**
         * Instance of a OpenLayers.Feature.Vector
         */
        vector : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     */
    constructor : function(cfg) {

        this.callParent(arguments);

        var olPoints = [];
        for (var i = 0; i < cfg.points.length; i++) {
            olPoints.push(new OpenLayers.Geometry.Point(cfg.points[i].getLongitude(), cfg.points[i].getLatitude()));
        }

        var olLineString = new OpenLayers.Geometry.LineString(olPoints);

        //Construct our feature
        var vector = new OpenLayers.Feature.Vector(olLineString, undefined, {
            stroke : true,
            strokeColor : this.getStrokeColor(),
            strokeOpacity : this.getStrokeOpacity(),
            strokeWidth : this.getStrokeWeight()
        });

        this.setVector(vector);
    }
});/**
 * Represents a simple Polyline (series of straight line segments) as implemented by the Gmap API
 */
Ext.define('portal.map.gmap.primitives.Polyline', {

    extend : 'portal.map.primitives.Polyline',

    config : {
        /**
         * GPolyline instance
         */
        polyline : null
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     */
    constructor : function(cfg) {

        this.callParent(arguments);

        var latLngs = [];
        for (var i = 0; i < cfg.points.length; i++) {
            latLngs.push(new GLatLng(cfg.points[i].getLatitude(), cfg.points[i].getLongitude()));
        }

        var line = new GPolyline(latLngs, this.getStrokeColor(), this.getStrokeWeight(), this.getStrokeOpacity());
        line._portalBasePrimitive = this;

        this.setPolyline(line);
    }
});/**
 * Represents a simple Polyline (series of straight line segments)
 */
Ext.define('portal.map.primitives.Polyline', {

    extend : 'portal.map.primitives.BasePrimitive',

    config : {
        /**
         * portal.map.Point[] - the bounds of this polyline
         */
        points : null,
        /**
         * String - HTML Style color string '#RRGGBB' for the vertices of the polygon
         */
        strokeColor : '#00FF00',
        /**
         * Number - Width of the stroke in pixels
         */
        strokeWeight : 1,
        /**
         * Number - the opacity of the vertices in the range [0, 1]
         */
        strokeOpacity : 0.7
    },

    /**
     * Accepts the following in addition to portal.map.primitives.BasePrimitive's constructor options
     *
     * points : portal.map.Point[] - the bounds of this polygon
     * strokeColor : String - HTML Style color string '#RRGGBB' for the vertices of the polygon
     * strokeWeight : Number - Width of the stroke in pixels
     * strokeOpacity : Number - the opacity of the vertices in the range [0, 1]
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        this.setPoints(cfg.points);
        this.setStrokeColor(cfg.strokeColor);
        this.setStrokeWeight(cfg.strokeWeight);
        this.setStrokeOpacity(cfg.strokeOpacity);
    }
});/**
 * Concrete implementation for OpenLayers
 */
Ext.define('portal.map.openlayers.PrimitiveManager', {
    extend: 'portal.map.BasePrimitiveManager',

    vectorLayer : null,
    vectorLayerGenerator : null,
    layers : null,
    vectors : null,

    /**
     * {
     *  baseMap : portal.map.BaseMap - The map instance that created this primitive manager,
     *  vectorLayerGenerator : function(this) - Called once on demand, should return a OpenLayers.Layer.Vector where vectors will be added by this class
     *  noLazyGeneration: Boolean - If true, this will force the vectorLayerGenerator to be fired immediately
     * }
     */
    constructor : function(config) {
        this.callParent(arguments);

        this.vectorLayer = null;
        this.vectorLayerGenerator = config.vectorLayerGenerator;

        this.layers = [];
        this.vectors = [];
        
        if (config.noLazyGeneration) {
            this.getVectorLayer();
        }
    },

    getVectorLayer: function() {
        if (this.vectorLayer) {
            return this.vectorLayer;
        } else {
            return this.vectorLayer = this.vectorLayerGenerator(this);
        }
    },

    setVisibility : function(visibility){
        this.getVectorLayer().setVisibility(visibility);
        
        for (var i = 0; i < this.layers.length; i++) {
            this.layers[i].setVisibility(visibility);
        }
    },

    /**
     * See parent class for info
     */
    clearPrimitives : function() {
        for (var i = 0; i < this.layers.length; i++) {
            this.layers[i].destroy();
        }
        this.layers = [];

        this.getVectorLayer().removeFeatures(this.vectors);
        for (var i = 0; i < this.vectors.length; i++) {
            this.vectors[i].destroy();
        }
        this.vectors = [];

        this.fireEvent('clearprimitives', this);
    },

    /**
     * See parent class for info
     */
    addPrimitives : function(primitives) {
        var markers = [];
        var vectors = [];

        //sort our primitives into vectors and markers
        for (var i = 0; i < primitives.length; i++) {
            var prim = primitives[i];

            if (prim instanceof portal.map.openlayers.primitives.Marker ||
                    prim instanceof portal.map.openlayers.primitives.Polygon ||
                    prim instanceof portal.map.openlayers.primitives.Polyline) {
                vectors.push(prim.getVector())
            } else if (prim instanceof portal.map.openlayers.primitives.WMSOverlay) {
                //VT: add wms primitive in the store order if exist
                var layerId=prim.getLayer().data.id;
                var layerStore = this.baseMap.layerStore.data.items;
                var position = 0;
                //layerStore provides the ordering
                for(position=0;position < layerStore.length; position++){
                    if(layerId==layerStore[position].data.id){
                        break;
                    }
                }

                var layer = prim.getWmsLayer();
                this.layers.push(layer);
                this.baseMap.map.addLayer(layer);

                //VT: this will give us the order where we should be slotting into the map layer
                position = this.baseMap.map.layers.length - 1 - position;
                this.baseMap.map.setLayerIndex(layer,position);
            }
        }

        if (vectors.length > 0) {
            this.getVectorLayer().addFeatures(vectors);
            for (var i = 0; i < vectors.length; i++) {
                this.vectors.push(vectors[i]);
            }
        }

        this.fireEvent('addprimitives', this, primitives);
    }
});/**
 * Concrete implementation of portal.map.BasePrimitiveManager
 * which is specialised for managing google map primitives.
 */
Ext.define('portal.map.gmap.PrimitiveManager', {
    extend: 'portal.map.BasePrimitiveManager',

    constructor : function(config) {
        this.callParent(arguments);
        this.markerManager = new MarkerManager(this.baseMap.map);
        this.primitiveList = [];
    },

    /**
     * See parent for definition.
     */
    clearPrimitives : function() {
        for (var i = 0; i < this.primitiveList.length; i++) {
            this.baseMap.map.removeOverlay(this.primitiveList[i]);
        }
        this.primitiveList = [];
        this.markerManager.clearMarkers();

        this.fireEvent('clearprimitives', this);
    },

    /**
     * See parent for definition.
     */
    addPrimitives : function(primitives) {
        var markers = [];
        for (var i = 0; i < primitives.length; i++) {
            var prim = primitives[i];

            if (prim instanceof portal.map.gmap.primitives.Marker) {
                markers.push(prim.getMarker());
            } else if (prim instanceof portal.map.gmap.primitives.Polygon) {
                var overlay = prim.getPolygon();
                this.baseMap.map.addOverlay(overlay);
                this.primitiveList.push(overlay);
            } else if (prim instanceof portal.map.gmap.primitives.Polyline) {
                var overlay = prim.getPolyline();
                this.baseMap.map.addOverlay(overlay);
                this.primitiveList.push(overlay);
            } else if (prim instanceof portal.map.gmap.primitives.WMSOverlay) {
                var overlay = prim.getTileLayerOverlay();
                this.baseMap.map.addOverlay(overlay);
                this.primitiveList.push(overlay);
            }
        }

        if (markers.length > 0) {
            this.markerManager.addMarkers(markers, 0);
            this.markerManager.refresh();
        }

        this.fireEvent('addprimitives', this, primitives);
    }
});






/**
 * Utility class for dealing with provider names.
 * For example to generate a human-readable (and short) name based on the real name. 
 */
Ext.define('portal.util.ProviderNameTransformer', {    
    statics : {
        abbreviateName : function(providerName) {  
            
            var SERVICE_PROVIDERS = [
                'CSIRO',
                'Geoscience Australia',
                'Northern Territory',
                'Queensland',
                'South Australia',
                'Tasmania',
                'Victoria',
                'Western Australia'
            ];
            
            // perform transformations on provider name - edge cases first
            for (var i = 0; i < SERVICE_PROVIDERS.length; i++) {  
                if (providerName.indexOf('DSD') != -1) {
                    providerName = 'South Australia';  
                    break;
                } else if (providerName.indexOf('NSW') != -1) {
                    providerName = 'New South Wales';  
                    break;
                } else if (providerName.indexOf('Department of Mines and Energy') != -1) {
                    providerName = 'Northern Territory';  
                    break;
                } else if (providerName.indexOf(SERVICE_PROVIDERS[i]) != -1) {
                    providerName = SERVICE_PROVIDERS[i];
                    break;
                }               
            }
            
            return providerName;
        } 
    }
});/**
 * An abstract class for providing functionality that
 * looks for more information about a particular QueryTarget.
 *
 * Ie if the user selects a particular feature from a WFS for
 * more information, the querier will lookup that information
 * an return a portal.layer.querier.BaseComponent contain
 * said information
 *
 */
Ext.define('portal.layer.querier.Querier', {
    extend: 'Ext.util.Observable',

    map : null, //instance of portal.util.gmap.GMapWrapper for use by subclasses

    constructor: function(config){

        this.map = config.map;

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Utility function for generating a WMS GetFeatureInfo request that is proxied through the portal backend
     * for querying for information about the specific queryTarget.
     * VT: Now that it is possible for querier to handle both wfs with wms, this utility has been moved to the parent layer.
     *
     * The response will be returned in the specified infoFormat
     * @param queryTarget A portal.layer.querier.QueryTarget
     * @param infoFormat a String representing a MIME type
     */
    generateWmsProxyQuery : function(queryTarget, infoFormat) {
        
        var point = Ext.create('portal.map.Point', {latitude : queryTarget.get('lat'), longitude : queryTarget.get('lng')});
        var lonLat = new OpenLayers.LonLat(point.getLongitude(), point.getLatitude());
        lonLat = lonLat.transform('EPSG:4326','EPSG:3857');

        var tileInfo = this.map.getTileInformationForPoint(point);
        var layer = queryTarget.get('layer');
        var feature_count=0;//VT:0 is to default to server setting
        
        if(layer.get('sourceType')==portal.layer.Layer.KNOWN_LAYER){
            feature_count = layer.get('source').get('feature_count');
        }
        
        var wmsOnlineResource = queryTarget.get('onlineResource');

        var typeName = wmsOnlineResource.get('name');
        var serviceUrl = wmsOnlineResource.get('url');


        var bbox = tileInfo.getTileBounds();
        var bboxString = Ext.util.Format.format('{0},{1},{2},{3}',
                bbox.eastBoundLongitude,
                bbox.northBoundLatitude,
                bbox.westBoundLongitude,
                bbox.southBoundLatitude);
        
        var sldParams = this.generateSLDParams (queryTarget, infoFormat);
        
        var queryParams = Ext.Object.merge({
            serviceUrl : serviceUrl,
            lat : lonLat.lat,
            lng : lonLat.lon,
            QUERY_LAYERS : typeName,
            x : tileInfo.getOffset().x,
            y : tileInfo.getOffset().y,
            BBOX : bboxString,
            WIDTH : tileInfo.getWidth(),
            HEIGHT : tileInfo.getHeight(),
            INFO_FORMAT : infoFormat,
            version : wmsOnlineResource.get('version'),
            feature_count : feature_count
        }, sldParams)
        
        //Build our proxy URL
        var queryString = Ext.Object.toQueryString(queryParams);
        return Ext.urlAppend('wmsMarkerPopup.do', queryString);
    },
    
    /*
     * Determines the parameters to use when applying an SLD to a GetFeatureInfo query.
     * 
     */    
    generateSLDParams : function (queryTarget, infoFormat) {
        var postMethod = false;
        var sld_body = null;
        
        if(queryTarget.get('layer').get('filterer').getParameters().postMethod){
            postMethod = queryTarget.get('layer').get('filterer').getParameters().postMethod;
        }
        
        var applicationProfile = queryTarget.get('onlineResource').get('applicationProfile');
        
        if (applicationProfile && applicationProfile.indexOf("Esri:ArcGIS Server") > -1) {
             postMethod = false;
             infoFormat = infoFormat ? infoFormat : 'text/xml'
        }
        else if (queryTarget.get('layer').get('renderer').sld_body){
            sld_body=queryTarget.get('layer').get('renderer').sld_body;
            //VT: if post is undefined and we have a very long sld_body
            //VT: we are goign to take a best guess approach and use post instead of get
            if(sld_body.length > 1200){
                postMethod = true;
                console.log('You really should not be using this method if the query' +
                        'is going be long as it generates a GET spring request to and' +
                        ' there are limitation to the lenght of a URI in GET method');
            }
            infoFormat = infoFormat ? infoFormat : 'application/vnd.ogc.gml/3.1.1'
            
        }
        return {
            postMethod : postMethod,
            SLD_BODY : sld_body,
            INFO_FORMAT : infoFormat
        }

    },

    /**
     * An abstract function for querying for information
     * about a particular feature/location associated with a
     * data source.
     *
     * The result of the query will be returned via a callback mechanism
     * as a set of BaseComponent's ie. GUI widgets.
     *
     * function(portal.layer.querier.QueryTarget target,
     *          function(portal.layer.querier.Querier this, portal.layer.querier.BaseComponent[] baseComponents, portal.layer.querier.QueryTarget target) callback
     *
     * returns - void
     *
     * target - the instance that fired off the query
     * callback - will be called the specified parameters after the BaseComponent has been created. The baseComponents array may be null or empty
     */
    query : portal.util.UnimplementedFunction

});/**
 * A factory class for creating instances of portal.layer.querier.Querier
 */
Ext.define('portal.layer.querier.QuerierFactory', {

    map : null, //instance of portal.map.BaseMap

    constructor: function(config){
        this.map = config.map;
        this.callParent(arguments);
    },

    /**
     * An abstract function for building a portal.layer.querier.Querier
     * suitable for a given KnownLayer
     *
     * function(portal.knownlayer.KnownLayer knownLayer)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     */
    buildFromKnownLayer : portal.util.UnimplementedFunction,

    /**
     * An abstract function for building a portal.layer.querier.Querier
     * suitable for a given CSWRecord
     *
     * function(portal.csw.CswRecord cswRecord)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     */
    buildFromCswRecord : portal.util.UnimplementedFunction
});/**
 * an QueryTarget is a fundamental object representing a query for more information
 * about a particular location/feature with respect to a particular data source.
 *
 * A query target will typically be either a lat/long query location OR a specific
 * feature ID to get more information about
 */
Ext.define('portal.layer.querier.QueryTarget', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id', type: 'string'}, //A unique id of a feature specifically selected (can be empty)
        {name: 'lat', type: 'float'}, //The EPSG:4326 latitude of the query location (can be empty)
        {name: 'lng', type: 'float'}, //The EPSG:4326 longitude of the query location (can be empty)
        {name: 'onlineResource', type: 'auto'}, //A portal.csw.OnlineResource representing the data source to query
        {name: 'layer', type: 'auto'}, //A portal.layer.Layer representing the owner of the online resource
        {name: 'explicit', type: 'boolean'}, //Whether this target is referencing the source explicitly. i.e. This
                                            //feature/location has been explicitly selected by the user. Typically only
                                            //WMS layers are implicit (not explicit) because it's impossible to tell what
                                            //what WMS layer (if any) was clicked on any given click event. It is however
                                            //easy to tell if a particular marker or polygon has been clicked.
        {name: 'cswRecord', type: 'auto'} //Needed for wms description and possibly for reports.
    ]
});

/**
 * Utility class for handling a series of portal.layer.querier.QueryTarget objects
 * by opening selection menus (if appropriate) or by simply passing along the
 * QueryTarget objects to the appropriate querier instances.
 */
Ext.define('portal.layer.querier.QueryTargetHandler', {

    _infoWindowHeight : 350,
    _infoWindowWidth : 600,

    /**
     * Accepts a config with {
     *  infoWindowHeight : [Optional] Number height of info window in pixels
     *  infoWindowWidth : [Optional] Number width of info window in pixels
     * }
     * 
     * VT: this might be legacy code however it feels a bit pointless to have this overriding window size option at the constructor level
     * as it does not allow fine grain tunning of windows size. A more suitable place would be in the baseComponents that makes up the
     * infowindow. Hence in _queryCallback, I have added checks on baseComponent for object overrideInfoWindowSize and if found, use that window size 
     * instead.
     */
    constructor : function(config) {
        if (config.infoWindowHeight) {
            this._infoWindowHeight = config.infoWindowHeight;
        }
        if (config.infoWindowWidth) {
            this._infoWindowWidth = config.infoWindowWidth;
        }
        this.callParent(arguments);
    },

    /**
     * Re-entrant show function for a particular loadMask
     */
    _showLoadMask : function(loadMask) {
        if (!loadMask) {
            return;
        }
        if (!loadMask._showCount) {
            loadMask._showCount = 0;
        }

        if (loadMask._showCount++ === 0) {
            loadMask.show();
        }
    },

    /**
     * Re-entrant hide function for a particular loadMask
     */
    _hideLoadMask : function(loadMask) {
        if (!loadMask) {
            return;
        }
        if (--loadMask._showCount === 0) {
            loadMask.hide();
        }
    },

    /**
     * Handles a query response by opening up info windows
     */
    _queryCallback : function(querier, baseComponents, queryTarget, mapWrapper, loadMask) {
        this._hideLoadMask(loadMask); //ensure this gets called or we'll have a forever floating 'Loading...'
        if (!baseComponents || baseComponents.length === 0) {
            return; //if the query failed, don't show a popup
        }

        //Build our info window content (sans parent containers)        
        var width = this._infoWindowWidth;
        var height = this._infoWindowHeight;
        //VT: if any overrideInfoWindowSize is found under the baseComponents, use that instead.
        for(var i = 0; i < baseComponents.length; i++){
            if(baseComponents[i].overrideInfoWindowSize){
                width = baseComponents[i].overrideInfoWindowSize.width;
                height = baseComponents[i].overrideInfoWindowSize.height;
                break;
            }
        }

        //Show our info window - create our parent components
        var windowLocation = Ext.create('portal.map.Point', {
            latitude : queryTarget.get('lat'),
            longitude : queryTarget.get('lng')
        });
        //function(windowLocation, width, height, content, initFunction)
        mapWrapper.openInfoWindow(windowLocation, width, height, baseComponents, queryTarget.get('layer'));
    },

    /**
     * Just query everything in queryTargets
     */
    _handleWithQuery : function(queryTargets, mapWrapper) {
        var loadMask = new Ext.LoadMask({
            msg : 'Loading...',
            target : mapWrapper.container
        });
        for (var i = 0; i < queryTargets.length; i++) {
            var queryTarget = queryTargets[i];
            var layer = queryTarget.get('layer');
            var querier = layer.get('querier');

            this._showLoadMask(loadMask);
            querier.query(queryTarget, Ext.bind(this._queryCallback, this, [mapWrapper, loadMask], true));
        }
    },

    /**
     * Show some form of selection to the user, ask them to decide
     * which query target they meant
     */
    _handleWithSelection : function(queryTargets, mapWrapper) {
        //Build a list of menu item objects from our query targets
        var items = [];
        var point = null;
        for (var i = 0; i < queryTargets.length; i++) {
            var cswRecord = queryTargets[i].get('cswRecord');
            var onlineResource = queryTargets[i].get('onlineResource');
            if (!cswRecord) {
                continue;
            }

            point = Ext.create('portal.map.Point', {
                latitude : queryTargets[i].get('lat'),
                longitude : queryTargets[i].get('lng')
            });


            var shortTitle = cswRecord.get('name');
            
            var provider = portal.util.ProviderNameTransformer.abbreviateName(cswRecord.get('contactOrg'));

            var maxTitleLength = 120;
            
            // append the name of the organisation that supplied the record
            shortTitle += ' - ' + provider;
            
            if(shortTitle.length > maxTitleLength) {
                shortTitle = shortTitle.substr(0, maxTitleLength) + "...";
            }

            //Figure out our icon class
            var type = onlineResource ? onlineResource.get('type') : '';
            var iconCls = undefined;
            switch(type) {
            case portal.csw.OnlineResource.WFS:
            case portal.csw.OnlineResource.WCS:
            case portal.csw.OnlineResource.OPeNDAP:
                iconCls = 'data';
                break;
            default:
                iconCls = 'portrayal';
                break;
            }

            items.push({
                text : shortTitle,
                queryTarget : queryTargets[i],
                iconCls : iconCls,
                listeners : {
                    click : Ext.bind(function(queryTarget, mapWrapper) {
                        this._handleWithQuery([queryTarget], mapWrapper);
                    }, this, [queryTargets[i], mapWrapper])
                }
            });
        }

        //If we couldn't make any menu items, no point in proceeding
        if (items.length === 0 || point === null) {
            return;
        }

        var menu = Ext.create('Ext.menu.Menu', {
            id : 'querytargethandler-selection-menu',
            header: {
                xtype: 'header',
                titlePosition: 0,
                title: 'Please select a query source',
                cls: 'x-panel-header-light'
            },
            autoWidth : true,
            closable : true,
            margin: '0 0 10 0',
            enableScrolling: true,
            items : items
        });

        mapWrapper.showContextMenuAtLatLng(point, menu);
    },

    /**
     * Given an array of portal.layer.querier.QueryTarget objects,
     * figure out how to pass these to appropriate querier instances
     * and optionally show popup information on the map.
     *
     * This function will likely open info windows on the map and
     * hide/show loading masks where appropriate.
     *
     * @param mapWrapper An instance of portal.util.gmap.GMapWrapper
     * @param queryTargets Array of portal.layer.querier.QueryTarget objects
     */
    handleQueryTargets : function(mapWrapper, queryTargets) {
        //Ensure subsequent clicks destroy the popup menu
        var menu = Ext.getCmp('querytargethandler-selection-menu');
        if (menu) {
            menu.destroy();
        }

        if (!queryTargets || queryTargets.length === 0) {
            return;
        }

        var explicitTargets = []; // all QueryTarget instances with the explicit flag set
        for (var i = 0; i < queryTargets.length; i++) {
            if (queryTargets[i].get('explicit')) {
                explicitTargets.push(queryTargets[i]);
            }
        }

        //If we have an ambiguous set of targets - let's just ask the user what they meant
        if (explicitTargets.length > 1) {
            this._handleWithSelection(explicitTargets, mapWrapper);
            return;
        }

        //If we have a single explicit target, then our decision is really easy
        if (explicitTargets.length === 1) {
            this._handleWithQuery(explicitTargets, mapWrapper);
            return;
        }

        //Otherwise query everything
        this._handleWithQuery(queryTargets, mapWrapper);
    }
});/**
 * Ext.panel.Panel extension to roughly reproduce an Ext.grid.Panel for displaying
 * grouped layer records with custom panels on each layer's expander 
 * 
 * The old grid panel was deprecated as part of AUS-2685
 */
Ext.define('portal.widgets.panel.recordpanel.RecordPanel', {
    extend : 'Ext.panel.Panel',
    xtype : 'recordpanel',

    statics: {
        LAZY_LOAD_ID: 'lazy-load'
    },
    
    config: {
        allowReordering: false,
        store: null,
        titleField: 'name',
        titleIndex: 0,
        tools: null,
        childPanelGenerator: Ext.emptyFn,
        lazyLoadChildPanel: false
    },


    toolFieldMap: null, //A map of tool config objects keyed by field name
    recordRowMap: null, //A map of RecordRowPanel itemId's keyed by their recordId
    recordGroupMap: null, //A map of RecordGroupPanel itemId's keyed by their group key (only valid when store is in group mode)

    /**
     * Extends Ext.panel.Panel and adds the following:
     * {
     *  allowReordering: Boolean - If true, the records will be able to be reordered by dragging and dropping. Currently only supported with non grouped stores.
     *  emptyText: String - HTML string that will be shown when the contents of this panel are empty.
     *  store: Ext.data.Store - Contains the layer elements
     *  titleField: String - The field in store's underlying data model that will populate the title of each record
     *  titleIndex: Number - The 0 based index of where the title field will fit in amongst tools (default - 0)
     *  tools: Object[] - The additional tool columns, each bound to fields in the underlying data model
     *             field - Array/String - The field name in the model to bind this tool icon to. 
     *                                    Can be an array of field names in which case changes to any field in this array will trigger an update of the renderer/tip
     *                                    All callback function values will be the field value for the first element in the array (the primary field)
     *             stopEvent: Boolean - If true, click events will not propogate upwards from this tool.
     *             clickHandler - function(value, record) - Called whenever this tool is clicked. No return value.
     *             doubleClickHandler - function(value, record) - Called whenever this tool is clicked. No return value.
     *             tipRenderer - function(value, record, tip) - Called whenever a tooltip is generated. Return HTML content to display in tip
     *             iconRenderer - function(value, record) - Called whenever the underlying field updates. Return String URL to icon that will be displayed in tip.
     *  childPanelGenerator: function(record) - Called when records are added to the store. Return a generated Ext.Container for display in the specified row's expander
     *  lazyLoadChildPanel: Boolean - If true. the childPanelGenerator won't be fired until the row is expanded (default - false)
     * }
     * 
     * And the following events:
     * reorder(recordPanel, record, newIndex, oldIndex) - Fired when the store is reordered (by user interaction)
     */
    constructor : function(config) {
        var grouped = config.store.isGrouped();

        this.recordRowMap = {};
        this.recordGroupMap = {};

        //Ensure we setup the correct layout
        Ext.apply(config, {
            layout: {
                type: 'accordion',
                hideCollapseTool: !grouped,
                collapseFirst: true,
                fill: false,
                multi: grouped
            },
            items: [{
                xtype: 'panel',
                itemId: 'emptytext',
                collapsed: false,
                header: {
                    hidden: true
                },
                html: config.emptyText
            }],
            autoScroll: true,
            plugins: ['collapsedaccordian']
        });

        this.callParent(arguments);

        this._generateToolFieldMap();

        this.store.on({
            update: this.onStoreUpdate,
            load: this.onStoreLoad,
            clear: this.onStoreLoad, //The load handler will perform a full clear for us
            beforeload: this.onStoreBeforeLoad,
            filterchange: this.onStoreFilterChange,
            add: this.onStoreAdd,
            remove: this.onStoreRemove,
            scope: this
        });

        //Setup our Drag Drop zones when the component is rendered.
        if (this.rendered) {
            this._initDDZones();
        } else {
            this.on({
                afterrender: {
                    fn: this._initDDZones,
                    scope: this,
                    options: {
                        single: true
                    }
                }
            });
        }
        
        this.on({
            show: this.onComponentShow,
            scope: this
        });

        //If our store is already loaded - fill panel with existing contents
        if (this.store.getCount()) {
            var existingRecords = null;
            if (this.store.isFiltered()) {
                existingRecords = this.store.getData().getSource().getRange();
            } else {
                existingRecords = this.store.getData().getRange();
            }
            this.onStoreLoad(this.store, existingRecords, true);
        }
    },

    _getPrimaryField: function(toolCfg) {
        if (Ext.isArray(toolCfg.field)) {
            return toolCfg.field[0];
        } else {
            return toolCfg.field;
        }
    },
    
    /**
     * Register drag/drop zones (if applicable)
     */
    _initDDZones: function() {
        var me = this;
        if (me.allowReordering && !me.store.isGrouped()) {
            var dragEl = this.getEl();
            me.ddGroup = Ext.id(undefined, 'recordpanel-dd-');
            me.dragZone = new Ext.dd.DragZone(dragEl, {
                ddGroup: this.ddGroup,
                getDragData: function(e) {
                    var el = Ext.fly(e.target);
                    if (!el.hasCls('recordrowpanel')) {
                        el = el.up('.recordrowpanel');
                    }

                    if (el) {
                        var xy = el.getXY();

                        //Back reference our component from the DOM and then use that to lookup our record
                        var rowPanel = Ext.getCmp(el.dom.id);
                        var record = me.store.getById(rowPanel.recordId);

                        //Only show the header in the drag
                        el = el.down('.x-panel-header');
                        var sourceEl = el.dom;
                        var d = sourceEl.cloneNode(true);
                        d.id = Ext.id();

                        return {
                            ddel: d,
                            sourceEl: sourceEl,
                            repairXY: xy,
                            source: me,
                            draggedRecord: record
                        }
                    }
                },

                // Provide coordinates for the proxy to slide back to on failed drag.
                // This is the original XY coordinates of the draggable element captured
                // in the getDragData method.
                getRepairXY: function() {
                    return this.dragData.repairXY;
                }
            });


            this.dropTarget = new Ext.dd.DropTarget(dragEl, {
                ddGroup : me.ddGroup,
                notifyDrop: function(ddSource, e, data) {
                    var dropSuccess = false;
                    if (Ext.isNumber(me.lastDDInsertionIdx)) {
                        var rec = data.draggedRecord;
                        var oldIdx = me.store.indexOf(rec);
                        var newIdx = me.lastDDInsertionIdx;
                        
                        if (oldIdx < newIdx) {
                            newIdx--;
                        }
                        
                        if (oldIdx !== newIdx) {
                            me.store.remove(rec, true);
                            me.store.insert(newIdx, rec);
                            me.fireEvent('reorder', me, rec, newIdx, oldIdx);
                        }
                        dropSuccess = true;
                    }
                    
                    me._clearDropTarget();
                    return dropSuccess;
                },
                notifyOver: function(ddSource, e, data) {
                    //To generate our highlight we need to iterate all row panels and look
                    //for the row under our mouse (not ideal - but should be quick)
                    var pageX = e.getX();
                    var pageY = e.getY();
                    var recordPanelEls = data.source.getEl().query('.recordrowpanel', false);
                    // Returns 0 if no intercept. Returns -1 if in top half of box, Returns 1 if in bottom half
                    var boxIntercept = function(x, y, box) {
                        if (x < box.x || x >= (box.x + box.width) ||
                            y < box.y || y >= (box.y + box.height)) {
                            return 0;
                        } else if (y < (box.y + box.height / 2)) {
                            return -1;
                        } else {
                            return 1;
                        }
                    };
                    
                    var isHighlightMade = false;
                    me._clearDropTarget();
                    Ext.each(recordPanelEls, function(el, idx) {
                        var box = el.getBox();
                        var intercept = 0;
                        if (intercept = boxIntercept(pageX, pageY, box)) {
                            if (intercept < 0) {
                                el.addCls('recordpanel-insertabove');
                            } else {
                                el.addCls('recordpanel-insertbelow');
                            }
                        
                            isHighlightMade = true;
                            me.lastDDHighlight = el;
                            me.lastDDInsertionIdx = intercept < 0 ? idx : (idx + 1);
                            return false;
                        }
                    });
                    
                    return isHighlightMade ? Ext.dd.DropZone.prototype.dropAllowed : false;
                },
                notifyOut: function(ddSource, e, data) {
                    me._clearDropTarget();
                }
            });
        }
    },
    
    /**
     * If there is a drag drop highlight set, this function will clear the
     * visual highlight effect and associated element references.
     */
    _clearDropTarget: function() {
        if (this.lastDDHighlight) {
            this.lastDDHighlight.removeCls('recordpanel-insertabove');
            this.lastDDHighlight.removeCls('recordpanel-insertbelow');
            this.lastDDHighlight = null;
            this.lastDDInsertionIdx = null;
        }
    },

    /**
     * Populates toolFieldMap with the contents of the current tool config
     */
    _generateToolFieldMap: function() {
        this.toolFieldMap = {};

        Ext.each(this.tools, function(toolCfg) {
            toolCfg.toolId = Ext.id(null, 'recordpanel-tool-'); //assign each tool a unique ID

            var fields = toolCfg.field;
            if (!Ext.isArray(fields)) {
                fields = [fields];
            }

            Ext.each(fields, function(field) {
                if (Ext.isEmpty(this.toolFieldMap[field])) {
                    this.toolFieldMap[field] = [toolCfg];
                } else {
                    this.toolFieldMap[field].push(toolCfg);
                }
            }, this);
        }, this);
    },

    /**
     * Enumerates each RecordGroupPanel and passes them one by one to callback
     */
    _eachGroup: function(callback, scope) {
        this.items.each(function(recordGroupPanel) {
            if (recordGroupPanel instanceof portal.widgets.panel.recordpanel.GroupPanel) { 
                callback.call(scope, recordGroupPanel);
            }
        });
    },

    /**
     * Enumerates each RecordRowPanel and passes them one by one to callback
     */
    _eachRow: function(callback, scope) {
        //If we are grouped, our rows are children of groups. Otherwise they can be found at the top level
        if (this.store.isGrouped()) {
            this._eachGroup(function(recordGroupPanel) {
                recordGroupPanel.items.each(function(recordRowPanel) {
                    if (recordRowPanel instanceof portal.widgets.panel.recordpanel.RowPanel) {
                        callback.call(scope, recordRowPanel);
                    }
                });
            });
        } else {
            this.items.each(function(recordRowPanel) {
                if (recordRowPanel instanceof portal.widgets.panel.recordpanel.RowPanel) { 
                    callback.call(scope, recordRowPanel);
                }
            });
        }
    },

    /**
     * Simple wrapper around a tool click event that extracts the current
     * record field value and passes it to the delegate
     */
    _clickMarshaller: function(record, fieldName, handler) {
        var value = record.get(fieldName);
        handler.call(this, value, record);
    },

    /**
     * Installs all tooltips for the specified recordRowPanel. Ensure this is only
     * called once per recordRowPanel or tooltips will leak.
     */
    _installToolTips: function(recordRowPanel) {
        var record = this.store.getById(recordRowPanel.recordId);
        recordRowPanel.tipMap = {};

        //Install a unique tooltip for each tool
        Ext.each(this.tools, function(tool) {
            var primaryField = this._getPrimaryField(tool);

            recordRowPanel.tipMap[tool.toolId] = Ext.create('Ext.tip.ToolTip', {
                target: recordRowPanel.getHeader().down('#' + tool.toolId).getEl(),
                trackMouse: true,
                listeners: {
                    beforeshow: function(tip) {
                        var content = tool.tipRenderer(record.get(primaryField), record, tip);
                        tip.update(content);
                    }
                }
            });
        }, this);

        //Ensure we destroy the tips if we remove this panel
        recordRowPanel.on('destroy', function(recordRowPanel) {
            for (var toolId in recordRowPanel.tipMap) {
                recordRowPanel.tipMap[toolId].destroy();
            }
            recordRowPanel.tipMap = {};
        });
    },

    /**
     * Generates a RecordRowPanel config object for a given record (also registers it internally so ensure this config gets added to the widget)
     */
    _generateRecordRowConfig: function(record, groupMode) {
        var tools = [];
        Ext.each(this.tools, function(tool) {
            var field = this._getPrimaryField(tool);
            var fieldValue = record.get(field);
            var clickBind = Ext.isEmpty(tool.clickHandler) ? null : Ext.bind(this._clickMarshaller, this, [record, field, tool.clickHandler], false);
            var doubleClickBind = Ext.isEmpty(tool.doubleClickHandler) ? null : Ext.bind(this._clickMarshaller, this, [record, field, tool.doubleClickHandler], false);
            tools.push({
                itemId: tool.toolId,
                stopEvent: tool.stopEvent,
                clickHandler: clickBind,
                doubleClickHandler: doubleClickBind,
                icon: tool.iconRenderer(fieldValue, record)
            });
        }, this);

        var recordId = record.getId();
        var newItemId = Ext.id(null, 'record-row-');
        this.recordRowMap[recordId] = newItemId; 
        
        
        var childPanel = null;
        if (this.lazyLoadChildPanel) {
            childPanel = {xtype: 'container', itemId: portal.widgets.panel.recordpanel.RecordPanel.LAZY_LOAD_ID};
        } else {
            childPanel = this.childPanelGenerator ? this.childPanelGenerator(record) : null;
        }
        
        return {
            xtype: 'recordrowpanel',
            recordId: recordId,
            itemId: newItemId,
            title: record.get(this.titleField), 
            titleIndex: this.titleIndex,
            groupMode: groupMode,
            tools: tools,
            record: record,
            items: childPanel ? [childPanel] : null,
            listeners: {
                scope: this,
                afterrender: this._installToolTips,
                beforeexpand: function(rp) {
                    if (!this.lazyLoadChildPanel) {
                        return;
                    }
                    
                    var placeHolder = rp.down('#' + portal.widgets.panel.recordpanel.RecordPanel.LAZY_LOAD_ID);
                    if (!placeHolder) {
                        return;
                    }
                    
                    rp.remove(placeHolder);
                    if (this.childPanelGenerator) {
                        rp.add(this.childPanelGenerator(rp.record));
                    }
                }
            }
        };
    },

    /**
     * Generates all widgets for a grouped data store
     * 
     * @param recordSelection Record[] ONLY records in this list will be considered for widget/group generation
     */
    _generateGrouped: function(recordSelection) {
        //Run through our groups of records, creating new 
        //items as we go 
        var newItems = [];
        var groups = this.store.getGroups();
        groups.each(function(groupObj) {
            var rows = [];

            //Create a RecordRowPanel for each row we receive
            Ext.each(groupObj.items, function(record) {
                var add = recordSelection === null || recordSelection === undefined;
                if (!add) {
                    Ext.each(recordSelection, function(r) {
                        if (r.getId() === record.getId()) {
                            add = true;
                            return false;
                        }
                    });
                }
                
                if (add) {
                    rows.push(this._generateRecordRowConfig(record, false));
                }
            }, this);

            //Shortcut out if we've filtered this grouping down to nothing
            if (Ext.isEmpty(rows)) {
                return;
            }
            
            //Next step is to figure out if our group already exists or whether we need
            //to create a new group
            var groupKey = groupObj.getConfig().groupKey;
            if (Ext.isEmpty(this.recordGroupMap[groupKey])) {
                //Create group
                var newGroupId = Ext.id(null, 'record-group-');
                this.recordGroupMap[groupKey] = newGroupId; 
                var newGroup = {
                    xtype: 'recordgrouppanel',
                    title: groupKey,
                    itemId: newGroupId,
                    items: rows,
                    groupKey: groupKey
                };
                newItems.push(newGroup);
            } else {
                //Add in situ
                var groupId = this.recordGroupMap[groupKey];
                var groupCmp = this.queryById(groupId);
                groupCmp.add(rows);
                groupCmp.refreshTitleCount();
            }
        }, this);

        this.add(newItems);
    },

    /**
     * Generates all widgets for an un-grouped data store
     * 
     * @param records Record[] if set, only these records will be used to generate widgets. 
     *                         Otherwise the entire store is used
     * @param insertionIndex Where the records should be inserted (if undefined, just append)           
     */
    _generateUnGrouped: function(records, insertionIndex) {
        var rows = [];
        var iterFn = function(record) {
            rows.push(this._generateRecordRowConfig(record, true));
        };
        
        if (records === null || records === undefined) {
            this.store.each(iterFn, this);
        } else {
            Ext.each(records, iterFn, this);
        }
        
        if (Ext.isNumber(insertionIndex)) {
            this.insert(insertionIndex, rows);
        } else {
            this.add(rows);
        }
    },
    
    /**
     * Updates the empty text hidden/visible status depending on whether any records are showing. Will
     * ALWAYS show empty text if this component is hidden.
     */
    _updateEmptyText: function() {
        var visibleItems = 0;
        
        if (this.store.isGrouped()) {
            this._eachGroup(function(recordGroupPanel) {
                if (recordGroupPanel.isVisible()) {
                    visibleItems++;
                }
            });
        } else {
            this._eachRow(function(recordRowPanel) {
                if (recordRowPanel.isVisible()) {
                    visibleItems++;
                }
            });
        }
        
        this.down('#emptytext').setHidden(visibleItems !== 0);
    },

    onComponentShow: function(recordPanel) {
        //Update our empty text visibility if the component is shown. Empty text visibility
        //does NOT properly work when the component is hidden hence we are forced to check this
        //every time the component is returned from hiding - See AUS-2811
        this._updateEmptyText();
    },
    
    /**
     * Handle updating renderers/tips for the modified fields
     */
    onStoreUpdate: function(store, record, operation, modifiedFieldNames, details) {
        if (!this.items.getCount()) {
            return;
        }

        //Figure out what fields we actually need to update
        var toolsToUpdate = {}; //Tools that require updates keyed by toolId 
        var anyToolsToUpdate = false;
        Ext.each(modifiedFieldNames, function(modifiedField) {
            if (!Ext.isEmpty(this.toolFieldMap[modifiedField])) {
                Ext.each(this.toolFieldMap[modifiedField], function(toolCfg) {
                    toolsToUpdate[toolCfg.toolId] = toolCfg;
                    anyToolsToUpdate = true;
                }, this);
            }
        }, this);
        if (!anyToolsToUpdate) {
            return;
        }

        //Update the modifiedFields
        var itemId = this.recordRowMap[record.getId()];
        var recordRowPanel = this.down('#' + itemId);
        if (!recordRowPanel) {
            return;
        }

        for (var toolId in toolsToUpdate) {
            var tool = toolsToUpdate[toolId];
            var primaryField = this._getPrimaryField(tool);
            var img = recordRowPanel.down('#' + toolId);
            var newSrc = tool.iconRenderer(record.get(primaryField), record);
            img.setSrc(newSrc);
        }
    },

    /**
     * When the store starts loading, begin prepping the panel for displaying
     * records
     */
    onStoreBeforeLoad: function(store, operation) {
        if (!this.rendered) {
            return;
        }

        if (!this.loadMask) {
            this.loadMask = new Ext.LoadMask({
                msg: 'Loading...',
                target: this
            });
        } 

        this.loadMask.show();
    },

    /**
     * When a filter changes, we need to enumerate each record row to see if it's currently in the filtered store (or not)
     * and shift its visibility accordingly
     */
    onStoreFilterChange: function(store, filters) {
        this.getLayout().suspendAnimations();
        Ext.suspendLayouts();

        this._eachRow(function(recordRowPanel) {
            var filtered = store.find('id', recordRowPanel.recordId) < 0; //we cant use store.getById as that bypasses any filters
            recordRowPanel.setHidden(filtered);
        }, this);

        this._eachGroup(function(recordGroupPanel) {
            recordGroupPanel.refreshTitleCount();
            if (recordGroupPanel.visibleItemCount) {
                recordGroupPanel.setHidden(false);
            } else {
                recordGroupPanel.setHidden(true);
            }
        });

        Ext.resumeLayouts();
        this.getLayout().resumeAnimations();
        
        this._updateEmptyText();
    },

    /**
     * When we receive a new set of records, update all items in the display from the
     * specified store. Any existing displaying records will be cleared first.
     */
    onStoreLoad: function(store) {
        if (this.loadMask) {
            this.loadMask.hide();
        }

        //Clear out the panel first (dont use removeAll otherwise we'll remove
        //our #collapsedtarget hidden items from CollapsedAccordianLayout
        for (var i = this.items.getCount() - 1; i >= 0; i--) {
            var item = this.items.getAt(i);
            if (item instanceof portal.widgets.panel.recordpanel.AbstractChild) {
                this.remove(item);
            }
        }
        this.recordRowMap = {};
        this.recordGroupMap = {};

        if (store.isGrouped()) {
            this._generateGrouped();
        } else {
            this._generateUnGrouped();
        }
        
        this._updateEmptyText();
    },

    /**
     * Fired when we get records to add that don't
     * require the entire store to be reloaded. In this case,
     * create groups (if required) and generate items.
     */
    onStoreAdd: function(store, records, index) {
        this.getLayout().suspendAnimations();
        Ext.suspendLayouts();
        
        if (store.isGrouped()) {
            this._generateGrouped(records);
        } else {
            //We insert at row + 1 to account for collapsed accordian item at position 0
            this._generateUnGrouped(records, index + 1);
        }
        
        Ext.resumeLayouts();
        this.getLayout().resumeAnimations();
        this.doLayout();
        
        this._updateEmptyText();
    },

    /**
     * Fired when we get small subsets of records to remove. In this case
     * we just delete the individual widgets (clearing up groups as required)
     */
    onStoreRemove: function(store, records, index, isMove) {
        this.getLayout().suspendAnimations();
        Ext.suspendLayouts();
        
        Ext.each(records, function(record) {
            var recordId = record.getId();
            var rowId = this.recordRowMap[recordId];
            var rowCmp = this.queryById(rowId);
            
            if (store.isGrouped()) {
                var groupCmp = rowCmp.ownerCt;
                var groupId = groupCmp.getItemId();
                
                delete this.recordRowMap[recordId];
                groupCmp.remove(rowCmp);
                if (groupCmp.items.getCount() <= 1) {
                    delete this.recordGroupMap[groupCmp.groupKey];
                    this.remove(groupCmp);
                } else {
                    groupCmp.refreshTitleCount();
                }
            } else {
                delete this.recordRowMap[recordId];
                this.remove(rowCmp);
            }
        }, this);
        
        Ext.resumeLayouts();
        this.getLayout().resumeAnimations();
        this.doLayout();
        
        this._updateEmptyText();
    },

    /**
     * Expands the row with the specified recordId. If that ID DNE, this has no effect.
     * 
     * The group containing the row will also be expanded.
     */
    expandRecordById: function(recordId) {
        this._eachRow(function(row) {
            if (row.recordId === recordId) {
                if (this.store.isGrouped()) {
                    row.ownerCt.expand();
                }
                row.expand();
            }
        });
    }
});
/**
 * An extension to Ext.ux.CheckColumn which allows
 * the ability for a custom value renderer.
 *
 * This is essentially the same as rendering this column
 * based on a 'view' of the underlying boolean data.
 *
 * Instances of this class must specify the functions:
 *  getCustomValueBool
 *  setCustomValueBool
 */
Ext.define('portal.widgets.grid.column.RenderableCheckColumn', {
    extend: 'Ext.ux.CheckColumn',
    alias: 'widget.renderablecheckcolumn',
    
    
    /**
     * A function that will be called when the column needs
     * to get the custom value for this column for the particular value
     *
     * function(portal.widgets.grid.column.RenderableCheckColumn, value, Ext.data.Model)
     */
    getCustomValueBool : portal.util.UnimplementedFunction,

    
    /**
     * A function that will be called to update the custom value of field
     *
     * function(portal.widgets.grid.column.RenderableCheckColumn, checked, Ext.data.Model)
     */
    setCustomValueBool : portal.util.UnimplementedFunction,

    constructor: function(config) {
        if (config.getCustomValueBool) {
            this.getCustomValueBool = config.getCustomValueBool;
        }
        if (config.setCustomValueBool) {
            this.setCustomValueBool = config.setCustomValueBool;
        }
        this.callParent(arguments);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * VT:I don't think this is needed anymore
     */
    processEvent: function(type, view, cell, recordIndex, cellIndex, e) {
        if (type === 'mousedown' || (type === 'keydown' && (e.getKey() === e.ENTER || e.getKey() === e.SPACE))) {
            var record = view.panel.store.getAt(recordIndex),
                dataIndex = this.dataIndex,
                value = record.get(dataIndex);
                checked = !this.getCustomValueBool(this, value,record);

            this.setCustomValueBool(this, checked, record);
            record.afterEdit(dataIndex);//trick record into triggering an update
            this.fireEvent('checkchange', this, recordIndex, checked);
            // cancel selection.
            return false;
        } else {
            return this.callParent(arguments);
        }
    },

    renderer : function(value, metadata, record, rowIndex, colIndex, store, view) {
        var header = view.getHeaderAtIndex(colIndex);
        return this.callParent([header.getCustomValueBool(header, value,record)]);

        
    }
});

/**
 * Contains debug information about the final state of rendering. ie what requests were made
 * to where for data.
 *
 *  events :
 *      change(portal.layer.filterer.Filterer this, String[] keys)
 *          Fired whenever the map changes, passed an array of all keys that have changed.
 */
Ext.define('portal.layer.renderer.RenderDebuggerData', {
    extend: 'portal.util.ObservableMap',

    constructor : function(config) {
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Updates this render debug info with a new debug info for a particular key. if key DNE it will be created,
     * otherwise it will be overridden.
     *
     * key - string
     * debugDetail - string
     *
     * returns void
     */
    updateResponse : function(key, debugDetail) {
        this.setParameter(key, debugDetail);
    },

    /**
     * Renders this debug data into a HTML string that represents all key/status pairs
     */
    renderHtml : function() {
        var htmlString = '<br/>' ;
        var parametersAdded = 0;

        for(i in this.parameters) {
            var unescapedXml = this.parameters[i];
            if (!Ext.isString(unescapedXml)) {
                unescapedXml = '';
            }
            var escapedXml = unescapedXml.replace(/</g, '&lt;');

            parametersAdded = parametersAdded + 1;
            htmlString += '<b>'+ i + '</b>' + '<br/> ' + escapedXml +'<br/><br/>';
        }
        if (parametersAdded === 0) {
            htmlString += 'No information has yet been recorded...';
        }
        htmlString += '<br/>' ;
        return htmlString;
    }
});



/**
 * Renderer is an abstract class representing the process of
 * requesting and displaying data from a data source.
 *
 *  events:
 *      renderstarted(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer)
 *          Fired whenever the renderer begins the process of rendering a new layer
 *      renderfinished(portal.layer.renderer.Renderer this)
 *          Fired whenever the renderer finishes the rendering of new data
 *      visibilitychanged(portal.layer.renderer.Renderer this, bool newVisibility)
 *          Fired whenever the layer's visibility changes
 */
Ext.define('portal.layer.renderer.Renderer', {
    extend: 'Ext.util.Observable',

    map : null, //portal.map.BaseMap
    visible : false, //whether the render is currently 'visible' or not,
    hasData : false, //whether the renderer has rendered any data or not,
    proxyUrl : '',  //a url to proxy data requests through (implementation specific)
    proxyCountUrl : '', //a url to proxy data count requests through (implementation specific)
    parentLayer : null, // a reference to the portal.layer.Layer that owns this renderer
    renderDebuggerData : null,
    renderStatus : null,
    primitiveManager : null,

    /**
     * Expects a Ext.util.Observable config with the following additions
     * {
     *  map : An instance of a google map GMap2 object
     * }
     */
    constructor: function(config) {

      

        //Setup class variables
        this.listeners = config.listeners;
        this.map = config.map;
        this.parentLayer = config.parentLayer;
        this.primitiveManager = this.map.makePrimitiveManager();
        this.renderStatus = Ext.create('portal.layer.renderer.RenderStatus', {}); //for maintaining the status of rendering,
        this.renderDebuggerData = Ext.create('portal.layer.renderer.RenderDebuggerData', {}); //for maintaining debug info about underlying requests

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * An abstract function for displaying data from a variety of data sources. This function will
     * raise the renderstarted and renderfinished events as appropriate. The effect of multiple calls
     * to this function (ie calling displayData again before renderfinished is raised) is undefined.
     *
     * This function will re-render itself entirely and thus may call removeData() during the normal
     * operation of this function
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer,
     *          function(portal.layer.renderer.Renderer this, portal.csw.OnlineResource[] resources, portal.layer.filterer.Filterer filterer, bool success) callback
     *
     * returns - void
     *
     * resources - an array of data sources which should be used to render data
     * filterer - A custom filter that can be applied to the specified data sources
     * callback - Will be called when the rendering process is completed and passed an instance of this renderer and the parameters used to call this function
     */
    displayData : portal.util.UnimplementedFunction,

    /**
     * An abstract function for aborting the display process. If this function is called any
     * in process rendering should attempt to be halted (if possible). If no rendering is underway
     * then this function should have no effect
     *
     * This function will typically be called immediately prior to a remove with the expectation
     * that any existing data be removed from the map AND no more data be added to the map.
     *
     * function()
     *
     * returns - void
     */
    abortDisplay : portal.util.UnimplementedFunction,

    /**
     * An abstract function for creating a legend that can describe the displayed data. If no
     * such thing exists for this renderer then null should be returned.
     *
     * function(portal.csw.OnlineResource[] resources,
     *          portal.layer.filterer.Filterer filterer)
     *
     * returns - portal.layer.legend.Legend or null
     *
     * resources - (same as displayData) an array of data sources which should be used to render data
     * filterer - (same as displayData) A custom filter that can be applied to the specified data sources
     */
    getLegend : portal.util.UnimplementedFunction,

    /**
     * An abstract function that is called when this layer needs to be permanently removed from the map.
     * In response to this function all rendered information should be removed
     *
     * function()
     *
     * returns - void
     */
    removeData : portal.util.UnimplementedFunction,

    ////////////////// Getter/Setters

    /**
     * A function for setting this layer's visibility.
     *  
     * visible - a bool
     * VT: not in use: Mark for deletion
     */
    getVisible : function() {
        return this.visible;
    },

    /**
     * A function for setting this layer's visibility.
     * 
     * visible - a bool
     * 
     * VT: not in use: Mark for deletion
     */
    setVisible : function(visible) {
        this.visible = visible;
        this.fireEvent('visibilitychanged', this, visible);
    },

    /**
     * Gets whether this renderer 'has data'. i.e. whether
     * this renderer has successfully been able to render information on the map
     */
    getHasData : function() {
        return this.hasData;
    },

    /**
     * Sets whether this renderer 'has data'. i.e. whether
     * this renderer has successfully been able to render information on the map
     */
    setHasData : function(hasData) {
        this.hasData = hasData;
    },

    /**
     * Gets the url to proxy data requests through (implementation specific)
     */
    getProxyUrl : function() {
        return this.proxyUrl;
    },

    /**
     * Gets the url to proxy data count requests through (implementation specific)
     */
    getProxyCountUrl : function() {
        return this.proxyCountUrl;
    },

    /**
     * Gets the portal.layer.renderer.RendererDebuggerData used by this renderer
     */
    getRendererDebuggerData : function() {
        return this.renderDebuggerData;
    },
    
    setVisibility : function(visible) {
        this.primitiveManager.setVisibility(visible);
    }
});/**
 * A factory class for creating instances of portal.layer.renderer.Renderer
 */
Ext.define('portal.layer.renderer.RendererFactory', {

    map : null, //instance of portal.map.BaseMap

    constructor: function(config){
        this.map = config.map;
        this.callParent(arguments);
    },

    /**
     * An abstract function for building a portal.layer.renderer.Renderer
     * suitable for a given KnownLayer
     *
     * function(portal.knownlayer.KnownLayer knownLayer)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     */
    buildFromKnownLayer : portal.util.UnimplementedFunction,

    /**
     * An abstract function for building a portal.layer.renderer.Renderer
     * suitable for a given CSWRecord
     *
     * function(portal.csw.CswRecord cswRecord)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     */
    buildFromCswRecord : portal.util.UnimplementedFunction,
    
    /**
     * An abstract function for building a portal.layer.renderer.Renderer
     * suitable for a given CSWRecord
     *
     * function(portal.csw.CswRecord cswRecord)
     *
     * returns - This function must return a portal.layer.downloader.Downloader
     */
    buildFromKMLRecord : portal.util.UnimplementedFunction
});/**
 * Contains status information about the current state of rendering.
 *
 *  events :
 *      change(portal.layer.filterer.Filterer this, String[] keys)
 *          Fired whenever the map changes, passed an array of all keys that have changed.
 */
Ext.define('portal.layer.renderer.RenderStatus', {
    extend: 'portal.util.ObservableMap',

    constructor : function(config) {
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Updates this render status with a new status for a particular key. if key DNE it will be created,
     * otherwise it will be overridden.
     *
     * key - string
     * responseStatus - string
     *
     * returns void
     */
    updateResponse : function(key, responseStatus) {
        this.setParameter(key, responseStatus);
    },

    /**
     * Batch sets all keys with a specified status.
     *
     * allKeys - array of strings
     * responseStatus - string
     */
    initialiseResponses : function(allKeys, responseStatus) {
        var params = {};
        for (var i = 0; i < allKeys.length; i++) {
            params[allKeys[i]] = responseStatus;
        }

        this.setParameters(params, true);
    },

    /**
     * Renders this status into a HTML string consisting of a table that represents all key/status pairs
     */
    renderHtml : function() {
        var parameterAddCount = 0;
        var htmlString = '<div class="auscope-servicestatus-grid"><table><tr><td>Request Status</td></tr>' ;

        for(i in this.parameters) {
            if(!this.parameters[i].toString().match('function')) {
                parameterAddCount++;
                if(i.length >= 1) {
                    htmlString += '<tr><td>'+ i + ' - ' + this.parameters[i] +'</td></tr>';
                } else {
                    htmlString += '<tr><td>'+ this.parameters[i] +'</td></tr>';
                }
            }
        }
        htmlString += '</table></div>' ;

        if (parameterAddCount === 0) {
            return 'No status has been recorded';
        } else {
            return htmlString;
        }

    }
});



/**
 * A plugin for an Ext.grid.Panel class that a context menu that
 * shows whenever a row is right clicked.
 *
 * To use this plugin, assign the following field to the plugin constructor
 * {
 *  contextMenu : Ext.menu.Menu - will be shown/hidden according to user right clicks.
 * }
 */
Ext.define('portal.widgets.grid.plugin.RowContextMenu', {
    alias: 'plugin.rowcontextmenu',

    /**
     * The Ext.grid.Panel this plugin will be applied to.
     */
    _grid : null,

    _contextMenu : null,

    constructor : function(cfg) {
        this._contextMenu = cfg.contextMenu;
        this.callParent(arguments);
    },

    init: function(grid) {
        this._grid = grid;
        grid.getView().on('itemcontextmenu', this._onContextMenu, this);
    },

    _onContextMenu : function(view, record, el, index, e, eOpts) {
        e.stopEvent();

        var sm = this._grid.getSelectionModel();
        if (!sm.isSelected(record)) {
            this._grid.getSelectionModel().select([record]);
        }

        this._contextMenu.showAt(e.getXY());
        return false;
    }
});/**
 * A plugin for an Ext.grid.Panel class that a container will show
 * whenever a row is selected. The container will render horizontally
 * below the selected row, seemingly "inline"
 *
 * To use this plugin, assign the following field to the plugin constructor
 * {
 *  generateContainer : function(record, parentElId, grid) - returns Ext.container.Container,
 *  allowMultipleOpen : Boolean - whether multiple containers can be open simultaneously.
 *  toggleColIndexes : int[] - Optional - Which column indexes can toggle open/close on single click - Defaults to every column
 *  recordIdProperty: Optional String - The property that should be used for record ID value (defaults to 'id'). If the 
 *                                      record idProperty has been defined you will to specify that value here. 
 *  baseId : String - Optional (default='rowexpandercontainer') - To be used as the base in the containing element Id so can 
 *      reuse this control in multiple locations (all baseIds must be unique) 
 * }
 *
 * Contains two events:
 *  containerhide, containershow
 *
 * Example usage:
 *  var panel = Ext.create('Ext.grid.Panel', {
 *                title : 'Grid Panel Test',
 *                store : store,
 *                split: true,
 *                renderTo: 'foo',
 *                plugins : [{
 *                  ptype : 'rowexpandercontainer',
 *                  generateContainer : function(record, parentElId, grid) {
 *                     return Ext.create('Ext.panel.Panel', {});
 *                  }
 *                }]
 *  });
 *
 * Work has been adapted from: http://www.rahulsingla.com/blog/2010/04/extjs-preserving-rowexpander-markup-across-view-refreshes#comment-86
 */
Ext.define('portal.widgets.grid.plugin.RowExpanderContainer', {
    extend: 'Ext.grid.plugin.RowExpander',
    
    alias: 'plugin.rowexpandercontainer',
    generateContainer : portal.util.UnimplementedFunction,
    allowMultipleOpen : false,
    recordIdProperty: null, //can be null
    rowBodyTpl: null, 
    storedHtml: null,   
    recordStatus: null,  
    generationRunning: false,
    toggleColIndexes: null,
    baseId: "RowExpanderContainer",
    expandOnEnter : false,

    
    constructor: function(config) {
        this.callParent(arguments);
        
        this.toggleColIndexes = Ext.isArray(config.toggleColIndexes) ? config.toggleColIndexes : null;
        this.allowMultipleOpen = config.allowMultipleOpen ? true : false;
        this.storedHtml = {};
        this.recordStatus = {};
        
        if (config.recordIdProperty) {
            this.recordIdProperty = config.recordIdProperty;
        }
        
        if (config.baseId) {
            this.baseId = config.baseId;
        }
        this.rowBodyTpl = '<div id="'+this.baseId+'-{[values.' + (this.recordIdProperty ? this.recordIdProperty : 'id') + '.toString().replace(/[^0-9A-Za-z\\-]/g,\'-\')]}"></div>'
    },
    
    //override to do nothing. We don't want an expander column
    addExpander: function(expanderGrid) {
        
    },
    
    init : function(grid) {
        this.callParent(arguments);
        var view = grid.getView();
        
        this.view = view;
        this.grid = grid;
        
        view.on('expandbody', this.onExpandBody, this);
        view.on('collapsebody', this.onCollapseBody, this);
        view.on('cellclick', this.onCellClick, this);
        view.on('groupexpand', this.onGroupExpand, this);
        view.on('groupcollapse', this.onGroupExpand, this);
        
        view.on('refresh', this.onRefresh, this);
        
        view.on('itemupdate', this.restoreRowContainer, this);
        
        view.on('resize', this.onResize, this);
        
        grid.getStore().on('clear', function(store) {
            this._hardReset();
        }, this);
    },
    
    /**
     * Resets the row expander state to that of a fresh instance. Cleans up any existing containers
     */
    _hardReset: function() {
        for (id in this.recordStatus) {
            var status = this.recordStatus[id];
            if (status && status.container) {
                status.container.destroy();
            }
        }
        
        this.storedHtml = {};
        this.recordStatus = {};
    },
    
    /**
     * Utility for accessing the defined "ID" property of a record
     */
    _getId: function(record, sanitise) {
        var id;
        if (this.recordIdProperty) {
            id = record.get(this.recordIdProperty).toString();
        } else {
            id = record.id.toString();
        }
        
        if (sanitise) {
            id = id.replace(/[^0-9A-Za-z\\-]/g,'-');
        }
        
        return id;
    },
    
    /**
     * Returns record if it exists or null.
     * 
     * @param recordId String
     */
    getStoreRecord: function(recordId) {
        return this.grid.getStore().getById(recordId);
    },
    
    /**
     * Returns true if the given record is rendered, expanded AND 
     * the internal rowbody is empty
     */
    restorationRequired: function(record) {
        //Is the record expanded?
        var id = this._getId(record);
        if (!(id in this.recordStatus)) {
            return false;
        } else if (!this.recordStatus[id].expanded) {
            return false;
        } 
        
        //Is the record visible?
        var el = this.view.getRow(record);
        if (!el) {
            return false;
        }
        
        var body = Ext.DomQuery.selectNode('#'+this.baseId + '-' + this._getId(record, true), el.parentNode); // rowexpandercontainer-'
        if (body.hasChildNodes()) {
            return false;
        }
        
        return true;
    },
    
    getRecordsForGroup: function(group) {
        var ds = this.grid.getStore();
        
        var groupInfo = ds.getGroups()[group];
        if (!groupInfo) {
            return [];
        }
        
        return groupInfo.children;
    },



    onExpandBody : function(rowNode, record, expandRow) {
        if (!this.allowMultipleOpen) {
            for (openId in this.recordStatus) {
                if (this.recordStatus[openId].expanded) {
                    var openRec = this.getStoreRecord(openId);
                    var openEl = this.view.getRow(openRec);
                    if (openEl !== null) {
                        this.toggleRow(openEl, openRec);
                    }
                }
            }
        }
        
        this.recordStatus[this._getId(record)] = {
            expanded : true,
            container : null 
        };
        
        this.restoreRowContainer(record);
    },
    
    onCollapseBody: function(rowNode, record, collapseRow) {
        this.recordStatus[this._getId(record)].expanded = false;
    },
    
    onCellClick: function(view, td, cellIndex, record, tr, rowIndex) {
        if (!Ext.isArray(this.toggleColIndexes) || Ext.Array.contains(this.toggleColIndexes, cellIndex)) {
            this.toggleRow(rowIndex, record);
        }
    },
    
    onResize: function() {
        for (openId in this.recordStatus) {
            if (this.recordStatus[openId].expanded) {
                if (this.recordStatus[openId].container) {
                    this.recordStatus[openId].container.updateLayout({
                        defer:false,
                        isRoot:false
                    });
                }
            }
        }
    },
    
    onGroupExpand : function(view, node, group, eOpts) {
        var recs = this.grid.getStore().getRange();
        var me = this;
        Ext.each(recs, function(record) {
            me.restoreRowContainer(record);
        });
    },
    
    onRefresh: function(view) {
        var store = this.grid.getStore(),
            n, row, record;
        for (n = 0; n < store.data.items.length; n++) {
            row = view.getRow(n);
            if (row) {
                record = store.getAt(n);
                this.restoreRowContainer(record);
            }
        }
    },
    
    restoreRowContainer: function(record) {        
        var me = this;
        
        //We don't want this function to be re-entrant
        //Which can occur if the generateContainer callback
        //makes any updates to record
        if (me.generationRunning === true) {
            return;
        }
        
        me.generationRunning = true;
        if (me.restorationRequired(record)) {
            var id = me.baseId + '-' + me._getId(record, true);   // "rowexpandercontainer-"
            var container = me.generateContainer(record, id, me.grid);
            
            // If a container already existed then destroy it first.
            if (me.recordStatus[me._getId(record)].container) {
                //AUS-2608 - Wrapped this in a try-catch due to some tooltips getting orphaned and throwing a JS error when
                //           trying to reference their dead parent.                
                try {
                    me.recordStatus[me._getId(record)].container.destroy();
                } catch(err) {
                    console.log("Error destroying parent container:", err);
                }
            }
            me.recordStatus[me._getId(record)].container = container;
            me.recordStatus[me._getId(record)].container.updateLayout({
                defer:false,
                isRoot:false
            });
        }

        this.generationRunning = false;
    }
    
});
/**
 * Ext.panel.Panel extensions to emulate the display of a grid panel row for a CommonBaseRecordPanel widget.
 * 
 * The grid panel was deprecated as part of AUS-2685
 */
Ext.define('portal.widgets.panel.recordpanel.RowPanel', {
    extend : 'portal.widgets.panel.recordpanel.AbstractChild',
    xtype : 'recordrowpanel',
    
    config: {
        titleIndex: 0,
        tools: null
    },
    
    /**
     * {
     *  title: String - title for this row panel
     *  titleIndex: Number - 0 based position of the title amongst all tools. (default 0)
     * 
     *  tools: Object[] - The additional tool columns, each bound to fields in the underlying data model
     *             clickHandler - function() - Called whenever this tool is clicked. No return value.
     *             doubleClickHandler - function() - Called whenever this tool is double clicked. No return value.
     *             icon - String - the icon to be displayed
     *             stopEvent - Boolean - whether the click events should be stopped propogating upwards
     * }
     */
    constructor : function(config) {
        var headerItems = [];
        Ext.each(config.tools, function(tool, index) {
            var leftMargin = '5';
            var rightMargin = '5';
            if (index == 0) {
                leftMargin = '0';
            }
            
            if (index == (config.tools.length - 1)) {
                rightMargin = '0';
            }
            
            headerItems.push({
                xtype : 'image',
                itemId: tool.itemId,
                width : 16,
                height : 16,
                margin : Ext.util.Format.format('0 {0} 0 {1}', rightMargin, leftMargin),
                src : tool.icon,
                plugins : [{
                    ptype: 'clickableimage', 
                    stopEvent: !!tool.stopEvent
                }],
                listeners : {
                    click : Ext.isEmpty(tool.clickHandler) ? Ext.emptyFn : tool.clickHandler,
                    dblclick : Ext.isEmpty(tool.doubleClickHandler) ? Ext.emptyFn : tool.doubleClickHandler
                }
            });
        });
        
        var title = config.title;
        
        delete config.tools;
        delete config.title;
        
        Ext.apply(config, {
            layout : 'fit',
            border: true,
            cls: 'recordrowpanel',
            bodyStyle: {
                'border-color': '#ededed'
            },
            margin: '0 0 0 0',
            header : {
                titlePosition : Ext.isNumber(config.titleIndex) ? config.titleIndex : 0,
                border: false,
                cls: 'recordrowpanelheader',
                style : {
                    'background-color':'white',
                    'border-color': '#ededed'
                },
                items : headerItems,
                padding: '8 0 8 0',
                height: 30,
                title: {
                    text: title,
                    margin: '0 0 0 10',
                    style: {
                        'font-size': '13px',
                        'font-weight': 'normal',
                        'color': '#000000'
                    }
                }
            }
        });
        this.callParent(arguments);
    }
});/**
 * A plugin for an Ext.grid.Panel class that allows the grid's
 * contents to be selected by a user
 */
Ext.define('portal.widgets.grid.plugin.SelectableGrid', {
    alias: 'plugin.selectablegrid',

    /**
     * The Ext.grid.Panel this plugin will be applied to.
     */
    _grid : null,

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    init: function(grid) {
        this._grid = grid;

        var view = grid.getView();
        var store = grid.getStore();
        view.on('itemadd', this._makeRecordsSelectable, this);
        view.on('viewready', function(view) {
            this._makeNodesSelectable(view.getNodes(), true);
        }, this);
        grid.on('afterlayout', function(grid) {
            if (grid.getView().viewReady) {
                this._makeRecordsSelectable(grid.getStore().getRange());
            }
        }, this);
    },

    /**
     * Makes added rows selectable
     */
    _makeRecordsSelectable : function(records) {
        var view = this._grid.getView();

        //For every record that was just added
        for (var i = 0; i < records.length; i++) {
            var node = view.getNode(records[i]);
            this._makeNodesSelectable(node, true);
        }
    },

    /**
     * Makes DOM nodes selectable (makes use of the Ext flyweight element)
     */
    _makeNodesSelectable : function(nodes, selectable) {
        if (!Ext.isArray(nodes)) {
            nodes = [nodes];
        }

        for (var i = 0; i < nodes.length; i++) {
            if (selectable) {
                Ext.fly(nodes[i]).selectable();
            } else {
                Ext.fly(nodes[i]).unselectable();
            }
        }
    }
});Ext.define('portal.util.permalink.serializers.SerializerV0', {
    extend : 'portal.util.permalink.serializers.BaseSerializer',

    getVersion : function() {
        return 0;
    },

    //This class is only for legacy deserialization therefore there is no serialize implementation

    /**
     * See parent interface for details.
     */
    deserialize : function(stateStr, callback) {
        var state = Ext.JSON.decode(stateStr);
        var mapState = serializationObj.mapState;
        var layers = serializationObj.activeLayers;

        //The above is ALMOST all the work - the schema has changed slightly
        for (var i = 0; i < layers.length; i++) {
            if (Ext.isDefined(layers[i].opacity)) {
                if (!layers[i].filter) {
                    layers[i].filter = {};
                }

                layers[i].filter.opacity = layers[i].opacity;
                layers[i].opacity = undefined;
            }

            if (Ext.isArray(layers[i].onlineResources)) {
                for (var j = 0; j < layers[i].onlineResources.length; j++) {
                    layers[i].onlineResources[j].type = layers[i].onlineResources[j].onlineResourceType;
                    layers[i].onlineResources[j].onlineResourceType = undefined;
                }
            }
        }

        callback({
            mapState : mapState,
            serializedLayers : layers
        });
    }
});Ext.define('portal.util.permalink.serializers.SerializerV1', {
    extend : 'portal.util.permalink.serializers.BaseSerializer',

    getVersion : function() {
        return 1;
    },

    //This class is only for legacy deserialization therefore there is no serialize implementation

    /**
     * See parent interface for details.
     */
    deserialize : function(stateStr, callback) {
        var state = Ext.JSON.decode(stateStr);
        var minifiedMapState = state.m;
        var minifiedLayers = state.a;

        //We need to take our minified map state and explode it into a meaningful object
        var mapState = {
            center : {
                lng : minifiedMapState.n,
                lat : minifiedMapState.a
            },
            zoom : minifiedMapState.z
        };

        //We need to take our minified active layer list and explode it into a meaningful object
        var serializedLayers = [];
        for (var i = 0; i < minifiedLayers.length; i++) {
            var onlineResources = undefined;
            if (Ext.isArray(minifiedLayers[i].r)) {
                onlineResources = [];
                for (var j = 0; j < minifiedLayers[i].r.length; j++) {
                    onlineResources.push({
                        url : minifiedLayers[i].r[j].u,
                        type : minifiedLayers[i].r[j].o,
                        name : minifiedLayers[i].r[j].n,
                        description : minifiedLayers[i].r[j].d
                    });
                }
            }

            var filter = minifiedLayers[i].f;
            if (!filter) {
                filter = {};
            }

            if (Ext.isDefined(minifiedLayers[i].o)) {
                filter.opacity = minifiedLayers[i].o;
            }

            this.serializedLayers.push({
                source : minifiedLayers[i].s,
                filter : filter,
                visible : minifiedLayers[i].v,
                id : minifiedLayers[i].i,
                onlineResources : onlineResources
            });
        }

        callback({
            mapState : mapState,
            serializedLayers : serializedLayers
        });
    }
});Ext.define('portal.util.permalink.serializers.SerializerV2', {
    extend : 'portal.util.permalink.serializers.BaseSerializer',

    getVersion : function() {
        return 2;
    },

    serialize : function(mapState, serializedLayers, callback) {
        //We strip out a lot of overhead by only limiting the size of our field definitions
        var serializationObj = {
            m : {
                a : mapState.center.lat,
                n : mapState.center.lng,
                z : mapState.zoom
            },
            a : []
        };

        //We also do the same by serializing our objects into 'shorter' variants of themselves that the deserialization step will take care of
        for (var i = 0; i < serializedLayers.length; i++) {
            var minifiedOnlineResources = undefined;
            if (Ext.isArray(serializedLayers[i].onlineResources)) {
                var serializedOrs = serializedLayers[i].onlineResources;
                minifiedOnlineResources = [];
                for (var j = 0; j < serializedOrs.length; j++) {
                    minifiedOnlineResources.push({
                        u : serializedOrs[j].url,
                        o : serializedOrs[j].type,
                        n : serializedOrs[j].name,
                        d : serializedOrs[j].description
                    });
                }
            }
            
            serializationObj.a.push(this.createSerializedObject(serializedLayers[i],minifiedOnlineResources));

        }

        callback(Ext.JSON.encode(serializationObj));
    },

    /**
     * See parent interface for details.
     */
    deserialize : function(stateStr, callback) {
        var state = Ext.JSON.decode(stateStr)
        var minifiedMapState = state.m;
        var minifiedLayers = state.a;

        //We need to take our minified map state and explode it into a meaningful object
        var mapState = {
            center : {
                lng : minifiedMapState.n,
                lat : minifiedMapState.a
            },
            zoom : minifiedMapState.z
        };

        //We need to take our minified active layer list and explode it into a meaningful object
        var serializedLayers = [];
        for (var i = 0; i < minifiedLayers.length; i++) {
            var onlineResources = undefined;
            if (Ext.isArray(minifiedLayers[i].r)) {
                onlineResources = [];
                for (var j = 0; j < minifiedLayers[i].r.length; j++) {
                    onlineResources.push({
                        url : minifiedLayers[i].r[j].u,
                        type : minifiedLayers[i].r[j].o,
                        name : minifiedLayers[i].r[j].n,
                        description : minifiedLayers[i].r[j].d
                    });
                }
            }
            
            serializedLayers.push(this.createDeSerializedObject(minifiedLayers[i],onlineResources));
                      
        }

        callback({
            mapState : mapState,
            serializedLayers : serializedLayers
        });
    },
    
    createDeSerializedObject : function(value, onlineResources){
        var result = {
            source : value.s,
            filter : value.f,
            visible : value.v,
            id : value.i,           
            onlineResources : onlineResources
        };
        
        return result;
    },
    
    createSerializedObject : function(value, onlineResources){
        var result = {
            s : value.source,
            f : value.filter,
            v : value.visible,
            i : value.id,           
            r : onlineResources
        };
        
        return result;
    }
});Ext.define('portal.util.permalink.serializers.SerializerV3', {
    extend : 'portal.util.permalink.serializers.SerializerV2',

    getVersion : function() {
        return 3;
    },

    serialize : function(mapState, serializedLayers, callback) {
        //Serialise as per V2 Serializer but then feed the results through LZMA compression
        //Return the compressed LZMA byte array (as a string)
        this.callParent([mapState, serializedLayers, function(stateString) {
            LZMA.compress(stateString, "1", function (result) {
                callback(String.fromCharCode.apply(String, result));
            });
        }]);
    },

    /**
     * See parent interface for details.
     */
    deserialize : function(stateString, callback) {
        //Turn LZMA string into byte array
        var compressedByteArray = [];
        for (var i = 0; i < stateString.length; i++) {
            compressedByteArray.push(stateString.charCodeAt(i));
        }

        //Decompress LZMA String, pass decompressed string to the V2 deserialiser
        //Return the results of the V2 deserialiser
        var parentDeserialize = portal.util.permalink.serializers.SerializerV2.prototype.deserialize;
        LZMA.decompress(compressedByteArray, Ext.bind(function (result, parentDeserialize) {
            parentDeserialize.call(this, result, function(resultObj) {
                callback(resultObj);
            });
        }, this, [parentDeserialize], true));
    }
});Ext.define('portal.util.permalink.serializers.SerializerV4', {
    extend : 'portal.util.permalink.serializers.SerializerV3',

    getVersion : function() {
        return 4;
    },

        
    createDeSerializedObject : function(value, onlineResources){
        var result = {
            source : value.s,
            filter : value.f,
            visible : value.v,
            id : value.i,  
            customlayer : value.c,
            onlineResources : onlineResources
        };
        
        return result;
    },
    
    createSerializedObject : function(value, onlineResources){
        var result = {
            s : value.source,
            f : value.filter,
            v : value.visible,
            i : value.id,   
            c : value.customlayer,
            r : onlineResources
        };
        
        return result;
    }
});/**
 * Utility functions for providing W3C DOM functionality with a single API for most browsers.
 *
 * Currently supported Browsers
 * IE 6-9
 * Mozilla Firefox 3+
 * Google Chrome
 */
Ext.ns('portal.util.xml.SimpleDOM');

//Constants
portal.util.xml.SimpleDOM.XML_NODE_ELEMENT = 1;
portal.util.xml.SimpleDOM.XML_NODE_ATTRIBUTE = 2;
portal.util.xml.SimpleDOM.XML_NODE_TEXT = 3;

/**
 * Utility for retrieving a W3C DOM Node 'localName' attribute across browsers.
 *
 * The localName is the node name without any namespace prefixes
 */
portal.util.xml.SimpleDOM.getNodeLocalName = function(domNode) {
    return domNode.localName ? domNode.localName : domNode.baseName;
};

/**
 * Returns the set of classes this node belongs to as an array of strings
 */
portal.util.xml.SimpleDOM.getClassList = function(domNode) {
    if (domNode.classList) {
        return domNode.classList;
    } else if (domNode['class']) {
        return domNode['class'].split(' ');
    } else if (domNode.className) {
        return domNode.className.split(' ');
    }
    return [];
};

/**
 * Figure out if domNode is a leaf or not
 * (Leaves have no nodes from XML_NODE_ELEMENT)
 */
portal.util.xml.SimpleDOM.isLeafNode = function(domNode) {
    var isLeaf = true;
    for ( var i = 0; i < domNode.childNodes.length && isLeaf; i++) {
        isLeaf = domNode.childNodes[i].nodeType !== portal.util.xml.SimpleDOM.XML_NODE_ELEMENT;
    }

    return isLeaf;
};

/**
 * Filters an array of DOM Nodes according to the specified parameters
 * @param nodeArray An Array of DOM Nodes
 * @param nodeType [Optional] An integer node type
 * @param namespaceUri [Optional] String to compare against node namespaceURI
 * @param nodeName [Optional] String to compare against the node localName
 */
portal.util.xml.SimpleDOM.filterNodeArray = function(nodeArray, nodeType, namespaceUri, nodeName) {
    var matchingNodes = [];
    for (var i = 0; i < nodeArray.length; i++) {
        var node = nodeArray[i];

        if (nodeType && node.nodeType !== nodeType) {
            continue;
        }

        if (namespaceUri && namespaceUri !== node.namespaceURI) {
            continue;
        }

        if (nodeName && nodeName !== portal.util.xml.SimpleDOM.getNodeLocalName(node)) {
            continue;
        }

        matchingNodes.push(node);
    }

    return matchingNodes;
};

/**
 * Gets all children of domNode as an Array that match the specified filter parameters
 * @param childNamespaceURI [Optional] The URI to lookup as a String
 * @param childNodeName [Optional] The node name to lookup as a String
 */
portal.util.xml.SimpleDOM.getMatchingChildNodes = function(domNode, childNamespaceURI, childNodeName) {
    return portal.util.xml.SimpleDOM.filterNodeArray(domNode.childNodes, portal.util.xml.SimpleDOM.XML_NODE_ELEMENT, childNamespaceURI, childNodeName);
};

/**
 * Gets all Attributes of domNode as an Array that match the specified filter parameters
 * @param childNamespaceURI [Optional] The URI to lookup as a String
 * @param childNodeName [Optional] The node name to lookup as a String
 */
portal.util.xml.SimpleDOM.getMatchingAttributes = function(domNode, attributeNamespaceURI, attributeName) {
    return portal.util.xml.SimpleDOM._filterNodeArray(domNode.attributes, portal.util.xml.SimpleDOM.XML_NODE_ATTRIBUTE, attributeNamespaceURI, attributeName);
};

/**
 * Given a DOM node, return its text content (however the browser defines it)
 */
portal.util.xml.SimpleDOM.getNodeTextContent = function(domNode) {
    return domNode.textContent ? domNode.textContent : domNode.text;
};

/**
 * Parse string to DOM
 */
portal.util.xml.SimpleDOM.parseStringToDOM = function(xmlString){
    var isIE11 = !!navigator.userAgent.match(/Trident.*rv[ :]*11\./)
    // Load our xml string into DOM
    var xmlDocument = null;
    if(window.DOMParser) {
        //browser supports DOMParser
        var parser = new DOMParser();
        xmlDocument = parser.parseFromString(xmlString, "text/xml");
    } else if(window.ActiveXObject) {
        //IE
        xmlDocument = new ActiveXObject("Microsoft.XMLDOM");
        xmlDocument.async="false";
        xmlDocument.loadXML(xmlString);
    } else {
        return null;
    }
    return xmlDocument;
};
/**
 * A simple extension of the BaseFactory class.
 * It is capable of representing a domNode as a basic tree-like structure.
 */
Ext.define('portal.layer.querier.wfs.factories.SimpleFactory', {
    extend : 'portal.layer.querier.wfs.factories.BaseFactory',

    /**
     * Accepts all portal.layer.querier.wfs.factories.BaseFactory configuration options
     */
    constructor : function(cfg) {
        this.callParent(arguments);
    },

    /**
     * The simple node supports EVERY type of node as it only displays simplistic information
     * and is intended to be the 'catch all' factory for parsing nodes that have no specific
     * factory written
     */
    supportsNode : function(domNode) {
        return true;
    },


    /**
     * Generates a simple tree panel that represents the specified node
     */
    parseNode : function(domNode, wfsUrl) {
        // Turn our DOM Node in an ExtJS Tree
        var rootNode = this._createTreeNode(domNode);
        var gmlId = portal.util.xml.SimpleXPath.evaluateXPathString(domNode, '@gml:id');
        var sf = this;
        this._parseXmlTree(domNode, rootNode);
        rootNode.expanded = true;

        // Continuously expand child nodes until we hit a node with
        // something "interesting" defined as a node with more than 1 child
        if (rootNode.children.length == 1) {
            var childNode = rootNode.children[0];
            while (childNode) {
                childNode.expanded = true;

                if (childNode.children.length > 1) {
                    break;
                } else {
                    childNode = childNode.children[0];
                }
            }
        }

        var panelConfig = {
            layout : 'fit',
            tabTitle : gmlId,
            height: 300,
            items : [{
                xtype : 'treepanel',
                autoScroll : true,
                rootVisible : true,
                root : rootNode
            }],
            buttons : [{
                xtype : 'button',
                text : 'Download Feature',
                iconCls : 'download',
                handler : function() {
                    var getXmlUrl = sf._makeFeatureRequestUrl(wfsUrl, domNode.nodeName, gmlId);
                    portal.util.FileDownloader.downloadFile('downloadGMLAsZip.do',{
                        serviceUrls : getXmlUrl
                    });
                }
            }]
        };

        return Ext.create('portal.layer.querier.BaseComponent', panelConfig);
    },

    /**
     * This is for creating a Node Objects from a DOM Node in the form
     * {
     *  text : String
     *  leaf : Boolean
     * }
     */
    _createTreeNode : function(documentNode) {
        var treeNode = null;

        // We have a leaf
        if (portal.util.xml.SimpleDOM.isLeafNode(documentNode)) {
            var textContent = portal.util.xml.SimpleDOM.getNodeTextContent(documentNode);

            treeNode = {
                text : documentNode.tagName + " = " + textContent,
                children : [],
                leaf: true
            };
        } else { // we have a parent node
            var parentName = documentNode.tagName;
            if (documentNode.attributes.length > 0) {
                parentName += '(';
                for ( var i = 0; i < documentNode.attributes.length; i++) {
                    parentName += ' ' + documentNode.attributes[i].nodeName +
                                  '=' + documentNode.attributes[i].value;
                }
                parentName += ')';
            }
            treeNode = {
                text : parentName,
                children : [],
                leaf: true
            };
        }

        return treeNode;
    },

    /**
     * Given a DOM tree starting at xmlDocNode, this function returns the
     * equivelant tree in ExtJs Tree Nodes
     */
    _parseXmlTree : function(xmlDocNode, treeNode) {
        var nodes = [];
        Ext.each(xmlDocNode.childNodes, function(docNodeChild) {
            if (docNodeChild.nodeType == portal.util.xml.SimpleDOM.XML_NODE_ELEMENT) {
                var treeChildNode = this._createTreeNode(docNodeChild);
                treeNode.leaf = false;
                treeNode.children.push(treeChildNode);
                nodes.push(treeNode);
                this._parseXmlTree(docNodeChild, treeChildNode);
            }
        }, this);

        return nodes;
    }
});/**
 * Utility functions for providing XPath functionality with a single API for most browsers.
 *
 * Supported functionality is only a subset of XPath (although you may get additional functionality
 * depending on browser).
 *      + Support for Axes is limited
 *      + Support for predicates is limited to only integer positions (no conditionals)
 *
 * Currently supported Browsers
 * IE 6-9
 * Mozilla Firefox 3+
 * Google Chrome
 */
Ext.ns('portal.util.xml.SimpleXPath');

//Constants
portal.util.xml.SimpleXPath.XPATH_STRING_TYPE = window.XPathResult ? XPathResult.STRING_TYPE : 0;
portal.util.xml.SimpleXPath.XPATH_UNORDERED_NODE_ITERATOR_TYPE = window.XPathResult ? XPathResult.UNORDERED_NODE_ITERATOR_TYPE : 1;

/**
 * A wrapper around the DOM defined Document.evaluate function
 *
 * Because not every browser supports document.evaluate we need to have a pure javascript
 * backup in place
 */
portal.util.xml.SimpleXPath.evaluateXPath = function(document, domNode, xPath, resultType) {
    if (document.evaluate) {
        return document.evaluate(xPath, domNode, document.createNSResolver(domNode), resultType, null);
    } else {
        //This gets us a list of dom nodes
        var matchingNodeArray = XPath.selectNodes(xPath, domNode);
        if (!matchingNodeArray) {
            matchingNodeArray = [];
        }

        //we need to turn that into an XPathResult object (or an emulation of one)
        switch(resultType) {
        case portal.util.xml.SimpleXPath.XPATH_STRING_TYPE:
            var stringValue = null;
            if (matchingNodeArray.length > 0) {
                stringValue = portal.util.xml.SimpleDOM.getNodeTextContent(matchingNodeArray[0]);
            }

            return {
                stringValue : stringValue
            };
        case portal.util.xml.SimpleXPath.XPATH_UNORDERED_NODE_ITERATOR_TYPE:
            return {
                _arr : matchingNodeArray,
                _i : 0,
                iterateNext : function() {
                    if (this._i >= this._arr.length) {
                        return null;
                    } else  {
                        return this._arr[this._i++];
                    }
                }
            };

        }

        throw 'Unrecognised resultType';
    }
};


/**
 * Evaluates an XPath which will return an array of W3C DOM nodes
 */
portal.util.xml.SimpleXPath.evaluateXPathNodeArray = function(domNode, xPath) {
    var document = domNode.ownerDocument;
    var xpathResult = null;
    try {
        xpathResult = portal.util.xml.SimpleXPath.evaluateXPath(document, domNode, xPath, portal.util.xml.SimpleXPath.XPATH_UNORDERED_NODE_ITERATOR_TYPE);
    } catch(err) {
        return [];
    }
    var matchingNodes = [];

    var matchingNode = xpathResult.iterateNext();
    while (matchingNode) {
        matchingNodes.push(matchingNode);
        matchingNode = xpathResult.iterateNext();
    }

    return matchingNodes;
};

/**
 * Evaluates an Xpath for returning a string
 */
portal.util.xml.SimpleXPath.evaluateXPathString = function(domNode, xPath) {
    var document = domNode.ownerDocument;
    var xpathResult = portal.util.xml.SimpleXPath.evaluateXPath(document, domNode, xPath, portal.util.xml.SimpleXPath.XPATH_STRING_TYPE);
    return xpathResult.stringValue;
};/**
 * Represents a size of some mapping component
 */
Ext.define('portal.map.Size', {

    config : {
        /**
         * Number - the width in pixels
         */
        width : 0,
        /**
         * Number - the height in pixels
         */
        height : 0
    },

    /**
     * Accepts the following
     *
     * width : Number - the width in pixels
     * height : Number - the height in pixels
     */
    constructor : function(cfg) {
        this.callParent(arguments);
        this.setWidth(cfg.width);
        this.setHeight(cfg.height);
    }
});/**
 * Represents information about a point on a single Tile from a Map
 */
Ext.define('portal.map.TileInformation', {

    config : {
        width : 0,  //Number - width of the tile in pixels
        height : 0, //Number - height of the tile in pixels
        offset : {  //Object - The point location within the tile being queried
            x : 0,  //Number - offset in x direction
            y : 0   //Number - offset in y direction
        },
        tileBounds : null //portal.util.BBox - bounds of the tile at the time of query
    },

    constructor : function(cfg) {
        this.callParent(arguments);

        this.setWidth(cfg.width);
        this.setHeight(cfg.height);
        this.setOffset(cfg.offset);
        this.setTileBounds(cfg.tileBounds);
    }
});/**
 * A utility function for representing a function that hasn't been implemented yet. Any calls to this function
 * will result in console error logs and exceptions being thrown
 */
Ext.define('portal.util.UnimplementedFunction', {
    singleton: true
}, function() {
    portal.util.UnimplementedFunction = function() {
        if (window.console) {
            console.error('The following function is calling an Unimplemented function with Arguments: ', arguments);
            console.error(arguments.callee.caller.toString());
        }
        throw 'NotImplemented';
    };
});/**
 * Utility functions for manipulating URLs
 */
Ext.define('portal.util.URL', {
    singleton: true
}, function() {
    if (!Ext.isString(window.WEB_CONTEXT)) {
        window.WEB_CONTEXT = '/' + window.location.pathname.substring(1).split("/")[0];
    }

    /**
     * The base URL for this page (with trailing '/') as a String variable
     *
     * eg - http://your.website/context/
     */
    portal.util.URL.base = window.location.protocol + "//" + window.location.host + WEB_CONTEXT + "/";
    
    /**
     * Given a URL in the form http://example.com:80/path?k=v
     * 
     * Return just the host (sans port number) i.e. 'example.com' or an empty string
     * if the host couldn't be parsed
     * 
     * @param url The URL as a String
     */
    portal.util.URL.extractHost = function(url) {
        var urlRegexp = /^(?:ftp|https?):\/\/(?:[^@:\/]*@)?([^:\/]+)/;
        var match = urlRegexp.exec(url);
        if (!match || match.length < 2) {
            return '';
        }
        return match[1];
    },
    
    
    /**
     * Given a URL in the form http://example.com:80/a/b/c
     * 
     * Return just the host (and port number) i.e. 'example.com' and subdirectory
     * 
     * 
     * @param url The URL as a String
     * @param OPTIONAL number of subDir to return
     */
    portal.util.URL.extractHostNSubDir = function(url,numberOfSubDir) {
        var a = document.createElement('a');
        a.href = url;
        var pathname = (a.pathname.charAt(0) == "/") ? a.pathname : "/" + a.pathname;
        var pathArray=pathname.split("/");
        
        var hostname = a.hostname;
        
        if(numberOfSubDir && pathArray.length > 1){
            
            for(var i=1; i <= numberOfSubDir && i < pathArray.length ; i++){
                hostname =  hostname + "/" + pathArray[i];
            }            
            
            return hostname;
        }
        
        
        
        return a.hostname;
    }
    
});Ext.define('portal.layer.downloader.coverage.WCSDownloader', {
    extend: 'portal.layer.downloader.Downloader',

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    downloadData : function(layer, resources, renderedFilterer, currentFilterer) {
        var wcsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WCS);
        var ftpResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.FTP);
        var ftpURL = ftpResources.length > 0 ? ftpResources[0].get('url') : '';
        this.showWCSDownload(wcsResources[0].get('url'), wcsResources[0].get('name'), layer.get('renderer').map, ftpURL);
    },// end downloadData

  //rec must be a record from the response from the describeCoverage.do handler
  //The north, south, east and west reference the EPSG:4326 latitudes/longitudes that represent the current visible section of the map
  //Alternatively pass a GLatLng bounds as the north parameter
    showWCSDownload : function (serviceUrl, layerName, map, ftpURL) {
        var currentVisibleBounds = map.getVisibleMapBounds();
        me = this;
        portal.util.Ajax.request({
            url         : 'describeCoverage.do',
            timeout     : 180000,
            params      : {
                serviceUrl      : serviceUrl,
                layerName       : layerName
            },
            //This gets called if the server returns an error
            failure     : function(message) {
                Ext.Msg.alert('Error Describing Coverage', 'ERROR: ' + message);
            },
            //This gets called if the server returned HTTP 200 (the actual response object could still be bad though)
            success : function(data, message) {
                if (data.length === 0) {
                    Ext.Msg.alert('Error Describing Coverage', 'The URL ' + serviceUrl + ' returned no parsable DescribeCoverage records');
                    return;
                }

                //We only parse the first record (as there should only be 1)
                var rec = data[0];
                var interpolationAllowed = rec.supportedInterpolations.length === 0 || rec.supportedInterpolations[0] !== 'none';

                if (!rec.temporalDomain) {
                    rec.temporalDomain = [];
                }
                if (!rec.spatialDomain) {
                    rec.spatialDomain = {envelopes : [], rectifiedGrid : null};
                }

                //Add a proper date time method to each temporal domain element
                for (var i = 0; i < rec.temporalDomain.length; i++) {
                    if (rec.temporalDomain[i].type === 'timePosition') {
                        if (rec.temporalDomain[i].timePosition.time) {
                            rec.temporalDomain[i].timePosition = new Date(rec.temporalDomain[i].timePosition);
                        }
                    } else if (rec.temporalDomain[i].type === 'timePeriod') {
                        if (rec.temporalDomain[i].beginPosition.time) {
                            rec.temporalDomain[i].beginPosition = new Date(rec.temporalDomain[i].beginPosition);
                        }
                        if (rec.temporalDomain[i].endPosition.time) {
                            rec.temporalDomain[i].endPosition = new Date(rec.temporalDomain[i].endPosition);
                        }
                    }
                }

                //Preprocess our list of strings into a list of lists
                for (var i = 0; i < rec.supportedRequestCRSs.length; i++)  {
                    rec.supportedRequestCRSs[i] = [rec.supportedRequestCRSs[i]];
                }
                for (var i = 0; i < rec.supportedResponseCRSs.length; i++) {
                    rec.supportedResponseCRSs[i] = [rec.supportedResponseCRSs[i]];
                }
                for (var i = 0; i < rec.supportedFormats.length; i++) {
                    rec.supportedFormats[i] = [rec.supportedFormats[i]];
                }

                //This list will be populate with each field set (in accordance to domains we have received)
                var fieldSetsToDisplay = [{
                    xtype   :'hidden',
                    name    :'layerName', //name of the field sent to the server
                    value   : layerName  //value of the field
                },{
                    xtype   :'hidden',
                    name    :'serviceUrl', //name of the field sent to the server
                    value   : serviceUrl  //value of the field
                }];

                //Completely disables a field set and stops its values from being selected by the "getValues" function
                //This function is recursive over fieldset objects
                var setFieldSetDisabled = function (fieldSet, disabled, depth) {
                    if (depth === undefined) {
                        depth = 0;
                    }

                    //IE workaround
                    //VT: I don't think we need this any. setting the disabling the fieldset from this level
                    // disable the checkbox associted with the fieldset as well. Can be seen from the ASTER WMS Download
                    //once bounding box is check, it cannot be unchecked anymore. disabling this works for all three browsers.
                    //if (!Ext.isIE || depth != 0) {
                    //    fieldSet.setDisabled(disabled);
                    //}

                    for (var i = 0; i < fieldSet.items.length; i++) {
                        var item = fieldSet.items.get(i);

                        if (item.getXType() == 'fieldset') {
                            setFieldSetDisabled(item, disabled, depth + 1);
                        } else {
                            item.setDisabled(disabled);
                        }
                    }
                };
                
               
                currentVisibleBounds.eastBoundLongitude = portal.util.BBox.datelineCorrection(currentVisibleBounds.eastBoundLongitude,"EPSG:4326")
                

                //Contains the fields for bbox selection
                if (rec.spatialDomain.envelopes.length > 0) {
                    fieldSetsToDisplay.push(new Ext.form.FieldSet({
                        id              : 'bboxFldSet',
                        title           : 'Bounding box constraint',
                        checkboxToggle  : true,
                        checkboxName    : 'usingBboxConstraint',
                        defaultType     : 'textfield',
                        bodyStyle       : 'padding: 0 0 0 50px',
                        listeners: {
                            expand : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, false);
                                }
                            },
                            collapse : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, true);
                                }
                            }
                        },
                        items:[{
                            id              : 'northBoundLatitude',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Latitude (North)',
                            value           : currentVisibleBounds.northBoundLatitude,
                            name            : 'northBoundLatitude',
                            allowBlank      : false,
                            anchor          : '-50'
                        },{
                            id              : 'southBoundLatitude',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Latitude (South)',
                            value           : currentVisibleBounds.southBoundLatitude,
                            name            : 'southBoundLatitude',
                            allowBlank      : false,
                            anchor          : '-50'
                        },{
                            id              : 'eastBoundLongitude',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Longitude (East)',
                            value           : currentVisibleBounds.eastBoundLongitude,
                            name            : 'eastBoundLongitude',
                            allowBlank      : false,
                            anchor          : '-50'
                        },{
                            id              : 'westBoundLongitude',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Longitude (West)',
                            value           : currentVisibleBounds.westBoundLongitude,
                            name            : 'westBoundLongitude',
                            allowBlank      : false,
                            anchor          : '-50'
                        }]
                    }));
                }

                //Contains the fields for temporal instance selection
                if (rec.temporalDomain.length > 0 && rec.temporalDomain[0].type === 'timePosition') {
                    var checkBoxList = [];

                    for (var i = 0; i < rec.temporalDomain.length; i++) {
                        checkBoxList.push({
                            boxLabel    : rec.temporalDomain[i].timePosition.format('Y-m-d H:i:s T'),
                            name        : 'timePosition',
                            inputValue  : rec.temporalDomain[i].timePosition.format('Y-m-d H:i:s T') + 'GMT'
                        });
                    }

                    fieldSetsToDisplay.push(new Ext.form.FieldSet({
                        id              : 'timePositionFldSet',
                        title           : 'Time Position Constraints',
                        checkboxToggle  : true,
                        checkboxName    : 'usingTimePositionConstraint',
                        defaultType     : 'textfield',
                        bodyStyle       : 'padding: 0 0 0 50px',
                        allowBlank      : false,
                        listeners: {
                            expand      : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, false);
                                }
                            },
                            collapse : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, true);
                                }
                            }
                        },
                        items:{
                            // Use the default, automatic layout to distribute the controls evenly
                            // across a single row
                            xtype: 'radiogroup',
                            fieldLabel: 'Time Positions',
                            columns: 1,
                            items: checkBoxList
                        }
                    }));
                }

                //Contains the fields for temporal range selection
                //This will be hidden if there is no temporalRange in the temporalDomain
                if (rec.temporalDomain.length > 0 && rec.temporalDomain[0].type === 'timePeriod') {
                    fieldSetsToDisplay.push(new Ext.form.FieldSet({
                        id              : 'timePeriodFldSet',
                        title           : 'Time Period Constraints',
                        checkboxToggle  : true,
                        checkboxName    : 'usingTimePeriodConstraint',
                        defaultType     : 'textfield',
                        bodyStyle       : 'padding: 0 0 0 50px',
                        listeners: {
                            expand      : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, false);
                                }
                            },
                            collapse : {
                                scope: this,
                                fn : function(panel, anim) {
                                    setFieldSetDisabled(panel, true);
                                }
                            }
                        },
                        items:[{
                            id              : 'dateFrom',
                            xtype           : 'datefield',
                            fieldLabel      : 'Date From',
                            name            : 'dateFrom',
                            format          : 'Y-m-d',
                            allowBlank      : false,
                            value           : rec.temporalDomain[0].beginPosition,
                            anchor          : '-50',
                            submitValue     : false         // We don't submit here as we perform some custom parsing for the query
                        },{
                            id              : 'timeFrom',
                            xtype           : 'timefield',
                            fieldLabel      : 'Time From',
                            name            : 'timeFrom',
                            format          : 'H:i:s',
                            allowBlank      : false,
                            value           : rec.temporalDomain[0].beginPosition.format('H:i:s'),
                            anchor          : '-50',
                            submitValue     : false         // We don't submit here as we perform some custom parsing for the query
                        },{
                            id              : 'dateTo',
                            xtype           : 'datefield',
                            fieldLabel      : 'Date To',
                            name            : 'dateTo',
                            format          : 'Y-m-d',
                            allowBlank      : false,
                            value           : rec.temporalDomain[0].endPosition,
                            anchor          : '-50',
                            submitValue     : false         // We don't submit here as we perform some custom parsing for the query
                        },{
                            id              : 'timeTo',
                            xtype           : 'timefield',
                            fieldLabel      : 'Time To',
                            name            : 'timeTo',
                            format          : 'H:i:s',
                            allowBlank      : false,
                            value           : rec.temporalDomain[0].endPosition.format('H:i:s'),
                            anchor          : '-50',
                            submitValue     : false         // We don't submit here as we perform some custom parsing for the query
                        }]
                    }));
                }

                //lets add our list (if any) of axis constraints that can be applied to the download
                var axisConstraints = [];
                if (rec.rangeSet.axisDescriptions && rec.rangeSet.axisDescriptions.length > 0) {
                    for (var i = 0; i < rec.rangeSet.axisDescriptions.length; i++) {
                        var constraint = rec.rangeSet.axisDescriptions[i];

                        //if this constraint has a defined set of values, lets add it
                        if (constraint.values && constraint.values.length > 0) {
                            //Only support singleValue constraints for the moment
                            if (constraint.values[0].type === 'singleValue') {
                                constraint.componentId = 'axis-constraint-' + i;
                                constraint.type = 'singleValue';
                                constraint.checkBoxName = 'axis-constraint-' + i;
                                constraint.checkBoxId = 'axis-constraint-' + i + '-chkboxgrp';

                                var checkBoxList = [];

                                for (var j = 0; j < constraint.values.length; j++) {
                                    checkBoxList.push({
                                        id          : constraint.checkBoxName + '-' + j,
                                        boxLabel    : constraint.values[j].value,
                                        name        : constraint.checkBoxName,
                                        inputValue  : constraint.values[j].value//,
                                        //submitValue : false // Can't use submitValue: false with radio's (it breaks them)
                                    });
                                }

                                fieldSetsToDisplay.push(new Ext.form.FieldSet({
                                    id              : constraint.componentId,
                                    title           : "Parameter '" + constraint.label + "' Constraints",
                                    checkboxToggle  : true,
                                    defaultType     : 'textfield',
                                    bodyStyle       : 'padding: 0 0 0 50px',
                                    allowBlank      : false,
                                    listeners: {
                                        expand      : {
                                            scope: this,
                                            fn : function(panel, anim) {
                                                setFieldSetDisabled(panel, false);
                                            }
                                        },
                                        collapse : {
                                            scope: this,
                                            fn : function(panel, anim) {
                                                setFieldSetDisabled(panel, true);
                                            }
                                        }
                                    },
                                    items: {
                                        // Use the default, automatic layout to distribute the controls evenly
                                        // across a single row
                                        id              : constraint.checkBoxId,
                                        xtype           : 'radiogroup',
                                        constraintName  : constraint.name,
                                        fieldLabel      : constraint.label,
                                        columns         : 3,
                                        items           : checkBoxList,
                                        submitValue     : false
                                    }
                                }));

                                axisConstraints.push(constraint);
                            }
                        }
                    }
                }

                fieldSetsToDisplay.push(new Ext.form.FieldSet({
                    id              : 'outputDimSpec',
                    title           : 'Output dimension specifications',
                    //defaultType     : 'radio', // each item will be a radio button
                    items           : [{
                        id              : 'radiogroup-outputDimensionType',
                        xtype            : 'radiogroup',
                        columns          : 2,
                        fieldLabel       : 'Type',
                        items           : [{
                            id           : 'radioHeightWidth',
                            boxLabel         : 'Width/Height',
                            name            : 'outputDimensionsType',
                            inputValue       : 'widthHeight',
                            checked         : true,
                            listeners       : {
                                check       : function (chkBox, checked) {
                                    var fldSet = Ext.getCmp('widthHeightFieldSet');
                                    fldSet.setVisible(checked);
                                    setFieldSetDisabled(fldSet, !checked);
                                }
                            }
                        },{
                            id              : 'radioResolution',
                            boxLabel     : 'Resolution',
                            name            : 'outputDimensionsType',
                            inputValue       : 'resolution',
                            checked         : false,
                            listeners       : {
                                check : function (chkBox, checked) {
                                    var fldSet = Ext.getCmp('resolutionFieldSet');
                                    fldSet.setVisible(checked);
                                    setFieldSetDisabled(fldSet, !checked);
                                }
                            }
                        }]
                    },{
                        id              : 'widthHeightFieldSet',
                        xtype           : 'fieldset',
                        hideLabel       : true,
                        hideBorders     : true,
                        items           : [{
                            id              : 'outputWidth',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Width',
                            value           : '256',
                            name            : 'outputWidth',
                            anchor          : '-50',
                            allowBlank      : false,
                            allowDecimals   : false,
                            allowNegative   : false
                        },{
                            id              : 'outputHeight',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Height',
                            value           : '256',
                            name            : 'outputHeight',
                            anchor          : '-50',
                            allowBlank      : false,
                            allowDecimals   : false,
                            allowNegative   : false
                        }]
                    },{
                        id              : 'resolutionFieldSet',
                        xtype           : 'fieldset',
                        hideLabel       : true,
                        hideBorders     : true,
                        disabled        : true,
                        hidden          : true,
                        items           : [{
                            id              : 'outputResX',
                            xtype           : 'numberfield',
                            fieldLabel      : 'X Resolution',
                            value           : '1',
                            name            : 'outputResX',
                            anchor          : '-50',
                            allowBlank      : false,
                            disabled        : true,
                            allowNegative   : false
                        },{
                            id              : 'outputResY',
                            xtype           : 'numberfield',
                            fieldLabel      : 'Y Resolution',
                            value           : '1',
                            name            : 'outputResY',
                            anchor          : '-50',
                            allowBlank      : false,
                            disabled        : true,
                            allowNegative   : false
                        }]
                    }]
                }));

                var downloadFormatStore = new Ext.data.ArrayStore({
                    fields : ['format'],
                    data   : rec.supportedFormats
                });
                var responseCRSStore = new Ext.data.ArrayStore({
                    fields : ['crs'],
                    data   : rec.supportedResponseCRSs
                });
                var requestCRSStore = new Ext.data.ArrayStore({
                    fields : ['crs'],
                    data   : rec.supportedRequestCRSs
                });

                var nativeCrsString = '';
                if (rec.nativeCRSs && rec.nativeCRSs.length > 0) {
                    for (var i = 0; i < rec.nativeCRSs.length; i++) {
                        if (nativeCrsString.length > 0) {
                            nativeCrsString += ',';
                        }
                        nativeCrsString += rec.nativeCRSs[i];
                    }
                }

                //Contains all "Global" download options
                fieldSetsToDisplay.push(new Ext.form.FieldSet({
                    id              : 'downloadOptsFldSet',
                    title           : 'Download options',
                    defaultType     : 'textfield',
                    bodyStyle       : 'padding: 0 0 0 50px',
                    items: [{
                        xtype           : 'textfield',
                        id              : 'nativeCrs',
                        name            : 'nativeCrs',
                        fieldLabel      : 'Native CRS',
                        emptyText       : 'Not specified',
                        value           : nativeCrsString,
                        disabled        : true,
                        anchor          : '-50',
                        submitValue     : false
                    },{
                        xtype           : 'combo',
                        id              : 'inputCrs',
                        name            : 'inputCrs',
                        fieldLabel      : 'Reference System',
                        labelAlign      : 'right',
                        emptyText       : '',
                        forceSelection  : true,
                        allowBlank      : false,
                        mode            : 'local',
                        store           : requestCRSStore,
                        typeAhead       : true,
                        triggerAction   : 'all',
                        displayField    : 'crs',
                        anchor          : '-50',
                        valueField      : 'crs',
                        listeners       : {
                            'render'    : function(cbx){
                                if(requestCRSStore.getTotalCount()>0){
                                    cbx.setValue(requestCRSStore.getAt(0).get('crs'));
                                }
                            }
                        }
                    },{
                        xtype           : 'combo',
                        id              : 'downloadFormat',
                        name            : 'downloadFormat',
                        fieldLabel      : 'Format',
                        labelAlign      : 'right',
                        forceSelection  : true,
                        mode            : 'local',
                        triggerAction   : 'all',
                        store           : downloadFormatStore,
                        typeAhead       : true,
                        allowBlank      : false,
                        displayField    : 'format',
                        anchor          : '-50',
                        valueField      : 'format',
                        listeners       : {
                            'render'    : function(cbx){
                                for(i = 0; i < downloadFormatStore.getTotalCount() ; i++) {
                                    if(downloadFormatStore.getAt(i).get('format')==='NetCDF3'){
                                        cbx.setValue(downloadFormatStore.getAt(i).get('format'));
                                    }
                                }
                            }
                        }
                    },{
                        xtype           : 'combo',
                        id              : 'outputCrs',
                        name            : 'outputCrs',
                        fieldLabel      : 'Output CRS',
                        labelAlign      : 'right',
                        emptyText       : '',
                        forceSelection  : true,
                        mode            : 'local',
                        triggerAction   : 'all',
                        store           : responseCRSStore,
                        typeAhead       : true,
                        displayField    : 'crs',
                        anchor          : '-50',
                        valueField      : 'crs'
                    }]
                }));

                // Dataset download window
                var win = new Ext.Window({
                    id              : 'wcsDownloadWindow',
                    border          : true,
                    layout          : 'fit',
                    resizable       : true,
                    modal           : true,
                    plain           : false,
                    buttonAlign     : 'right',
                    title           : 'Layer: '+ layerName,
                    height          : 600,
                    width           : 500,
                    items:[{
                        xtype   : 'panel',
                        layout  : 'fit',
                        autoScroll : true,
                        bodyStyle   : 'background-color: transparent;',
                        items : [{
                            // Bounding form
                            id      :'wcsDownloadFrm',
                            xtype   :'form',
                            layout  :'form',
                            frame   : false,
                            autoHeight : true,
                            autoWidth   : true,
                            axisConstraints : axisConstraints,  //This is stored here for later validation usage

                            // these are applied to columns
                            defaults:{
                                xtype: 'fieldset', layout: 'form'
                            },

                            // fieldsets
                            items   : fieldSetsToDisplay
                        }],
                        buttons:[{
                            xtype: 'button',
                            text: 'Download',
                            handler: function() {

                                if (!me.validateWCSInfoWindow()) {
                                    return;
                                }

                                var downloadUrl = './downloadWCSAsZip.do?' + me.getWCSInfoWindowDownloadParameters() +
                                    (ftpURL ? '&' + Ext.Object.toQueryString({ftpURL: ftpURL}) : '');
                                
                                portal.util.FileDownloader.downloadFile(downloadUrl);
                            }
                        }]
                    }]
                });

                win.show();
            }
        });
    },

    getWCSInfoWindowDownloadParameters : function () {
        var win = Ext.getCmp('wcsDownloadFrm');
        var params = win.getForm().getValues(true);
        var customParams = '';

        //Custom handling for time periods
        var dateFrom = Ext.getCmp('dateFrom');
        var dateTo = Ext.getCmp('dateTo');
        var timeFrom = Ext.getCmp('timeFrom');
        var timeTo = Ext.getCmp('timeTo');
        if (dateFrom && dateTo && timeFrom && timeTo) {
            if (!dateFrom.disabled && !dateTo.disabled && !timeFrom.disabled && !timeTo.disabled) {
                var dateTimeFrom = dateFrom.getValue().format('Y-m-d') + ' ' + timeFrom.getValue();
                var dateTimeTo = dateTo.getValue().format('Y-m-d') + ' ' + timeTo.getValue();

                customParams += '&timePeriodFrom=' + escape(dateTimeFrom);
                customParams += '&timePeriodTo' + escape(dateTimeTo);
            }
        }

        //Get the custom parameter constraints
        var axisConstraints = win.initialConfig.axisConstraints;
        if (axisConstraints && axisConstraints.length > 0) {
            for (var i = 0; i < axisConstraints.length; i++) {
                if (axisConstraints[i].type === 'singleValue') {
                    var checkBoxGrp = Ext.getCmp(axisConstraints[i].checkBoxId);

                    //This is for radio group selection
                    var selection = checkBoxGrp.getValue();
                    if (selection && !selection.disabled) {
                        var constraintName = checkBoxGrp.initialConfig.constraintName;
                        var constraintValue = selection.initialConfig.inputValue;

                        customParams += '&customParamValue=' + escape(constraintName + '=' + constraintValue);
                    }

                  /* This is for checkbox group selection
                  var selections = checkBoxGrp.getValue();

                  for (var j = 0; selections && j < selections.length; j++) {
                      if (!selections[j].disabled) {
                          var constraintName = checkBoxGrp.initialConfig.constraintName;
                          var constraintValue = selections[j].initialConfig.inputValue;

                          customParams += '&customParamValue=' + escape(constraintName + '=' + constraintValue);
                      }
                  }*/
                } else if (axisConstraints[i].type === 'interval') {
                    //TODO: Intervals
                }
            }
        }

        return params + customParams;
    },


    validateWCSInfoWindow : function () {
        var win = Ext.getCmp('wcsDownloadFrm');
        var form = win.getForm();
        var timePositionFieldSet = Ext.getCmp('timePositionFldSet');
        var timePeriodFieldSet = Ext.getCmp('timePeriodFldSet');
        var bboxFieldSet = Ext.getCmp('bboxFldSet');

        if (!form.isValid()) {
            Ext.Msg.alert('Invalid Fields','One or more fields are invalid');
            return false;
        }

        var usingTimePosition = timePositionFieldSet && !timePositionFieldSet.collapsed;
        var usingTimePeriod = timePeriodFieldSet && !timePeriodFieldSet.collapsed;
        var usingBbox = bboxFieldSet && !bboxFieldSet.collapsed;

        if (!usingBbox && !(usingTimePosition || usingTimePeriod)) {
            Ext.Msg.alert('No Constraints', 'You must specify at least one spatial or temporal constraint');
            return false;
        }

        if (usingTimePosition && usingTimePeriod) {
            Ext.Msg.alert('Too many temporal', 'You may only specify a single temporal constraint');
            return false;
        }

        return true;
    }
});/**
 * Querier for WCS instances
 */
Ext.define('portal.layer.querier.coverage.WCSQuerier', {
    extend: 'portal.layer.querier.Querier',

    constructor: function(config){
        this.callParent(arguments);
    },
    _generateErrorComponent : function(message) {
        return Ext.create('portal.layer.querier.BaseComponent', {
            html: Ext.util.Format.format('<p class="centeredlabel">{0}</p>', message)
        });
    },
    statics : {
        _parseArrayToString : function(arr, contentFunc){

            if (!arr || arr.length === 0) {
                return '';
            }

            if (!contentFunc) {
                contentFunc = function (item) {
                    return item;
                };
            }

            var description = '';
            for (var i = 0; i < arr.length; i++) {
                description += contentFunc(arr[i]);
                if (arr[i+1]) {
                    description += '<br/>';
                }
            }
            return description;
        }
    },



    query : function(queryTarget, callback) {
        var allOnlineResources = queryTarget.get('cswRecord').get('onlineResources');
        var opendapResources = portal.csw.OnlineResource.getFilteredFromArray(allOnlineResources, portal.csw.OnlineResource.OPeNDAP);

        portal.util.Ajax.request({
            url: 'describeCoverage.do',
            timeout : 180000,
            params      : {
                serviceUrl      : queryTarget.get('onlineResource').get('url'),
                layerName       : queryTarget.get('onlineResource').get('name')
                //cswRecord       : queryTarget.get('cswRecord')
            },
            scope : this,
            callback : function(success, data, message) {
                if (!success || !data) { //LJ: AUS-2598 ASTER mask hanging when server is down.
                    callback(this, [this._generateErrorComponent('There was a problem when looking up the coverage: ' + message)], queryTarget);
                    return;
                }
                
                if(success) {
                    var record = data[0];
                    var spatialFunc=function(item) {
                        var s = '';
                        if (item.type === 'Envelope' || item.type === 'EnvelopeWithTimePeriod') {
                            s += '[';
                            s += 'E' + item.eastBoundLongitude + ', ';
                            s += 'W' + item.westBoundLongitude + ', ';
                            s += 'N' + item.northBoundLatitude + ', ';
                            s += 'S' + item.southBoundLatitude;
                            s   += ']';
                        } else {
                            s += item.type;
                        }

                        return s;
                    };

                    var cmp= Ext.create('portal.layer.querier.BaseComponent',{  
                        overrideInfoWindowSize : {
                            width : 600,
                            height : 400
                        },
                        layout : 'fit',
                        items : [{
                            xtype : 'fieldset',
                            border : false,
                            autoScroll:true,
                            labelWidth: 75,
                            layout:'anchor',

                            items : [{
                                xtype : 'displayfield',
                                fieldLabel : 'name',
                                value : record.name
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'Description',
                                value : record.description
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'Label',
                                value : record.label
                            },{
                                xtype : 'displayfield',
                                fieldLabel : "Supported Request CRSs",
                                value : portal.layer.querier.coverage.WCSQuerier._parseArrayToString(record.supportedRequestCRSs)
                            },{
                                xtype : 'displayfield',
                                fieldLabel : "Supported Response CRSs",
                                value : portal.layer.querier.coverage.WCSQuerier._parseArrayToString(record.supportedResponseCRSs)
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'Supported Formats',
                                value : portal.layer.querier.coverage.WCSQuerier._parseArrayToString(record.supportedFormats)
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'Supported Interpolation',
                                value : portal.layer.querier.coverage.WCSQuerier._parseArrayToString(record.supportedInterpolations)
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'WMS URLs',
                                value : queryTarget.get('onlineResource').get('name') + '-' + queryTarget.get('onlineResource').get('url')
                            },{
                                xtype : 'displayfield',
                                fieldLabel : 'Spatial Domain',
                                value : portal.layer.querier.coverage.WCSQuerier._parseArrayToString(record.spatialDomain.envelopes,spatialFunc)
                            }]
                        }],
                        buttonAlign : 'right',
                        buttons : [{
                            text : 'Download WCS',
                            iconCls : 'download',
                            handler : function() {
                                var layer=queryTarget.get('layer');
                                var downloader=layer.get('downloader');
                                var renderedFilterer = layer.get('filterer').clone();
                                downloader.downloadData(layer, layer.getAllOnlineResources(), renderedFilterer, undefined);
                            }
                        },{
                            text : 'Download OPeNDAP',
                            iconCls : 'download',
                            hidden : opendapResources.length === 0,
                            handler : function() {
                                var opendapDownloader = Ext.create('portal.layer.downloader.coverage.OPeNDAPDownloader', {map : this.map});
                                opendapDownloader.downloadData(queryTarget.get('layer'), allOnlineResources, null, null);
                            }
                        }]
                    });
                    callback(this, [cmp], queryTarget);
                }
            }
        });


    }

});/**
 * A downloader that creates an Ext.Window specialised into showing a
 * dialog for the user to download features from a WFS in a zip file
 */
Ext.define('portal.layer.downloader.wfs.WFSDownloader', {
    extend: 'portal.layer.downloader.Downloader',

    statics : {
        DOWNLOAD_CURRENTLY_VISIBLE : 1,
        DOWNLOAD_ORIGINALLY_VISIBLE : 2,
        DOWNLOAD_ALL : 3
    },

    currentTooltip : null,

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    /**
     * An implementation of an abstract method, see parent method for details
     *
     * layer - portal.layer.Layer that owns resources
     * resources - an array of data sources that were used to render data
     * renderedFilterer - custom filter that was applied when rendering the specified data sources
     * currentFilterer - The value of the custom filter, this may differ from renderedFilterer if the
     *                   user has updated the form/map without causing a new render to occur
     */
    downloadData : function(layer, resources, renderedFilterer, currentFilterer) {
        var me = this;
        var isDifferentBBox = false;
        var originallyVisibleBBox = null;
        var currentlyVisibleBBox = null;

        originallyVisibleBBox = renderedFilterer.getSpatialParam();
        currentlyVisibleBBox = currentFilterer.getSpatialParam();
        isDifferentBBox = originallyVisibleBBox && currentlyVisibleBBox &&
                          !originallyVisibleBBox.equals(currentlyVisibleBBox);

        //Create a popup showing our options
        Ext.create('Ext.Window', {
            title : 'Download Options',
            buttonAlign : 'right',
            width : 550,
            height : 200,
            modal : true,
            layout : {
                type : 'anchor'
                //align : 'stretch'
            },
            buttons : [{
               text : 'Download',
               iconCls : 'download',
               handler : function(button) {
                   var bboxJson = '';
                   var popup = button.ownerCt.ownerCt;
                   var fieldSet = popup.items.getAt(1); //our second item is the fieldset
                   var radioGroup = fieldSet.items.getAt(0);
                   var checkedRadio = radioGroup.getChecked()[0]; //there should always be a checked radio

                   switch(checkedRadio.inputValue) {
                   case portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_CURRENTLY_VISIBLE:
                       me._doDownload(layer, currentFilterer, resources);
                       break;
                   case portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_ORIGINALLY_VISIBLE:
                       me._doDownload(layer, renderedFilterer, resources);
                       break;
                   default:
                       if (!Ext.Object.isEmpty(renderedFilterer.getParameters())) {
                           me._doDownload(layer, renderedFilterer, resources);
                       } else if (!Ext.Object.isEmpty(currentFilterer.getParameters())) {
                           me._doDownload(layer, currentFilterer, resources);
                       }
                   }

                   popup.close();
               }
            }],
            items : [{
                xtype : 'label',
                anchor : '100%',
                style : 'font-size: 12px;',
                text : 'The portal will make a download request on your behalf and return the results in a ZIP archive. How would you like the portal to filter your download?'
            },{
                xtype : 'fieldset',
                anchor : '100%',
                layout : 'fit',
                border : 0,
                items : [{
                    //Our radiogroup can see its item list vary according to the presence of bounding boxes
                    xtype : 'radiogroup',
                    //Forced to use fixed width columns
                    //see: http://www.sencha.com/forum/showthread.php?187933-Ext-4.1-beta-3-Incorrect-layout-on-Radiogroup-with-columns
                    //columns : [0.99, 18],
                    columns : [500, 18],
                    listeners : {
                        change : Ext.bind(this._handleRadioChange, this, [currentlyVisibleBBox, originallyVisibleBBox], true)
                    },
                    items : [{
                        boxLabel : 'Filter my download using the current visible map bounds.',
                        name : 'wfs-download-radio',
                        inputValue : portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_CURRENTLY_VISIBLE,
                        hidden : !isDifferentBBox || Ext.Object.isEmpty(currentlyVisibleBBox),
                        checked : isDifferentBBox

                    },{
                        xtype : 'box',
                        autoEl : {
                            tag : 'img',
                            src : 'portal-core/img/magglass.gif',
                            qtip : 'Click to display the spatial bounds, double click to pan the map so they are visible.'
                        },
                        width : 18,
                        height : 21,
                        hidden : !isDifferentBBox || Ext.Object.isEmpty(currentlyVisibleBBox),
                        style : 'padding:3px 0px 0px 0px;',
                        listeners : {
                            render : Ext.bind(this._configureImageClickHandlers, this, [currentlyVisibleBBox], true)
                        }
                    },{
                        boxLabel : 'Filter my download using the original bounds that were used to load the layer.',
                        name : 'wfs-download-radio',
                        inputValue : portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_ORIGINALLY_VISIBLE,
                        checked : !isDifferentBBox && !Ext.Object.isEmpty(originallyVisibleBBox),
                        hidden : Ext.Object.isEmpty(originallyVisibleBBox)
                    },{
                        xtype : 'box',
                        autoEl : {
                            tag : 'img',
                            src : 'portal-core/img/magglass.gif',
                            qtip : 'Click to display the spatial bounds, double click to pan the map so they are visible.'
                        },
                        width : 18,
                        height : 21,
                        style : 'padding:3px 0px 0px 0px;',
                        hidden : Ext.Object.isEmpty(originallyVisibleBBox),
                        listeners : {
                            render : Ext.bind(this._configureImageClickHandlers, this, [originallyVisibleBBox], true)
                        }
                    },{
                        boxLabel : 'Don\'t filter my download. Return all available data.',
                        name : 'wfs-download-radio',
                        inputValue : portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_ALL,
                        checked : !isDifferentBBox && Ext.Object.isEmpty(originallyVisibleBBox)
                    }]
                }]
            }]
        }).show();
    },

    _configureImageClickHandlers : function(c, eOpts, bbox) {
        var fireRender = function(bbox) {
            this.map.highlightBounds(bbox);
        };

        var fireScroll = function(bbox) {
            this.map.scrollToBounds(bbox);
        };

        c.getEl().on('click', Ext.bind(fireRender, this, [bbox], false), c);
        c.getEl().on('dblclick', Ext.bind(fireScroll, this, [bbox], false), c);
    },

    _handleRadioChange : function(radioGroup, newValue, oldValue, eOpts, currentBounds, originalBounds) {
        switch(newValue['wfs-download-radio']) {
        case portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_CURRENTLY_VISIBLE:
            this.map.scrollToBounds(currentBounds);
            break;
        case portal.layer.downloader.wfs.WFSDownloader.DOWNLOAD_ORIGINALLY_VISIBLE:
            this.map.scrollToBounds(originalBounds);
            break;
        }
    },

    /**
     * Handles a download the specified set of online resources and filterer
     *
     * filterer - a portal.layer.filterer.Filterer
     * resources - an array portal.csw.OnlineResource
     */
    _doDownload : function(layer, filterer, resources) {
        var renderer = layer.get('renderer');
        var downloadParameters = {
            serviceUrls : []
        };
        var proxyUrl = renderer.getProxyUrl();
        proxyUrl = (proxyUrl && proxyUrl.length > 0) ? proxyUrl : 'getAllFeatures.do';
        var prefixUrl = portal.util.URL.base + proxyUrl + "?";

        //Iterate our WFS records and generate the array of PORTAL BACKEND requests that will be
        //used to proxy WFS requests. That array will be sent to a backend handler for making
        //multiple requests and zipping the responses into a single stream for the user to download.
        var wfsResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
        for (var i = 0; i < wfsResources.length; i++) {
        	//VT: if there is a service provider filter we only want to download from the service provider specified
        	if(filterer.parameters.serviceFilter && filterer.parameters.serviceFilter.length > 0
        			&& wfsResources[i].get('url')!= filterer.parameters.serviceFilter[0]){
        		continue;
        	}
            //Create a copy of the last set of filter parameters
            var url = wfsResources[i].get('url');
            var typeName = wfsResources[i].get('name');
            var filterParameters = filterer.getParameters();

            filterParameters.serviceUrl = url;
            filterParameters.typeName = typeName;
            filterParameters.maxFeatures = 0;

            downloadParameters.serviceUrls.push(Ext.urlEncode(filterParameters, prefixUrl));
        }

        //download the service URLs through our zipping proxy
        portal.util.FileDownloader.downloadFile('downloadGMLAsZip.do', downloadParameters);
    }
});
/**
 * Feature source extension for pulling features directly from a WFS
 * using the a filter on a single property.
 *
 * Only the first feature matching a particular filter will be returned
 */
Ext.define('portal.layer.querier.wfs.featuresources.WFSFeatureByPropertySource', {
    extend : 'portal.layer.querier.wfs.FeatureSource',

    property : null,
    value : null,

    /**
     * Accepts a config in the form
     * {
     *  property : String - the property name to filter against
     *  value : [Optional] String - the value of the property to be used as a comparison match. If omitted, the featureId will be used
     * }
     */
    constructor : function(config) {
        this.property = config.property;
        this.value = config.value;

        this.callParent(arguments);
    },

    /**
     * See parent class for definition
     */
    getFeature : function(featureId, featureType, wfsUrl, callback) {
        var value = this.value ? this.value : featureId;

        var me = this;
        portal.util.Ajax.request({
            url : 'requestFeatureByProperty.do',
            params : {
                serviceUrl : wfsUrl,
                typeName : featureType,
                property : this.property,
                value : value
            },
            callback : function(success, data) {
                if (!success) {
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }

                // Load our xml string into DOM, extract the first feature
                var xmlDocument = portal.util.xml.SimpleDOM.parseStringToDOM(data.gml);
                if (!xmlDocument) {
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }
                var featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(xmlDocument.documentElement, 'http://www.opengis.net/gml', 'featureMember');
                if (featureMemberNodes.length === 0) {
                    featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(xmlDocument.documentElement, 'http://www.opengis.net/gml', 'featureMembers');
                }
                if (featureMemberNodes.length === 0 || featureMemberNodes[0].childNodes.length === 0) {
                    //we got an empty response - likely because the feature ID DNE.
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }

                callback(featureMemberNodes[0].childNodes[0], featureId, featureType, wfsUrl);
            }
        });
    }
});
/**
 * Feature source extensions for pulling features directly from a WFS
 * using the 'feature id' parameter.
 */
Ext.define('portal.layer.querier.wfs.featuresources.WFSFeatureSource', {
    extend : 'portal.layer.querier.wfs.FeatureSource',

    extraParams : null,

    constructor : function(config) {
        this.extraParams = config.extraParams ? config.extraParams : {};
        this.callParent(arguments);
    },

    /**
     * See parent class for definition
     */
    getFeature : function(featureId, featureType, wfsUrl, callback) {
        if (!featureId || !featureType || !wfsUrl) {
            callback(null, featureId, featureType, wfsUrl);
            return;
        }

        var params = Ext.apply({}, this.extraParams);
        Ext.apply(params, {
            serviceUrl : wfsUrl,
            typeName : featureType,
            featureId : featureId
        });

        var me = this;
        portal.util.Ajax.request({
            url : 'requestFeature.do',
            params : params,
            callback : function(success, data) {
                if (!success) {
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }

                // Load our xml string into DOM, extract the first feature
                var xmlDocument = portal.util.xml.SimpleDOM.parseStringToDOM(data.gml);
                if (!xmlDocument) {
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }
                var featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(xmlDocument.documentElement, 'http://www.opengis.net/gml', 'featureMember');
                if (featureMemberNodes.length === 0) {
                    featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(xmlDocument.documentElement, 'http://www.opengis.net/gml', 'featureMembers');
                }
                if (featureMemberNodes.length === 0 || featureMemberNodes[0].childNodes.length === 0) {
                    //we got an empty response - likely because the feature ID DNE.
                    callback(null, featureId, featureType, wfsUrl);
                    return;
                }

                callback(featureMemberNodes[0].childNodes[0], featureId, featureType, wfsUrl);
            }
        });
    }
});
/**
 * An implementation of portal.layer.legend.Legend for providing
 * simple GUI details on a WFS layer added to the map
 */
Ext.define('portal.layer.legend.wfs.WFSLegend', {
    extend: 'portal.layer.legend.Legend',

    iconUrl : '',

    /**
     * @param cfg an object in the form {
     *  iconUrl : String The URL of the marker icon for this layer
     * }
     */
    constructor : function(cfg) {
        this.iconUrl = cfg.iconUrl;
        this.callParent(arguments);
    },

    /**
     * Implemented function, see parent class
     */
    getLegendComponent : function(resources, filterer,response, isSld_body, callback) {
        
        var table = '<table>';
        var wfsOnlineResources = portal.csw.OnlineResource.getFilteredFromArray(resources, portal.csw.OnlineResource.WFS);
        table += '<tr><td><img height="16" width="16" src="' + this.iconUrl +'"><td><td>' + wfsOnlineResources[0].get('name') + '<td><tr>';
        table += '</table';
        
        var form = Ext.create('Ext.form.Panel',{
            title : 'WFS Feature',
            layout: 'fit',
            width: 250,
            html :  table
            });
        
        callback(this, resources, filterer, true, form); //this layer cannot generate a GUI popup
    },

    /**
     * Implemented function, see parent class
     */
    getLegendIconHtml : function(resources, filterer) {
        if (this.iconUrl && this.iconUrl.length > 0) {
            return Ext.DomHelper.markup({
                tag : 'div',
                style : 'text-align:center;',
                children : [{
                    tag : 'img',
                    width : 16,
                    height : 16,
                    align: 'CENTER',
                    src: this.iconUrl
                }]
            });
        } else {
            return '';
        }
    }
});/**
 * Class for making and then parsing a WFS request/response using a GenericParser.Parser class
 */
Ext.define('portal.layer.querier.wfs.WFSQuerier', {
    extend: 'portal.layer.querier.Querier',

    featureSource : null,

    /**
     * {
     *  featureSource : [Optional] An portal.layer.querier.wfs.FeatureSource implementation. If omitted WFSFeatureSource will be used
     *  parser : portal.layer.querier.wfs.Parser instances to parse features in GUI elements
     *  knownLayerParser : portal.layer.querier.wfs.KnownLayerParser to parse features in GUI elements
     * }
     */
    constructor: function(config){

        if (config.featureSource) {
            this.featureSource = config.featureSource;
        } else {
            this.featureSource = Ext.create('portal.layer.querier.wfs.featuresources.WFSFeatureSource', {});
        }

        this.parser = config.parser ? config.parser : Ext.create('portal.layer.querier.wfs.Parser', {});
        this.knownLayerParser = config.knownLayerParser ? config.knownLayerParser : Ext.create('portal.layer.querier.wfs.KnownLayerParser', {});

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },


    _generateErrorComponent : function(message) {
        return Ext.create('portal.layer.querier.BaseComponent', {
            html: Ext.util.Format.format('<p class="centeredlabel">{0}</p>', message)
        });
    },

    /**
     * See parent class for definition
     *
     * Makes a WFS request, waits for the response and then parses it passing the results to callback
     */
    query : function(queryTarget, callback) {
        //This class can only query for specific WFS feature's
        var id = queryTarget.get('id');
        var onlineResource = queryTarget.get('onlineResource');
        var layer = queryTarget.get('layer');
        var typeName = onlineResource.get('name');
        var wfsUrl = onlineResource.get('url');
        var applicationProfile = queryTarget.get('onlineResource').get('applicationProfile');

        //we need to get a reference to the parent known layer (if it is a known layer)
        var knownLayer = null;
        if (layer.get('sourceType') === portal.layer.Layer.KNOWN_LAYER) {
            knownLayer = layer.get('source');
        }

        //Download the DOM of the feature we are interested in
        var me = this;
        this.featureSource.getFeature(id, typeName, wfsUrl, function(wfsResponseRoot, id, typeName, wfsUrl) {
            if (!wfsResponseRoot) {
                callback(me, [me._generateErrorComponent(Ext.util.Format.format('There was a problem when looking up the feature with id \"{0}\"', id))], queryTarget);
                return;
            }

            //Parse our response into a number of GUI components, pass those along to the callback
            var allComponents = [];
            allComponents.push(me.parser.parseNode(wfsResponseRoot, onlineResource.get('url'), applicationProfile));
            if (knownLayer && me.knownLayerParser.canParseKnownLayerFeature(queryTarget.get('id'), knownLayer, onlineResource, layer)) {
                var knownLayerFeature = me.knownLayerParser.parseKnownLayerFeature(queryTarget.get('id'), knownLayer, onlineResource, layer);
                if(knownLayerFeature){
                    allComponents.push(knownLayerFeature);
                }
            }

            callback(me, allComponents, queryTarget);
        });
    }
});/**
 * Class for making and then parsing a WFS request/response using a GenericParser.Parser class
 */
Ext.define('portal.layer.querier.wfs.WFSWithMapQuerier', {
    extend: 'portal.layer.querier.Querier',

    featureSource : null,

    /**
     * {
     *  featureSource : [Optional] An portal.layer.querier.wfs.FeatureSource implementation. If omitted WFSFeatureSource will be used
     *  parser : portal.layer.querier.wfs.Parser instances to parse features in GUI elements
     *  knownLayerParser : portal.layer.querier.wfs.KnownLayerParser to parse features in GUI elements
     * }
     */
    constructor: function(config){

        if (config.featureSource) {
            this.featureSource = config.featureSource;
        } else {
            this.featureSource = Ext.create('portal.layer.querier.wfs.featuresources.WFSFeatureSource', {});
        }

        this.parser = config.parser ? config.parser : Ext.create('portal.layer.querier.wfs.Parser', {});
        this.knownLayerParser = config.knownLayerParser ? config.knownLayerParser : Ext.create('portal.layer.querier.wfs.KnownLayerParser', {});

        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },


    _generateErrorComponent : function(message) {
        return Ext.create('portal.layer.querier.BaseComponent', {
            html: Ext.util.Format.format('<p class="centeredlabel">{0}</p>', message)
        });
    },


    /**
     * See parent class for definition
     *
     * Makes a WFS request, waits for the response and then parses it passing the results to callback
     */
    query : function(queryTarget, callback) {

        var onlineResource = queryTarget.get('onlineResource');

        if(onlineResource.get('type')==='WMS'){
             this._checkGml32(queryTarget,callback,this);
        }else if(onlineResource.get('type')=='WFS'){
            this._handleWFSQuery(queryTarget, callback);
        }

    },

    _checkGml32 : function(queryTarget,callback,scope){
        var wmsOnlineResource = queryTarget.get('onlineResource');
        var serviceUrl = wmsOnlineResource.get('url');        
        
        Ext.Ajax.request({
            url : "checkGml32.do",
            timeout : 180000,
            scope : this,
            params :  {
                "serviceUrl" : serviceUrl
            },
            callback : function(options, success, response) {
                if (success && (response.responseText=='true')) {
                    // RA: GML 3.2 is not supported by GetFeatureInfo, so we have to use GetFeature
                    this._handleWFSQueryWithBbox(queryTarget,callback,scope);  
                } else {
                    this._handleWMSQuery(queryTarget,callback,scope);
                } 
            }
        });
    },
    
    _handleWMSQuery : function(queryTarget,callback,scope){
        //VT:app-schema wms requires the gml version to be declared in the info_format
        var applicationProfile = queryTarget.get('onlineResource').get('applicationProfile');
        
        var wmsOnlineResource = queryTarget.get('onlineResource');
        var typeName = wmsOnlineResource.get('name');
        var serviceUrl = wmsOnlineResource.get('url');
        var featureUrl=serviceUrl;
        if (applicationProfile && applicationProfile.indexOf("Esri:ArcGIS Server") > -1) {
            // ArcGIS does not permit a WMS query with feature ids with, so must use a WFS url
            var onlineResources = queryTarget.get('cswRecord').get('onlineResources');
            for (var idx=0; idx < onlineResources.length; idx++) {
                if (onlineResources[idx].get('type')=='WFS') {
                    featureUrl = onlineResources[idx].get('url');
                    break;
                }
            }
        }
        
        var point = Ext.create('portal.map.Point', {latitude : queryTarget.get('lat'), longitude : queryTarget.get('lng')});
        var lonLat = new OpenLayers.LonLat(point.getLongitude(), point.getLatitude());
        lonLat = lonLat.transform('EPSG:4326','EPSG:3857');

        var tileInfo = this.map.getTileInformationForPoint(point);
        var layer = queryTarget.get('layer');
        
        var bbox = tileInfo.getTileBounds();
        var bboxString = Ext.util.Format.format('{0},{1},{2},{3}',
                bbox.eastBoundLongitude,
                bbox.northBoundLatitude,
                bbox.westBoundLongitude,
                bbox.southBoundLatitude);

        var sldParams = this.generateSLDParams(queryTarget);
        
        var queryParams = Ext.Object.merge({
            serviceUrl : serviceUrl,
            lat : lonLat.lat,
            lng : lonLat.lon,
            QUERY_LAYERS : typeName,
            x : tileInfo.getOffset().x,
            y : tileInfo.getOffset().y,
            BBOX : bboxString,
            WIDTH : tileInfo.getWidth(),
            HEIGHT : tileInfo.getHeight(),
            version : wmsOnlineResource.get('version')                
            }, sldParams);
        
        var proxyGetFeatureInfoUrl ="wmsMarkerPopup.do";
        if (queryTarget.get('layer').get('source').get('proxyGetFeatureInfoUrl')) {
            proxyGetFeatureInfoUrl = queryTarget.get('layer').get('source').get('proxyGetFeatureInfoUrl');
        }
        
        this._displayWMSPopup(proxyGetFeatureInfoUrl, queryParams, queryTarget, applicationProfile, featureUrl, callback, false);
    },

    _handleWFSQueryWithBbox : function(queryTarget,callback,scope){
        var wmsOnlineResource = queryTarget.get('onlineResource');
        var typeName = wmsOnlineResource.get('name');      
        var methodPost = false;
        var applicationProfile = wmsOnlineResource.get('applicationProfile');
        var serviceUrl;
        var onlineResources = queryTarget.get('cswRecord').get('onlineResources');
        for (var idx=0; idx < onlineResources.length; idx++) {
            if (onlineResources[idx].get('type')=='WFS') {
                serviceUrl = onlineResources[idx].get('url');
                break;
            }
        }        

        if(queryTarget.get('layer').get('filterer').getParameters().postMethod){
            methodPost = queryTarget.get('layer').get('filterer').getParameters().postMethod;
        }

        //TODO: RA: this doesn't work properly at the lowest zoom level.
        // We need to factor in the zoom level when creating the bbox but I don't know how to        
//        var zoomLevel = this.map.getZoom();
        var bbox = Ext.create('portal.util.BBox',{
            eastBoundLongitude : queryTarget.get('lng') - 0.1,
            westBoundLongitude : queryTarget.get('lng') + 0.1,
            northBoundLatitude : queryTarget.get('lat') + 0.1,
            southBoundLatitude : queryTarget.get('lat') - 0.1
        }); 
        var queryParams = Ext.Object.merge({
            serviceUrl : serviceUrl,
            typeName : typeName,
            bbox : Ext.JSON.encode(bbox),  
            maxFeatures : 50
        });
        var proxyUrl="getAllGml32Features.do";
        
        this._displayWMSPopup(proxyUrl, queryParams, queryTarget, applicationProfile, serviceUrl, callback, true);
    },
    
    _displayWMSPopup : function(requestUrl, queryParams, queryTarget, applicationProfile, featureUrl, callback, isGml32) {
      //Start off by making a request for the GML at the specified location
        //We need to extract the survey line ID of the place we clicked
        Ext.Ajax.request({
            url : requestUrl,
            timeout : 180000,
            scope : this,
            params : queryParams,
            method : 'POST',//VT: potentially long sld_body forces us to use "POST" instead of "GET"
            callback : function(options, success, response) {
                if (!success) {
                    callback(this, [this._generateErrorComponent('There was an error when attempting to contact the remote WMS instance for information about this point.')], queryTarget);
                    return;
                }

                // VT: notes: we might be able to improve this. IF the wms getfeatureinfo response is the same as wfs GetFeature response, we can
                // jump straight in and use the response rather then getting the feature right now and then getting the wfs version of it.
                // I am unsure why it was implemented this way unless getFeatureInfo response is different from its wfs counterpart.

                //TODO: There is a convergence here between this and the WFSQuerier (parsing a wfs:FeatureCollection)
                var domDoc = portal.util.xml.SimpleDOM.parseStringToDOM(response.responseText);
                var featureMemberNodes;
                if (isGml32) {
                    // gml 3.2 specific
                    featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(domDoc.documentElement, 'http://www.opengis.net/wfs/2.0', 'member');
                } else {
                    featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(domDoc.documentElement, 'http://www.opengis.net/gml', 'featureMember');
                    if (featureMemberNodes.length === 0) {
                        featureMemberNodes = portal.util.xml.SimpleDOM.getMatchingChildNodes(domDoc.documentElement, 'http://www.opengis.net/gml', 'featureMembers');
                    }
                }
                if (featureMemberNodes.length === 0 || featureMemberNodes[0].childNodes.length === 0) {
                    //we got an empty response - likely because the feature ID DNE.
                    callback(this, [], queryTarget);
                    return;
                }

                var featureTypeRoots = featureMemberNodes[0].childNodes;
                var allComponents = [];
                
                var layer = queryTarget.get('layer');      
                // We need to get a reference to the parent known layer (if it is a known layer)
                var knownLayer = null;
                if (layer.get('sourceType') === portal.layer.Layer.KNOWN_LAYER) {
                    knownLayer = layer.get('source');
                } 
                
                for(var i=0; i < featureTypeRoots.length; i++){
                    var featureTypeRoot = featureTypeRoots[i];
    
                    //Extract the line ID of what we clicked
                    var id = portal.util.xml.SimpleXPath.evaluateXPathString(featureTypeRoot, '@gml:id');                         

                    var me = this;                                       
                    if (!featureTypeRoot) {
                        callback(me, [me._generateErrorComponent(Ext.util.Format.format('There was a problem when looking up the feature with id \"{0}\"', id))], queryTarget);
                        return;
                    }                                                            
                    var base = me.parser.parseNode(featureTypeRoot, featureUrl, applicationProfile);        
                    var wmsOnlineResource = queryTarget.get('onlineResource');
                    if (knownLayer && me.knownLayerParser.canParseKnownLayerFeature(id, knownLayer, wmsOnlineResource, layer)) {
                        var knownLayerFeature = me.knownLayerParser.parseKnownLayerFeature(id, knownLayer, wmsOnlineResource, layer);
                        if(knownLayerFeature){                            
                            var tabTitle = id;
                            if(base.tabTitle){
                                tabTitle = base.tabTitle;
                            }                            
                            //VT: if we have tabs within tabs, we use the tabTitles in each component to assign values to the title.
                            base.setTitle(base.tabTitle);                               
                            knownLayerFeature.setTitle(knownLayerFeature.tabTitle);                                                                                
                            var colateComponent =   Ext.create('Ext.tab.Panel',{
                                tabTitle : tabTitle,
                                layout : 'fit',                                                                                      
                                items : [base,knownLayerFeature]
                            });                            
                            allComponents.push(colateComponent);
                        }else{
                            allComponents.push(base);
                        }
                    }else{
                        allComponents.push(base);
                    }                    
                }
                callback(me, allComponents, queryTarget);                                
            }
        });
    },

    _handleWFSQuery : function(queryTarget, callback){

        //This class can only query for specific WFS feature's
        var id = queryTarget.get('id');
        var onlineResource = queryTarget.get('onlineResource');
        var layer = queryTarget.get('layer');
        var typeName = onlineResource.get('name');
        var wfsUrl = onlineResource.get('url');
        var applicationProfile = queryTarget.get('onlineResource').get('applicationProfile');

        //we need to get a reference to the parent known layer (if it is a known layer)
        var knownLayer = null;
        if (layer.get('sourceType') === portal.layer.Layer.KNOWN_LAYER) {
            knownLayer = layer.get('source');
        }

        //Download the DOM of the feature we are interested in
        var me = this;
        this.featureSource.getFeature(id, typeName, wfsUrl, function(wfsResponseRoot, id, typeName, wfsUrl) {
            if (!wfsResponseRoot) {
                callback(me, [me._generateErrorComponent(Ext.util.Format.format('There was a problem when looking up the feature with id \"{0}\"', id))], queryTarget);
                return;
            }

            //Parse our response into a number of GUI components, pass those along to the callback
            var allComponents = [];
            allComponents.push(me.parser.parseNode(wfsResponseRoot, onlineResource.get('url'), applicationProfile));
            if (knownLayer && me.knownLayerParser.canParseKnownLayerFeature(queryTarget.get('id'), knownLayer, onlineResource, layer)) {
                allComponents.push(me.knownLayerParser.parseKnownLayerFeature(queryTarget.get('id'), knownLayer, onlineResource, layer));
            }

            callback(me, allComponents, queryTarget);
        });
    }

});
/**
 * The new upgraded Searchfield doesn't work for us. It broke in multiple places
 * such as custom layer or even searches in known layer registered layer.
 * We have resorted to use the old SearchField and override the 4.1.1 version.
 */
Ext.define('portal.widgets.field.WMSCustomSearchField', {
    extend: 'Ext.form.field.Text',

    alias: 'widget.wmscustomsearchfield',


    hasSearch : false,
    paramName : 'query',

    initComponent: function(){       
        
        this.setTriggers({
            clear:{
                cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                handler: function() {
                    this.clearClick();
                }
            },
            search: {
                cls: Ext.baseCSSPrefix + 'form-search-trigger',
                handler: function() {
                    this.searchClick(false);
                }
            }            
        });
        
        this.callParent(arguments);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.searchClick(false);
            }
        }, this);
    },

    afterRender: function(){
        this.callParent();
        this.triggerCell.item(0).setDisplayed(false);
    },

    clearClick : function(){
        var me = this,
            store = me.store,
            proxy = store.getProxy(),
            val;

        if (me.hasSearch) {
            me.setValue('');
            proxy.extraParams[me.paramName] = '';
            this._clearLayerStore(store);            
            me.hasSearch = false;
            me.triggerCell.item(0).setDisplayed(false);
            me.updateLayout();
        }
    },
    
    _clearLayerStore : function(store){
        store.query("active",true).each(function(record){
            record.get('layer').removeDataFromMap();
        })
        store.removeAll();
    },

    searchClick : function(weakCheck){
        var me = this,
            store = me.store,
            proxy = store.getProxy(),
            value = me.getValue();
        this._clearLayerStore(store); 
        proxy.extraParams[me.paramName] = value;
        // Forces website to avoid extra checking
        if (weakCheck==true) {
          proxy.extraParams['weakCheck'] = 'Y';
        }
        store.loadPage(1);
        store.on('load',function(store, records, successful, eOpts){
            //VT:tracking            
            portal.util.GoogleAnalytic.trackevent('Custom WMS Query','URL:' + value,'ResultCount:'+ store.count(), store.count());
        },this)
        
     
        
        me.hasSearch = true;
        me.triggerCell.item(0).setDisplayed(true);
        me.updateLayout();
    }
});/**
 * A downloader that creates an Ext.Window specialised into showing a
 * dialog for the user to download features from a WMS in a zip file
 */
Ext.define('portal.layer.downloader.wms.WMSDownloader', {
    extend: 'portal.layer.downloader.Downloader',

    constructor : function(cfg) {
        this.callParent(arguments);
    },

    /**
     * An implementation of an abstract method, see parent method for details
     *
     * layer - portal.layer.Layer that owns resources
     * resources - an array of data sources that were used to render data
     * renderedFilterer - custom filter that was applied when rendering the specified data sources
     * currentFilterer - The value of the custom filter, this may differ from renderedFilterer if the
     *                   user has updated the form/map without causing a new render to occur
     */
    downloadData : function(layer, resources, renderedFilterer, currentFilterer) {
        var me = this;

        //Assumption - we are only interested in 1 WMS resource
        var wmsResource = portal.csw.OnlineResource.getFilteredFromArray(resources)[0];

        var formatStore = Ext.create('Ext.data.Store',{
            fields : ['format'],
            proxy : {
                type : 'ajax',
                url : 'getLayerFormats.do',
                extraParams : {
                    serviceUrl : wmsResource.get('url')
                },
                reader: {
                    type : 'json',
                    rootProperty : 'data'
                }
            },
            autoLoad : true
        });

        //Create a popup showing our options
        Ext.create('Ext.Window', {
            title : 'Download Options',
            buttonAlign : 'right',
            width : 300,
            height : 150,
            modal : true,
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            buttons : [{
               text : 'Download',
               iconCls : 'download',
               handler : function(button) {
                   var popup = button.ownerCt.ownerCt;
                   var combo = popup.items.getAt(1);

                   var format = combo.getValue();
                   if (!format || format.length === 0) {
                       return;
                   }

                   var vb = me.map.getVisibleMapBounds();
                   var mapSize = me.map.getMapSizeInPixels();
                   var bboxString = Ext.util.Format.format('{0},{1},{2},{3}',
                           (vb.westBoundLongitude < 0 ? parseInt(vb.westBoundLongitude) + 360.0 : vb.westBoundLongitude),
                           vb.southBoundLatitude,
                           (vb.eastBoundLongitude < 0 ? parseInt(vb.eastBoundLongitude) + 360.0 : vb.eastBoundLongitude),
                           vb.northBoundLatitude);

                   var queryString = Ext.Object.toQueryString({
                      request : 'GetMap',
                      service : 'WMS',
                      version : '1.1.1',
                      layers : wmsResource.get('name'),
                      format : format,
                      styles : '',
                      bgcolor : '0xFFFFFF',
                      transparent : true,
                      srs : 'EPSG:4326',
                      bbox : bboxString,
                      width : mapSize.getWidth(),
                      height : mapSize.getHeight()
                   });

                   //This is the WMS request URL (we will be proxying it through our local zipping proxy)
                   var wmsRequest = Ext.urlAppend(wmsResource.get('url'), queryString);

                   //Pass the WMS request to our zipping proxy
                   portal.util.FileDownloader.downloadFile('downloadDataAsZip.do', {
                       serviceUrls : [wmsRequest],
                       filename : 'WMSDownload.zip'
                   });

                   popup.close();
               }
            }],
            items : [{
                xtype : 'label',
                style : 'font-size: 12px;',
                text : 'What file format would you like to download this layer as?'
            },{
                xtype : 'combo',
                anchor: '100%',
                emptyText : 'Please select a file format.',
                forceSelection: true,
                queryMode: 'remote',
                triggerAction: 'all',
                typeAhead: true,
                typeAheadDelay: 500,
                store : formatStore,
                displayField : 'format',
                valueField : 'format'
            }]
        }).show();
    }
});
/**
 * Builds a form panel for WMS Layers (Containing WMS specific options such as transparency).
 *
 */
Ext.define('portal.layer.filterer.forms.WMSLayerFilterForm', {
    extend: 'portal.layer.filterer.BaseFilterForm',

    /**
     * Accepts a config for portal.layer.filterer.BaseFilterForm
     */
    constructor : function(config) {

        var filterer=config.layer.get('filterer');

        var sliderHandler = function(caller, newValue) {  
        	if (this.layer.get('source').get('active')) {
        		filterer.setParameter('opacity',newValue);
        	}
        };

        if(!filterer.getParameter('opacity')){
            filterer.setParameter('opacity',1,true);
        }

        Ext.apply(config, {

            border      : false,
            autoScroll  : false,
            hideMode    : 'offsets',
            width       : '100%',
            labelAlign  : 'right',
            bodyStyle   : 'padding:5px',
            height      :    65,
            layout: 'anchor',
            items:[ {
                xtype      :'fieldset',
                title      : 'WMS Properties',
                layout     : 'fit',
                height : '100%',
                items      : [{
                        xtype       : 'slider',
                        fieldLabel  : 'Opacity',
                        name        : 'opacity',
                        minValue    : 0,
                        increment   : 0.01,
                        decimalPrecision : false,
                        maxValue    : 1,
                        value       : config.layer.get('filterer').getParameter('opacity'),
                        listeners   : {
                        	changecomplete: sliderHandler,
                        	scope: this
                        }
                }]
            }]
        });

        this.callParent(arguments);
    }
});

/**
 * An implementation of portal.layer.legend.Legend for providing
 * simple GUI details on a WFS layer added to the map
 */
Ext.define('portal.layer.legend.wms.WMSLegend', {
    extend: 'portal.layer.legend.Legend',

    iconUrl : '',

    /**
     * @param cfg an object in the form {
     *  iconUrl : String The URL of the marker icon for this layer
     * }
     */
    constructor : function(cfg) {
        this.iconUrl = cfg.iconUrl;
        this.callParent(arguments);
    },

    /**
     * Implemented function, see parent class
     */
    getLegendComponent : function(resources, filterer,sld_body, isSld_body, callback, staticLegendUrl, tryGetCapabilitiesFirst) {
        // GPT-80 - Legend - This is called from BARP / _getLegendAction().  I think I want to change WMSLegendForm ... (see there)
        var form = Ext.create('portal.layer.legend.wms.WMSLegendForm',{resources:resources, filterer:filterer, sld_body:sld_body, staticLegendUrl:staticLegendUrl});
        callback(this, resources, filterer, true, form); //this layer cannot generate a GUI popup
        // GPT-80 - the Legend data now comes from async service calls and needs to added separately (prev. was done in constructor)
        form.addLegends({resources : resources, form : form, sld_body: sld_body, isSld_body: isSld_body, tryGetCapabilitiesFirst:tryGetCapabilitiesFirst});
    },

    /**
     * Implemented function, see parent class
     */
    getLegendIconHtml : function(resources, filterer) {
        if (this.iconUrl && this.iconUrl.length > 0) {
            return Ext.DomHelper.markup({
                tag : 'div',
                style : 'text-align:center;',
                children : [{
                    tag : 'img',
                    width : 16,
                    height : 16,
                    align: 'CENTER',
                    src: this.iconUrl
                }]
            });
        } else {
            return '';
        }
    },

    statics : {

        generateImageUrl : function(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles) {
            var url = wmsURL;
            var last_char = url.charAt(url.length - 1);
            if ((last_char !== "?") && (last_char !== "&")) {
              if (url.indexOf('?') == -1) {
                 url += "?";
              } else {
                 url += "&";
              }
            }
            url += 'REQUEST=GetLegendGraphic';
            url += '&SERVICE=WMS';
            url += '&VERSION='+ wmsVersion;
            url += '&FORMAT=image/png';
            url += '&HEIGHT=25';
            url += '&BGCOLOR=0xFFFFFF';
            url += '&LAYER=' + escape(wmsName);
            url += '&LAYERS=' + escape(wmsName);
            if (width) {
                url += '&WIDTH=' + width;  
            }
            
            //vt: The sld for legend does not require any filter therefore it should be
            // able to accomadate all sld length.
            if (sld_body && sld_body.length < 2000) {
                if (isSld_body === true) {
                    url += '&SLD_BODY=' + escape(sld_body);
                } else {
                    url += '&SLD=' + encodeURIComponent(sld_body);
                }
                url += '&LEGEND_OPTIONS=forceLabels:on;minSymbolSize:16';
            }
            
            // GPT-MS -- I don't believe the below works. GetLegendGraphic takes a STYLE parameter, not a STYLES parameter. Have left it as is. 
            if (this.styles) {
                url += '&STYLES=' + escape(this.styles);
            } else if (applicationProfile && applicationProfile.indexOf("Esri:ArcGIS Server") > -1) {
            	var sld = portal.util.xml.SimpleDOM.parseStringToDOM(sld_body);
            	// GPT-MS : This would be better as an XPath '/StyledLayerDescriptor/UserStyle/Name" but I couldn't get it to work.  
            	url += '&STYLE=' + sld.getElementsByTagName("UserStyle")[0].getElementsByTagName("Name")[0].textContent;
            }

            return url;
        },
    
        /* Hits the WMS controller to doa  getCapabilties call on the layer and retrieve the LegendGraphicURL element */
        generateLegendGraphicFromGetCapabilities : function(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles, callback) {
            portal.util.Ajax.request({
                url: "getLegendURL.do",
                timeout : 30000,    // Yes this seems a long time but was necessary
                params : {
                    serviceUrl : wmsURL ,
                    wmsVersion : wmsVersion,
                    layerName : wmsName
                },
                scope : this,
                success: function(data, message){
                    callback(data);
                },
                failure: function(message) {
                    var getLegendGraphicUrl = portal.layer.legend.wms.WMSLegend.generateImageUrl(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles);
                    callback(getLegendGraphicUrl);
                }
            });
        },

        // WMS Can specify a <legendUrl> image - retrieve from the service
        generateLegendUrl : function(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles, tryGetCapabilitiesFirst, callback) {
            
            // first check the getCapabilities if configured to do so
            if (tryGetCapabilitiesFirst) {
                this.generateLegendGraphicFromGetCapabilities(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles, callback);
            } else {
                // the default behaviour: try a getLegendGraphic call and use the getCapabilties iff it fails
                var getLegendGraphicUrl = portal.layer.legend.wms.WMSLegend.generateImageUrl(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles);
                if (getLegendGraphicUrl) {
                    callback(getLegendGraphicUrl);
                } else {
                    this.generateLegendGraphicFromGetCapabilities(wmsURL,wmsName,wmsVersion,applicationProfile,width,sld_body,isSld_body,styles, callback);
                }
            }
        }
    }    
});/**
 * An implementation of portal.layer.legend.BaseComponent for providing
 * simple GUI details for a WMS Legend
 */
Ext.define('portal.layer.legend.wms.WMSLegendForm', {
    extend: 'portal.layer.legend.BaseComponent',

    staticLegendUrl: null,
    tryGetCapabilitiesFirst : false,

    constructor : function(config) {
 
        this.staticLegendUrl = config.staticLegendUrl;
        this.tryGetCapabilitiesFirst = config.tryGetCapabilitiesFirst;
        Ext.apply(config, {
            html    : '<p> Waiting for Legend data ...</p>'
        });
        this.callParent(arguments);
    },
    
    addLegends : function(config) {   
        var me = this;

        var dimensions = {maxWidth:330,height:30}; // Of all graphics so can resize window - accumulative height, max width (allow for title)

        // if a url to a static image was provided then just use that and
        if (this.staticLegendUrl) {
            var html = '<a target="_blank" href="' + this.staticLegendUrl + '">';
            html += '<img onerror="this.alt=\'There was an error loading this legend. Click here to try again in a new window or contact the data supplier.\'" alt="Loading legend..." src="' + this.staticLegendUrl + '"/>';
            html += '</a>';
            html += '<br/>\n';
            config.form.setData(html);
            me._setFormHeight(config.form, this.staticLegendUrl, dimensions);
            return;
        }

        if (config.sld_body && config.sld_body.length > 0 && config.sld_body.length < 2000) {
            this.addStyledLegend(config);           
        } else {
            var wmsOnlineResources = portal.csw.OnlineResource.getFilteredFromArray(config.resources, portal.csw.OnlineResource.WMS);
            var urls={};
            var dimensions = {maxWidth:330,height:30}; // Of all graphics so can resize window - accumulative height, max width (allow for title)

            for (var j = 0; j < wmsOnlineResources.length; j++) {
                var applicationProfile = wmsOnlineResources[j].get('applicationProfile');
                var width = this._determineWidth(applicationProfile, config.sld_body);

                portal.layer.legend.wms.WMSLegend.generateLegendUrl(
                    wmsOnlineResources[j].get('url'), 
                    wmsOnlineResources[j].get('name'),
                    wmsOnlineResources[j].get('version'),
                    applicationProfile,
                    width,
                    config.sld_body,
                    config.isSld_body,
                    undefined,
                    config.tryGetCapabilitiesFirst,
                    function(url) {
                        if (! urls.hasOwnProperty(url)) {
                            // Add a WIDTH attribute to ?requests (but not to normal resource GETs ie. that don't contain a '?')
                            if (!url.match(/width/i) === null && url.match(/\?/) !== null) {
                                // Force a width or else the gis server seems to return it truncated
                                url += "&WIDTH=100";
                            }
                            var html='';

                            urls[url] = 1;
                            Object.getOwnPropertyNames(urls).sort().forEach(function (url, index, array) {
                                html += '<a target="_blank" href="' + url + '">';
                                html += '<img onerror="this.alt=\'There was an error loading this legend. Click here to try again in a new window or contact the data supplier.\'" alt="Loading legend..." src="' + url + '"/>';
                                html += '</a>';
                                html += '<br/>\n';
                            });
                            config.form.setData(html);
                            me._setFormHeight(config.form, url, dimensions);
                        }
                    }
                );
            } 
        }
    },
    
    /*
     * Adds a legend image using SLD styles. If the sld style if being used for the layer then 
     * we will only have one legend so we need slightly different processing to the addLegends method
     */
    addStyledLegend : function(config) {
        var me = this;
        var wmsOnlineResources = portal.csw.OnlineResource.getFilteredFromArray(config.resources, portal.csw.OnlineResource.WMS);
        var sourceUrls = [];
        var loopIndex = 0;
        
        // do some AJAX stuff to populate the list of image URLS
        for (loopIndex; loopIndex < wmsOnlineResources.length; loopIndex++) {
            var applicationProfile = wmsOnlineResources[loopIndex].get('applicationProfile');
            var width = this._determineWidth(applicationProfile, config.sld_body);
            
           
            var handler = portal.layer.legend.wms.WMSLegend.generateLegendUrl(
                    
                wmsOnlineResources[loopIndex].get('url'), 
                wmsOnlineResources[loopIndex].get('name'),
                wmsOnlineResources[loopIndex].get('version'),
                applicationProfile,
                width,
                config.sld_body,
                config.isSld_body,
                undefined,
                me.tryGetCapabilitiesFirst,
                // callback function. Populates the array of legend urls
                function(url) {                    
                    if (sourceUrls.indexOf(url) == -1) {                                                    
                        sourceUrls.push(url);                           
                    }
                }
            );
        };    
        
    	var useableImage = false;
    	
    	// now loop through looking for a useable image
        for (loopIndex = 0; loopIndex < sourceUrls.length; loopIndex++) {                            
            if (useableImage)
             	break;
                
            var url = sourceUrls[loopIndex];
            var image = new Image();    
            image.onload = function() {                    
                if (this.height > 0) {               
                    var html='';
                    html += '<a target="_blank" href="' + this.src + '">';
                    html += '<img onerror="this.alt=\'There was an error loading this legend. Click here to try again in a new window or contact the data supplier.\'" alt="Loading legend..." src="' + this.src + '"/>';
                    html += '</a>';                        
                    config.form.setData(html);                    
                    me._setStyledFormHeight(config.form, this);
                    
                    useableImage = true;
                }   
            }; 
            image.src=url;            
        }               
    },
    
    _setStyledFormHeight : function(form, image) {
    	var dimensions = {maxWidth:330,height:30};
        dimensions.height += image.height;
        // Add extra to allow for spacing
        dimensions.height += (dimensions.height * 0.02);
        dimensions.maxWidth = Math.max(dimensions.maxWidth,image.width);
        form.setHeight(dimensions.height);
        form.setWidth(dimensions.maxWidth);        
    },
    
    _setFormHeight : function(form, url, dimensions) {
        var image = new Image(); 
        image.onload = function(){
            dimensions.height += image.height;
            // Add extra to allow for spacing
            dimensions.height += (dimensions.height * 0.02);
            dimensions.maxWidth = Math.max(dimensions.maxWidth,image.width);
            form.setHeight(dimensions.height);
            form.setWidth(dimensions.maxWidth);
        };
        image.src=url;
    },
    
    _determineWidth : function(applicationProfile, sld_body) {
        if (applicationProfile && applicationProfile.indexOf("Esri:ArcGIS Server") > -1) {
            return 300;
        } else if (sld_body) {
            return null;
        } else {
            return this.getWidth();
        }
    }

});/**
 * Class for making and then parsing a WMS request/response for WMSGetFeatureRequests that may return more than on Feature Field and render each field
 * in a tab in the panel / window.
 * Example of this code use, https://github.com/GeoscienceAustralia/geoscience-portal/pull/80/files
 * This should be subclassed with implementations of a number of unimplemented methods defined here. 
 */
Ext.define('records', {
    extend : 'Ext.data.Model', 
    fields: [
             {name: 'field', type: 'string'},
             {name: 'value', type: 'string'}
    ]
});

Ext.define('portal.layer.querier.wms.WMSMultipleTabDisplayQuerier', {
    extend: 'portal.layer.querier.wms.WMSQuerier',

    constructor: function(config){
        this.callParent(arguments);
    },

    /**
     * @Override
     * See parent class for definition
     *
     * Makes a WMS request, waits for the response and then parses it passing the results to callback
     */
    query : function(queryTarget, callback) {
        var me = this;
        var proxyUrl = this.generateWmsProxyQuery(queryTarget, 'text/xml');
        var layerName = queryTarget.get('layer').get('name');
        
        // We need to prepare the data first with an array of maps.  The array is the fields (to display as a tab per field) and the maps is the data
        // in each tab.  We display it later in a Store passed to a grid.Panel
        var fieldsArray = [];
        
        Ext.Ajax.request({
            url : proxyUrl,
            timeout : 180000,
            scope : this,
            callback : function(options, success, response) {
                if (success) {
                    var xmlResponse = response.responseText;
                    var domDoc = portal.util.xml.SimpleDOM.parseStringToDOM(xmlResponse);
                    var wmsGetFeatureInfo = new OpenLayers.Format.WMSGetFeatureInfo();
                    var fields = wmsGetFeatureInfo.read_FeatureInfoResponse(domDoc);
                    //    console.log("Fields: ", fields);
                    me.populateFeatureFieldsDisplayArray(fields, fieldsArray);
                    me._drawFeatureFieldsTabs(layerName, fieldsArray);
                    callback(me, [], queryTarget);
                }else{
                    callback(this, [this.generateErrorComponent('There was an error when attempting to contact the remote WMS instance for information about this point.')], queryTarget);
                }
            }
        });
    },

    _getPopulatedStore : function(fields) {
        // Stores are best way to display data in the Extjs UI
        var store = Ext.create('Ext.data.Store', {
            model : 'records'
        });
        
        var order = fields.order;
        for (var j = 0; j < order.length; j++) {
            var key = order[j];
            store.add([{field:key, value:fields[key]}]);
        }
        return store;
    }, 
    
    _fieldNameMapping : function (dataKey) {
        var fieldNameMappingMap = this.getFieldNameMappingMap();
        if (fieldNameMappingMap.hasOwnProperty(dataKey)) {
            return fieldNameMappingMap[dataKey];
        }
        return false;
    },
    
    _drawFeatureFieldsTabs : function(name, fieldsArray) {
        var win = Ext.create('Ext.Window', {
            border      : true,
            layout      : 'fit',
            resizable   : false,
            modal       : true,
            plain       : false,
            title       : name,
            constrain   : true,
            items:[{
                xtype           : 'tabpanel',
                activeItem      : 0,
                enableTabScroll : true,
                buttonAlign     : 'center',
                items           : []
            }]
        });
        var tabPanel = win.items.getAt(0);

        for (var i = 0; i < fieldsArray.length; i++) {
            var store = this._getPopulatedStore(fieldsArray[i]);
            var tabTitle = fieldsArray[i][this.getTabTitleMappedName()];


            var gridPanel = Ext.create('Ext.grid.Panel', {
                store : store,
                width : 860,
                hideHeaders : true,
                columns : [
                   {
                       text : "Feature",
                       dataIndex:"field",
                       width : 250,
                       align : "right",
                       renderer: function(value) {
                           return '<span style="font-size : 1.2 em; font-weight : bolder">'+value+'</span>';
                       }
                   },
                   {
                       text : "Value",
                       dataIndex: "value",
                       flex : true,
                       renderer: function(value) {
                           if (value.indexOf("http") == 0) {
                               return '<a href="' + value + '" target="_blank">' + value + '</a>';
                           } else {
                               return value;
                           }
                       },
                       listeners : {
                           delegate: 'div a',
                           click : function(name, title, cell, element) {
                               var link = element.innerText.trim();
                               if (link.indexOf("http") === 0 ){
                                   portal.util.GoogleAnalytic.trackevent("QueryPanelLinkClick", name, title, link);
                               }
                           },
                           args: [name, tabTitle]
                       }

                   }
               ]
            });

            tabPanel.add({
                title : tabTitle,
                items : [gridPanel]
            });
        }
        win.show();
    },

    /**
     * Define the mapping from WFS GetFeatureInfo Key to what should be displayed for it.
     * eg. 
     * var fieldNameMappingMap = {
     *   EDITION: "Edition",
     *   PUBYEAR: "Publication Year",
     *   LABEL: "Map Title"
     * };
     * return fieldNameMappingMap;
     */
    getFieldNameMappingMap : portal.util.UnimplementedFunction,
    
    /**
     * Return the mapped field name as defined in getFieldNameMappingMap() to use to title the tab
     * eg.
     * return "Map Title";
     */
    getTabTitleMappedName : portal.util.UnimplementedFunction,
    
    /**
     * Populate the passed in array featureFieldsDisplayArray with display data from featureFieldsArray which contains all 
     * the returned fields for the features that for example, were clicked upon.
     *
     * Arguments:
     *  featureFieldsArray - array of features where each feature is an object Map of the fields from WFS GetFeatureInfo
     *  featureFieldsDisplayArray - passed in empty array to populate -
     *      Array of Object Map of fields to be displayed.  Included in that map is 'order' array of the fields keys
     *          [feature1={'order':[field1, field2, ..], 'field1':scalar string, 'field2':scalar string}, feature2={'order' ...}];
     *      where each fieldX is the display name as returned in the value part of map by getFieldNameMappingMap()
     */
    populateFeatureFieldsDisplayArray : portal.util.UnimplementedFunction
});/**
 * Represents a simple Polyline (series of straight line segments) as implemented by the Gmap API
 */
Ext.define('portal.map.openlayers.primitives.WMSOverlay', {

    extend : 'portal.map.primitives.BaseWMSPrimitive',

    config : {
        wmsLayer : null
    },

    /**
     * Accepts portal.map.primitives.BaseWMSPrimitive constructor args as well as
     *
     * map : Instance of GMap2
     */
    constructor : function(cfg) {
        this.callParent(arguments);
       
        var wmsLayer = null;
        
        var cswRecord = cfg.layer.getCSWRecordsByResourceURL(cfg.wmsUrl)
        //VT: We work on the assumption that 1 CSW Record == 1 wms layer.         
        var wmsOnlineResources = portal.csw.OnlineResource.getFilteredFromArray(cswRecord[0].get('onlineResources'), portal.csw.OnlineResource.WMS);
        var wmsVersion='1.1.1';//VT:Default to 1.1.1 unless specified
        if(wmsOnlineResources.length > 0 && wmsOnlineResources[0].get('version')){
            wmsVersion = wmsOnlineResources[0].get('version');
        }        
        var applicationProfile = "";
        if(wmsOnlineResources.length > 0 && wmsOnlineResources[0].get('applicationProfile')){
            applicationProfile = wmsOnlineResources[0].get('applicationProfile');
        } 
        
        var singleTile = cfg.layer.get('source').get('singleTile');
        
        var cswboundingBox= this._getCSWBoundingBox(cswRecord);        

        var options = {
            layers: this.getWmsLayer(),
            version: wmsVersion,
            transparent : true,
            exceptions : 'BLANK',
            displayOutsideMaxExtent : true
        };
                
        var additionalOptions = {
            tileOptions: {},
            isBaseLayer : false,
            projection: cswboundingBox.crs,
            maxExtent: cswboundingBox.bounds,
            tileOrigin: new OpenLayers.LonLat(-20037508.34, -20037508.34),
            displayInLayerSwitcher : false
        };
        
        if (singleTile == true) {
            additionalOptions.singleTile = true;
            additionalOptions.ratio = 1;
        }
        
        if (applicationProfile !== "Esri:ArcGIS Server") {
        	additionalOptions.tileOptions.maxGetUrlLength = 1500;
        }
        
        if(this.getSld_body() && this.getSld_body().length > 0){            
            options.sld_body = this.getSld_body();
            options.styles = this._getStylesFromSLD(applicationProfile);
            options.tiled = true;
        } 

        wmsLayer = new OpenLayers.Layer.WMS(
            this.getWmsLayer(),
            this.getWmsUrl(),
            options,
            additionalOptions
        );
        
        if (this.getOpacity()) {
            wmsLayer.setOpacity(this.getOpacity());
        }
        
        wmsLayer._portalBasePrimitive = this;

        this.setWmsLayer(wmsLayer);
    },
    
    /*
     * Returns STYLES parameter from the SLD, which is required for ArcGIS WMS.
     * 
     * The design only permits one layer per SLD.
     */
    

    _getStylesFromSLD : function(applicationProfile) {
        if (applicationProfile && applicationProfile.indexOf("Esri:ArcGIS Server") > -1) {
            var sld = portal.util.xml.SimpleDOM.parseStringToDOM(this.getSld_body());
            // GPT-MS : This would be better as an XPath
            // '/StyledLayerDescriptor/UserStyle/Name" but I couldn't get it to work.
            return sld.getElementsByTagName("UserStyle")[0].getElementsByTagName("Name")[0].textContent;
        }
        return null;
    },
    
    _getCSWBoundingBox : function(cswrecords){
        var bbox = null;
        var crs = null;
        for(var i=0;i<cswrecords.length;i++){
            var geoEl = cswrecords[i].get('geographicElements')[0]
            if(bbox){
                bbox = bbox.combine(geoEl);
            }else{
                bbox = geoEl;
            }
            crs=geoEl.crs;
        }

        var openlayerBoundObject = {
                bounds : new OpenLayers.Bounds(bbox.westBoundLongitude, bbox.southBoundLatitude, bbox.eastBoundLongitude, bbox.northBoundLatitude),
                crs : crs
        }

        if(crs != 'EPSG:3857'){
            openlayerBoundObject.crs='EPSG:3857';
            openlayerBoundObject.bounds = openlayerBoundObject.bounds.transform(crs,'EPSG:3857');
        }

        return openlayerBoundObject;
    }
});
/**
 * Represents a simple Polyline (series of straight line segments) as implemented by the Gmap API
 */
Ext.define('portal.map.gmap.primitives.WMSOverlay', {

    extend : 'portal.map.primitives.BaseWMSPrimitive',

    config : {
        /**
         * GTileLayer instance
         */
        tileLayer : null,
        tileLayerOverlay : null
    },

    /**
     * Accepts portal.map.primitives.BaseWMSPrimitive constructor args as well as
     *
     * map : Instance of GMap2
     */
    constructor : function(cfg) {
        this.callParent(arguments);

        var tileLayer = new GWMSTileLayer(cfg.map, new GCopyrightCollection(""), 1, 17);
        tileLayer._portalBasePrimitive = this;
        tileLayer.baseURL = this.getWmsUrl();
        tileLayer.layers = this.getWmsLayer();
        tileLayer.opacity = this.getOpacity();

        var overlay = new GTileLayerOverlay(tileLayer);
        overlay._portalBasePrimitive = this;

        this.setTileLayer(tileLayer);
        this.setTileLayerOverlay(overlay);
    }
});/**
 * Class for making and then parsing a WMS request/response
 */
Ext.define('portal.layer.querier.wms.WMSQuerier', {
    extend: 'portal.layer.querier.Querier',

    constructor: function(config){
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },

    /**
     * Creates a BaseComponent rendered with an error message
     *
     * @param message The message string to put in the body of the generated component
     * @param tabTitle The title of the tab (defaults to 'Error')
     */
    generateErrorComponent : function(message, tabTitle) {
        return Ext.create('portal.layer.querier.BaseComponent', {
            html: Ext.util.Format.format('<p class="centeredlabel">{0}</p>', message),
            tabTitle : tabTitle ? tabTitle : 'Error'
        });
    },

    /**
     * Returns true if WMS GetFeatureInfo query returns data.
     *
     * We need to hack a bit here as there is not much that we can check for.
     * For example the data does not have to come in tabular format.
     * In addition html does not have to be well formed.
     * In addition an "empty" click can still send style information
     *
     * So ... we will assume that minimum html must be longer then 30 chars
     * eg. data string: <table border="1"></table>
     *
     * For a bit of safety lets only count the bytes in the body tag
     *
     * @param {iStr} HTML string content to be verified
     * @return {Boolean} Status of the
     */
    isHtmlDataThere : function(iStr) {
        //This isn't perfect and can technically fail
        //but it is "good enough" unless you want to start going mental with the checking
        var lowerCase = iStr.toLowerCase();

        //If we have something resembling well formed HTML,
        //We can test for the amount of data between the body tags
        var startIndex = lowerCase.indexOf('<body>');
        var endIndex = lowerCase.indexOf('</body>');
        if (startIndex >= 0 || endIndex >= 0) {
            return ((endIndex - startIndex) > 32);
        }

        //otherwise it's likely we've just been sent the contents of the body
        return lowerCase.length > 32;
    },


    /**
     * This is for creating a Node Objects from a DOM Node in the form
     * {
     *  text : String
     *  leaf : Boolean
     * }
     */
    _createTreeNode : function(documentNode) {
        var treeNode = null;

        // We have a leaf
        if (portal.util.xml.SimpleDOM.isLeafNode(documentNode)) {
            var textContent = portal.util.xml.SimpleDOM.getNodeTextContent(documentNode);

            treeNode = {
                text : documentNode.tagName + " = " + textContent,
                children : [],
                leaf: true
            };
        } else { // we have a parent node
            var parentName = documentNode.tagName;
            if (documentNode.attributes.length > 0) {
                parentName += '(';
                for ( var i = 0; i < documentNode.attributes.length; i++) {
                    parentName += ' ' + documentNode.attributes[i].nodeName +
                                  '=' + documentNode.attributes[i].value;
                }
                parentName += ')';
            }
            treeNode = {
                text : parentName,
                children : [],
                leaf: true
            };
        }

        return treeNode;
    },

    /**
     * Given a DOM tree starting at xmlDocNode, this function returns the
     * equivelant tree in ExtJs Tree Nodes
     */
    _parseXmlTree : function(xmlDocNode, treeNode) {
        var nodes = [];
        Ext.each(xmlDocNode.childNodes, function(docNodeChild) {
            if (docNodeChild.nodeType == portal.util.xml.SimpleDOM.XML_NODE_ELEMENT) {
                var treeChildNode = this._createTreeNode(docNodeChild);
                treeNode.leaf = false;
                treeNode.children.push(treeChildNode);
                nodes.push(treeNode);
                this._parseXmlTree(docNodeChild, treeChildNode);
            }
        }, this);

        return nodes;
    },

    _parseStringXMLtoTreePanel : function(xmlString){
        var domNode = portal.util.xml.SimpleDOM.parseStringToDOM(xmlString);
        var rootNode = this._createTreeNode(domNode.documentElement);
        this._parseXmlTree(domNode.documentElement, rootNode);
        rootNode.expanded = true;

        // Continuously expand child nodes until we hit a node with
        // something "interesting" defined as a node with more than 1 child
        if (rootNode.children.length == 1) {
            var childNode = rootNode.children[0];
            while (childNode) {
                childNode.expanded = true;

                if (childNode.children.length > 1) {
                    break;
                } else {
                    childNode = childNode.children[0];
                }
            }
        }

        var panelConfig = {
            layout : 'fit',
            height: 300,
            items : [{
                xtype : 'treepanel',
                autoScroll : true,
                rootVisible : true,
                root : rootNode
            }]
        };

        return panelConfig;
    },

    /**
     * See parent class for definition
     *
     * Makes a WMS request, waits for the response and then parses it passing the results to callback
     */
    query : function(queryTarget, callback) {
        var proxyUrl = this.generateWmsProxyQuery(queryTarget, 'text/html');
        Ext.Ajax.request({
            url: proxyUrl,
            timeout : 180000,
            scope : this,
            callback : function(options, success, response) {
                var cmp = null;

                if (!success) {
                    cmp = this.generateErrorComponent('There was an error when attempting to contact the remote WMS instance for information about this point.');
                } else if (this.isHtmlDataThere(response.responseText)) {
                    cmp = Ext.create('portal.layer.querier.BaseComponent', {
                        autoScroll : true,
                        html: response.responseText
                    });
                }

                if (cmp !== null) {
                    callback(this, [cmp], queryTarget);
                } else {
                    callback(this, [], queryTarget);
                }
            }
        });
    }
});/**
 * Class for making and then parsing a WMS request/response
 */
Ext.define('portal.layer.querier.wms.WMSXMLFormatQuerier', {
    extend: 'portal.layer.querier.wms.WMSQuerier',

    constructor: function(config){
        // Call our superclass constructor to complete construction process.
        this.callParent(arguments);
    },



    /**
     * @Override
     * See parent class for definition
     *
     * Makes a WMS request, waits for the response and then parses it passing the results to callback
     */
    query : function(queryTarget, callback) {
        var proxyUrl = this.generateWmsProxyQuery(queryTarget, 'text/xml');
        Ext.Ajax.request({
            url: proxyUrl,
            timeout : 180000,
            scope : this,
            callback : function(options, success, response) {
                var cmp = null;

                if (!success) {
                    cmp = this.generateErrorComponent('There was an error when attempting to contact the remote WMS instance for information about this point.');
                } else {
                    //VT: Hopefully this is a once off event. When we serve up wms using GeoServer,
                    //VT: we can process the it normally using wmsquerier because of it supports info_format=text/html
                    //VT: However we should be able to reuse this in the future when we are required to parese xml instead of html.
                    cmp = Ext.create('portal.layer.querier.BaseComponent', this._parseStringXMLtoTreePanel(response.responseText));
                }

                if (cmp !== null) {
                    callback(this, [cmp], queryTarget);
                } else {
                    callback(this, [], queryTarget);
                }
            }
        });
    }

});