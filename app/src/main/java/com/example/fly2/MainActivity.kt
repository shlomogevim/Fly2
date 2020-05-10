package com.example.fly2

import android.media.CamcorderProfile
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //url it dinosur , setScale(0.5)
    private val url =
        "https://firebasestorage.googleapis.com/v0/b/thermal-proton-239415.appspot.com/o/dinosur1.glb?alt=media&token=d469fb0d-e964-482f-9209-5109c16aca16"

    //url1 it the bird ,  setScale(0.002f)
    //  private val url1="https://firebasestorage.googleapis.com/v0/b/thermal-proton-239415.appspot.com/o/out.glb?alt=media&token=1a1bca0f-143e-446e-bbb8-1b49700bcdb8"
    lateinit var arFragment: ArFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = fragment as ArFragment
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            spawnObject(hitResult.createAnchor(), Uri.parse(url))

        }
    }

    private fun spawnObject(anchor: Anchor, modelUri: Uri) {
        val rendrebaleSource = RenderableSource.builder()
            .setSource(this, modelUri, RenderableSource.SourceType.GLB)
            .setScale(0.5f)
            // .setScale(0.002f)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable.builder()
            .setSource(this, rendrebaleSource)
            .setRegistryId(modelUri)
            .build()
            .thenAccept {
                addNodeToScene(anchor, it)
            }.exceptionally {
                Log.e("clima", "Somthing go wrong in loading model")
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor, modelRenderable: ModelRenderable) {
        val anchorNode = AnchorNode(anchor)
        TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            setParent(anchorNode)
        }
        arFragment.arSceneView.scene.addChild(anchorNode)
    }


//Original versia of fly
    /*  private val spaceship = Models.Bee
      private var modelResourceId = R.raw.beedrill

      private val nodes = mutableListOf<RotatingNode>()
      private lateinit var arFragment: ArFragment
      private var curCameraPosition = Vector3.zero()

      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          arFragment = fragment as ArFragment
          arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
              loadModelAndAddToScene(hitResult.createAnchor(), modelResourceId)
          }
          arFragment.arSceneView.scene.addOnUpdateListener {
              updateNodes()
          }
          setupFab()
      }

      private fun loadModelAndAddToScene(anchor: Anchor, modelResourceId: Int) {
          ModelRenderable.builder()
              .setSource(this, modelResourceId)
              .build()
              .thenAccept { modelRenderable ->
                  addNodeToScene(anchor, modelRenderable, spaceship)
                  eliminateDot()
              }.exceptionally {
                  Toast.makeText(this, "Error creating node: $it", Toast.LENGTH_LONG).show()
                  null
              }

      }

      private fun updateNodes() {
          curCameraPosition = arFragment.arSceneView.scene.camera.worldPosition
          for (node in nodes) {
              node.worldPosition =
                  Vector3(curCameraPosition.x, node.worldPosition.y, curCameraPosition.z)
          }
      }

      private fun addNodeToScene(
          anchor: Anchor,
          modelRenderable: ModelRenderable,
          spaceship: Models
      ) {
          val anchorNode = AnchorNode(anchor)
          val rotatingNode = RotatingNode(spaceship.degreesPerSecond).apply {
              setParent(anchorNode)
          }
          Node().apply {
              renderable = modelRenderable
              setParent(rotatingNode)
              localPosition = Vector3(spaceship.radius, spaceship.height, 0f)
              localRotation = Quaternion.eulerAngles(Vector3(0f, spaceship.rotationDegrees, 0f))
          }
          arFragment.arSceneView.scene.addChild(anchorNode)
          nodes.add(rotatingNode)
          val animationData = modelRenderable.getAnimationData("Beedrill_Animation")
          ModelAnimator(animationData, modelRenderable).apply {
              repeatCount = ModelAnimator.INFINITE
              start()
          }

      }

      private fun addNodeToScene(anchor: Anchor, modelRenderable: ModelRenderable) {
          val anchorNode = AnchorNode(anchor)
          TransformableNode(arFragment.transformationSystem).apply {
              renderable = modelRenderable
              setParent(anchorNode)
          }
          arFragment.arSceneView.scene.addChild(anchorNode)
      }

      private fun setupFab() {
          *//*      photoSaver = PhotoSaver(this)
              videoRecorder = VideoRecorder(this).apply {
                  sceneView = arFragment.arSceneView

                  setVideoQuality(CamcorderProfile.QUALITY_1080P, resources.configuration.orientation)
              }
              fab.setOnClickListener {
                  if (!isRecording) {
                      photoSaver.takePhoto(arFragment.arSceneView)
                  }
              }

              fab.setOnLongClickListener {
                  isRecording = videoRecorder.toggleRecordingState()
                  true
              }

              fab.setOnTouchListener { view, motionEvent ->

                  if (motionEvent.action == MotionEvent.ACTION_UP && isRecording) {

                      isRecording = videoRecorder.toggleRecordingState()

                      Toast.makeText(this, "Saved video to gallery!", Toast.LENGTH_LONG).show()

                      true

                  } else false

              }*//*

    }

    private fun eliminateDot() {
        arFragment.arSceneView.planeRenderer.isVisible = false
        arFragment.planeDiscoveryController.hide()
        arFragment.planeDiscoveryController.setInstructionView(null)
    }*/
}
