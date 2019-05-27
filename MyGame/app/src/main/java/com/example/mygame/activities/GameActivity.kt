package com.example.mygame.activities

import android.content.Context
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mygame.views.GameView

class GameActivity: AppCompatActivity(), SensorEventListener {

    private lateinit var gameView: GameView
    private lateinit var sensorManager: SensorManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        gameView = GameView(this, size.x, size.y)
        setContentView(gameView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gameView.setOnClickListener{
            jump()
            startGame()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_ACCELEROMETER)
                gameView.updatePlayerX(event!!.values[0].toInt())
            else
                gameView.showStar(event.values[0])
        }
    }

    private fun jump() {
        gameView.jump()
    }
    private fun startGame() {
        gameView.startGame()
    }

    override fun onPause() {
        gameView.pause()
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onResume() {
        gameView.resume()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_FASTEST)
        super.onResume()
    }
}