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
    
function shaderInit(fs,canvas) {
//window.alert("1");
    //vertex_header_source = document.getElementById('vsHeader').textContent;
    vertex_header_source = vsHeader;
    //fragment_header_source=document.getElementById('fsHeader').textContent;
    fragment_header_source=fsHeader;
    //vertex_shader_source = document.getElementById('vs').textContent;
    vertex_shader_source = vs;
    fragment_shader_source = fs;
    //vertex_footer_source = document.getElementById('vsFooter').textContent;
    vertex_footer_source = vsFooter;

    //fragment_footer_source = document.getElementById('fsFooter').textContent;
    fragment_footer_source = fsFooter;
    //canvas = document.querySelector( 'canvas' );


    addMouseEvents(canvas);


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

            gl = canvas.getContext( "webgl2",opts);
            if( gl === null) gl = canvas.getContext( "experimental-webgl2" );
            if( gl === null) gl = canvas.getContext( "webgl",opts );
            if( gl === null) gl = canvas.getContext( "experimental-webgl",opts );

    } catch( error ) { }

    if ( !gl ) {

            throw "cannot create webgl context";

    }



    // Create Vertex buffer (2 triangles)

    buffer = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, buffer );
    gl.bufferData( gl.ARRAY_BUFFER, new Float32Array( [ - 1.0, - 1.0, 1.0, - 1.0, - 1.0, 1.0, 1.0, - 1.0, 1.0, 1.0, - 1.0, 1.0 ] ), gl.STATIC_DRAW );




    currentProgram = createProgram( vertex_shader_source, fragment_shader_source, vertex_header_source, fragment_header_source,vertex_footer_source,fragment_footer_source,gl);

    timeLocation = gl.getUniformLocation( currentProgram, 'iTime' );
    frameLocation = gl.getUniformLocation( currentProgram, 'iFrame' );
    resolutionLocation = gl.getUniformLocation( currentProgram, 'resolution' );
    iResolutionLocation = gl.getUniformLocation(currentProgram, 'iResolution' );
    iMouseLocation = gl.getUniformLocation(currentProgram,'iMouse');

}

function createProgram( vertex, fragment, vertexheader, fragmentheader, vertexfooter, fragmentfooter,gl ) {

        var program = gl.createProgram();


        var vs = createShader( vertexheader+"\r\n"+vertex+"\r\n"+vertexfooter, gl.VERTEX_SHADER);
        var fs = createShader( fragmentheader+"\r\n"+fragment+"\r\n"+fragmentfooter, gl.FRAGMENT_SHADER);

        if ( vs == null || fs == null ) return null;

        gl.attachShader( program, vs );
        gl.attachShader( program, fs );

        gl.deleteShader( vs );
        gl.deleteShader( fs );

        gl.linkProgram( program );

        if ( !gl.getProgramParameter( program, gl.LINK_STATUS ) ) {

                alert( "ERROR:\n" +
                "VALIDATE_STATUS: " + gl.getProgramParameter( program, gl.VALIDATE_STATUS ) + "\n" +
                "ERROR: " + gl.getError() + "\n\n" +
                "- Vertex Shader -\n" + vertex + "\n\n" +
                "- Fragment Shader -\n" + fragment );

                return null;

        }

        return program;

}

function createShader( src, type ) {

        var shader = gl.createShader( type );

        gl.shaderSource( shader, src );
        gl.compileShader( shader );

        if ( !gl.getShaderParameter( shader, gl.COMPILE_STATUS ) ) {

                alert( ( type == gl.VERTEX_SHADER ? "VERTEX" : "FRAGMENT" ) + " SHADER:\n" + gl.getShaderInfoLog( shader ) );
                return null;

        }

        return shader;

}

function resizeCanvas( canvas ) {
        //canvas = event.target||event.srcElement;
       // window.alert(canvas);
        try{
                if ( canvas.width != canvas.clientWidth ||
                        canvas.height != canvas.clientHeight ) {

                        canvas.width = canvas.clientWidth;
                        canvas.height = canvas.clientHeight;

                        parameters.screenWidth = canvas.width;
                        parameters.screenHeight = canvas.height;

                        gl.viewport( 0, 0, canvas.width, canvas.height );

                }
        }
        catch(error){
                window.alert(error);
        }

}

function animate(canvas) {

        resizeCanvas(canvas);
        render();
        window.requestAnimationFrame( animate );

}
function render() {
 
        if ( !currentProgram ) 
        {
            return;
        }


        window.mouseevents.mMousePosX=window.mouseevents.mMousePosX||0;
        window.mouseevents.mMousePosY=window.mouseevents.mMousePosY||0;
        window.mouseevents.mMouseOriX=window.mouseevents.mMouseOriX||0;
        window.mouseevents.mMouseOriY=window.mouseevents.mMouseOriY||0;
        window.frameCounter = window.frameCounter||1;

        let mouse = [  window.mouseevents.mMousePosX, window.mouseevents.mMousePosY, window.mouseevents.mMouseOriX, window.mouseevents.mMouseOriY ];


        parameters.iTime = new Date().getTime() - parameters.start_time;

        gl.clear( gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT );

        // Load program into GPU

        gl.useProgram( currentProgram );

        // Set values to program variables

        gl.uniform1f( timeLocation, parameters.iTime / 1000 );
        gl.uniform1i( frameLocation, window.frameCounter++);
        gl.uniform2f( resolutionLocation, parameters.screenWidth, parameters.screenHeight );
        gl.uniform3f( iResolutionLocation, parameters.screenWidth, parameters.screenHeight, 32. );
        gl.uniform4f( iMouseLocation, window.mouseevents.mMousePosX, window.mouseevents.mMousePosY,window.mouseevents.mMouseOriX, window.mouseevents.mMouseOriY);


        // Render geometry

        gl.bindBuffer( gl.ARRAY_BUFFER, buffer );
        gl.vertexAttribPointer( vertex_position, 2, gl.FLOAT, false, 0, 0 );
        gl.enableVertexAttribArray( vertex_position );
        gl.drawArrays( gl.TRIANGLES, 0, 6 );
        gl.disableVertexAttribArray( vertex_position );

}


function addMouseEvents(cv){
                          
    //shadertoy compatible mouse events.

    mouseevents.canvas = cv;

    mouseevents.canvas.onmousedown = function(ev)
    {
        var rect = mouseevents.canvas.getBoundingClientRect();
        mouseevents.mMouseOriX = Math.floor((ev.clientX-rect.left)/(rect.right-rect.left)*mouseevents.canvas.width);
        mouseevents.mMouseOriY = Math.floor(mouseevents.canvas.height - (ev.clientY-rect.top)/(rect.bottom-rect.top)*mouseevents.canvas.height);
        mouseevents.mMousePosX = mouseevents.mMouseOriX;
        mouseevents.mMousePosY = mouseevents.mMouseOriY;
        mouseevents.mMouseIsDown = true;
                mouseevents.mMouseSignalDown = true;
                //window.alert(mouseevents.mMouseOriX);
                //window.alert(window.mouseevents.mMouseOriX);
                //window.alert(Math.floor((ev.clientX-rect.left)/(rect.right-rect.left)*mouseevents.canvas.width));

    }

    mouseevents.canvas.onmousemove = function(ev)
    {
        if( mouseevents.mMouseIsDown )
        {
            var rect = mouseevents.canvas.getBoundingClientRect();
            mouseevents.mMousePosX = Math.floor((ev.clientX-rect.left)/(rect.right-rect.left)*mouseevents.canvas.width);
            mouseevents.mMousePosY = Math.floor(mouseevents.canvas.height - (ev.clientY-rect.top)/(rect.bottom-rect.top)*mouseevents.canvas.height);
           //window.alert(mouseevents.mMousePosX);
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

//The window.requestAnimationFrame() method tells the browser you wish to perform an animation.
//It requests the browser to call a user-supplied callback function before the next repaint.
//The following method does not invoke it but checks to see if it exists and if not
//attempts to map alternate versions to the same window method
//thereby setting it up in a cross-browser way...
function requestAnimationFrameInit(){
    window.requestAnimationFrame = window.requestAnimationFrame || ( function() {

            return  window.webkitRequestAnimationFrame ||
                    window.mozRequestAnimationFrame ||
                    window.oRequestAnimationFrame ||
                    window.msRequestAnimationFrame ||
                    function(  callback, element ) {
                            window.setTimeout( callback, 1000 / 60 );
                    };

    })();
}