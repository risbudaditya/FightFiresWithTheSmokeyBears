using UnityEngine;
using System;
using System.Collections.Generic;
using System.Net;
using System.IO;

public class PeopleStuckInFire : MonoBehaviour {

    private DateTime time;
    System.Random random;
    private static int indexer = 0;
    private int index;

    // Use this for initialization
    void Start () {
        time = DateTime.Now;
        random = new System.Random();
        index = ++indexer;
    }

    // Update is called once per frame
    void Update() {
        if ((DateTime.Now - time).Milliseconds > 1000) {
            return;
        }
        time = DateTime.Now;
        int factor = 2;
        transform.Translate(new Vector3(0.2f - random.Next(1, factor+1) / 10, random.Next(-1 * factor * 2,factor* 2)/3, random.Next(-1*factor, factor) / 10));
        return;
        SmokeySingleton.Response fireData = SmokeySingleton.getInstance().fireData;
        try
        {
            float xtransform = (float)(transform.position.x - fireData.clusters[index][0]);
            float ytransform = (float)(transform.position.y - fireData.clusters[index][1]);
            ((TextMesh)GetComponent(typeof(TextMesh))).text = fireData.clusters[index][2].ToString();
            transform.Translate(new Vector3(xtransform, ytransform));
        }
        catch (Exception e)
        {
            Console.Out.WriteLine("-----------------");
            Console.Out.WriteLine(e.Message);
        }
    }
}
