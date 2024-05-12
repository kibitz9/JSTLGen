class CJMShader{

        constructor(fragmentShaderSourcecode, canvasToRender){
                this.fs=fragmentShaderSourcecode;
                //window.alert(0);
                this.canvasToRender=canvasToRender;
                //window.alert(0);
                window.mouseevents = window.mouseevents ||  function(){};//fix?
                //window.alert(0);
                this.requestAnimationFrameInit();
                //window.alert(fragmentShaderSourcecode);
                this.shaderInit();
                //window.alert("Constructed");
        }
        start(){
                try{    
                        this.animate();
                }
                catch(error){
                        window.alert(error);
                }
        }
        //variables that need analysis
        vertex_position;
        timeLocation;
        resolutionLocation;
        iResolutionLocation;
        iMouseLocation;
        parameters = {  start_time  : new Date().getTime(), 
                        iTime        : 0, 
                        screenWidth : 0, 
                        screenHeight: 0 };
        canvasToRender;
        buffer;
        fs;
        gl;
        currentProgram;
        //fs="";
        //canvasToRender;
        vsHeader=`#version 300 es
                #ifdef GL_ES
                precision highp float;
                precision highp int;
                precision mediump sampler3D;
                #endif
                `;
        vs=`
                layout(location = 0) in vec2 pos;
                void main() {

                        gl_Position = vec4( pos.xy,0.0, 1.0 );
                        
                }
        `;

        fsHeader=`#version 300 es
                #ifdef GL_ES
                precision highp float;
                precision highp int;
                precision mediump sampler3D;
                #endif

                //shadertoy compatible items.
                #define HW_PERFORMANCE 1//TODO do something with this
                uniform float iTime;
                uniform int iFrame;
                uniform vec4 iMouse;
                uniform vec2 resolution;
                uniform vec3 iResolution;       
        `;
        vsFooter=``; 

        fsFooter=`
                out vec4 outColor;
                void main() {
                        vec4 tempColor = vec4(0.0,0.0,0.0,1.0);
                        mainImage(tempColor,gl_FragCoord.xy);
                        outColor = max(tempColor,vec4(0.0,0.0,0.0,1.0));
                        outColor = min(tempColor,vec4(1.0,1.0,1.0,1.0));
                }
        `;
        
        shaderInit() {
                //window.alert(canvas);
                //window.alert("1");
                //vertex_header_source = document.getElementById('vsHeader').textContent;
                var vertex_header_source = this.vsHeader;
                //fragment_header_source=document.getElementById('fsHeader').textContent;
                var fragment_header_source=this.fsHeader;
                //vertex_shader_source = document.getElementById('vs').textContent;
                var vertex_shader_source = this.vs;
                var fragment_shader_source = this.fs;
                //vertex_footer_source = document.getElementById('vsFooter').textContent;
                var vertex_footer_source = this.vsFooter;

                //fragment_footer_source = document.getElementById('fsFooter').textContent;
                var fragment_footer_source = this.fsFooter;
                //canvas = document.querySelector( 'canvas' );


                this.addMouseEvents();


                // Initialise WebGL

                try {

                        var opts = { alpha: false ,
                        depth: false, 
                        stencil: false, 
                        premultipliedAlpha: false, 
                        antialias: false, 
                        preserveDrawingBuffer: false, 
                        powerPreference: "high-performance",  // "low_power", "high_performance", "default"
                        };

                        this.gl = this.canvasToRender.getContext( "webgl2",opts);
                        
                        if( this.gl === null) this.gl = this.canvasToRender.getContext( "experimental-webgl2" );
                        if( this.gl === null) this.gl = this.canvasToRender.getContext( "webgl",opts );
                        if( this.gl === null) this.gl = this.canvasToRender.getContext( "experimental-webgl",opts );

                } catch( error ) { }

                if ( !this.gl ) {

                        throw "cannot create webgl context";

                }



                // Create Vertex buffer (2 triangles)

                this.buffer = this.gl.createBuffer();
                this.gl.bindBuffer( this.gl.ARRAY_BUFFER, this.buffer );
                this.gl.bufferData( this.gl.ARRAY_BUFFER, new Float32Array( [ - 1.0, - 1.0, 1.0, - 1.0, - 1.0, 1.0, 1.0, - 1.0, 1.0, 1.0, - 1.0, 1.0 ] ), this.gl.STATIC_DRAW );




                this.currentProgram = this.createProgram( vertex_shader_source, fragment_shader_source, vertex_header_source, fragment_header_source,vertex_footer_source,fragment_footer_source);

                this.timeLocation = this.gl.getUniformLocation( this.currentProgram, 'iTime' );
                this.frameLocation = this.gl.getUniformLocation( this.currentProgram, 'iFrame' );
                this.resolutionLocation = this.gl.getUniformLocation( this.currentProgram, 'resolution' );
                this.iResolutionLocation = this.gl.getUniformLocation(this.currentProgram, 'iResolution' );
                this.iMouseLocation = this.gl.getUniformLocation(this.currentProgram,'iMouse');

        }

        createProgram( vertex, fragment, vertexheader, fragmentheader, vertexfooter, fragmentfooter ) {

                var program = this.gl.createProgram();


                var vs = this.createShader( vertexheader+"\r\n"+vertex+"\r\n"+vertexfooter, this.gl.VERTEX_SHADER);
                var fs = this.createShader( fragmentheader+"\r\n"+fragment+"\r\n"+fragmentfooter, this.gl.FRAGMENT_SHADER);

                if ( vs == null || fs == null ) return null;

                this.gl.attachShader( program, vs );
                this.gl.attachShader( program, fs );

                this.gl.deleteShader( vs );
                this.gl.deleteShader( fs );

                this.gl.linkProgram( program );

                if ( !this.gl.getProgramParameter( program, this.gl.LINK_STATUS ) ) {

                        alert( "ERROR:\n" +
                        "VALIDATE_STATUS: " + this.gl.getProgramParameter( program, this.gl.VALIDATE_STATUS ) + "\n" +
                        "ERROR: " + this.gl.getError() + "\n\n" +
                        "- Vertex Shader -\n" + vertex + "\n\n" +
                        "- Fragment Shader -\n" + fragment );

                        return null;

                }

                return program;

        }

        createShader( src, type ) {

                var shader = this.gl.createShader( type );

                this.gl.shaderSource( shader, src );
                this.gl.compileShader( shader );

                if ( !this.gl.getShaderParameter( shader, this.gl.COMPILE_STATUS ) ) {

                        alert( ( type == this.gl.VERTEX_SHADER ? "VERTEX" : "FRAGMENT" ) + " SHADER:\n" + this.gl.getShaderInfoLog( shader ) );
                        return null;

                }

                return shader;

        }

        resizeCanvas(  ) {

                try{
                        if ( this.canvasToRender.width != this.canvasToRender.clientWidth ||
                                this.canvasToRender.height != this.canvasToRender.clientHeight ) {

                                        this.canvasToRender.width = this.canvasToRender.clientWidth;
                                        this.canvasToRender.height = this.canvasToRender.clientHeight;

                                        this.parameters.screenWidth = this.canvasToRender.width;
                                        this.parameters.screenHeight = this.canvasToRender.height;

                                        this.gl.viewport( 0, 0, this.canvasToRender.width, this.canvasToRender.height );

                        }
                }
                catch(error){
                        window.alert(error);
                }

        }

        
       
        render() {
                
               


                window.mouseevents.mMousePosX=window.mouseevents.mMousePosX||0;
                window.mouseevents.mMousePosY=window.mouseevents.mMousePosY||0;
                window.mouseevents.mMouseOriX=window.mouseevents.mMouseOriX||0;
                window.mouseevents.mMouseOriY=window.mouseevents.mMouseOriY||0;
                window.frameCounter = window.frameCounter||1;

                let mouse = [  window.mouseevents.mMousePosX, window.mouseevents.mMousePosY, window.mouseevents.mMouseOriX, window.mouseevents.mMouseOriY ];


                this.parameters.iTime = new Date().getTime() - this.parameters.start_time;

                this.gl.clear( this.gl.COLOR_BUFFER_BIT | this.gl.DEPTH_BUFFER_BIT );

                // Load program into GPU

                this.gl.useProgram( this.currentProgram );

                // Set values to program variables

                this.gl.uniform1f( this.timeLocation, this.parameters.iTime / 1000 );
                this.gl.uniform1i( this.frameLocation, window.frameCounter++);
                this.gl.uniform2f( this.resolutionLocation, this.parameters.screenWidth, this.parameters.screenHeight );
                this.gl.uniform3f( this.iResolutionLocation, this.parameters.screenWidth, this.parameters.screenHeight, 32. );
                this.gl.uniform4f( this.iMouseLocation, window.mouseevents.mMousePosX, window.mouseevents.mMousePosY,window.mouseevents.mMouseOriX, window.mouseevents.mMouseOriY);


                // Render geometry

                this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.buffer );
                this.gl.vertexAttribPointer( this.vertex_position, 2,this.gl.FLOAT, false, 0, 0 );
                this.gl.enableVertexAttribArray( this.vertex_position );
                this.gl.drawArrays(this.gl.TRIANGLES, 0, 6 );
                this.gl.disableVertexAttribArray( this.vertex_position );

        }


        addMouseEvents(){
                                        
                //shadertoy compatible mouse events.

                mouseevents.canvas = this.canvasToRender;

                mouseevents.canvas.onmousedown = function(ev)
                {
                        var rect = mouseevents.canvas.getBoundingClientRect();
                        mouseevents.mMouseOriX = Math.floor((ev.clientX-rect.left)/(rect.right-rect.left)*mouseevents.canvas.width);
                        mouseevents.mMouseOriY = Math.floor(mouseevents.canvas.height - (ev.clientY-rect.top)/(rect.bottom-rect.top)*mouseevents.canvas.height);
                        mouseevents.mMousePosX = mouseevents.mMouseOriX;
                        mouseevents.mMousePosY = mouseevents.mMouseOriY;
                        mouseevents.mMouseIsDown = true;
                        mouseevents.mMouseSignalDown = true;


                }

                mouseevents.canvas.onmousemove = function(ev)
                {
                        if( mouseevents.mMouseIsDown )
                        {
                        var rect = mouseevents.canvas.getBoundingClientRect();
                        mouseevents.mMousePosX = Math.floor((ev.clientX-rect.left)/(rect.right-rect.left)*mouseevents.canvas.width);
                        mouseevents.mMousePosY = Math.floor(mouseevents.canvas.height - (ev.clientY-rect.top)/(rect.bottom-rect.top)*mouseevents.canvas.height);

                        }
                }

                mouseevents.canvas.onmouseup = function(ev)
                {
                        mouseevents.mMouseIsDown = false;
                        if( mouseevents.mIsPaused ) mouseevents.mForceFrame = true;
                }
                /*
                */
        }
        counter = 0;
        animate() {
                try{
                        
                        this.resizeCanvas();
                        this.render();
                        //apparently requestAnimationFrame won't take class methods directly. Must be a function.
                        window.requestAnimationFrame( function(){shader.animate();});
                        this.counter++;
                }
                catch(err){
                        window.alert(this.counter);
                }

        }

        //The window.requestAnimationFrame() method tells the browser you wish to perform an animation.
        //It requests the browser to call a user-supplied callback function before the next repaint.
        //The following method does not invoke it but checks to see if it exists and if not
        //attempts to map alternate versions to the same window method
        //thereby setting it up in a cross-browser way...
        requestAnimationFrameInit(){
                window.requestAnimationFrame = window.requestAnimationFrame || ( function() {

                        return  window.webkitRequestAnimationFrame ||
                                window.mozRequestAnimationFrame ||
                                window.oRequestAnimationFrame ||
                                window.msRequestAnimationFrame 
                                ||
                                function(  callback, element ) {
                                        window.setTimeout( callback, 1000 / 60 );
                                }
                                ;

                })();
        }

}