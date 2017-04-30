using UnityEngine;
using System.Collections;
using System;

public class FireAndHowItGrows : MonoBehaviour {

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
    void Update () {
        if ((DateTime.Now - time).Milliseconds > 1000)
        {
            return;
        }
        time = DateTime.Now;
        transform.Translate(new Vector3(0.2f, 0, 0));
        return;
        SmokeySingleton.Response fireData = SmokeySingleton.getInstance().fireData;
        try
        {
            float xtransform = (float)(transform.position.x - fireData.fires[index][0][0]);
            float ytransform = (float)(transform.position.y - fireData.fires[index][0][1]);
            ((TextMesh)GetComponent(typeof(TextMesh))).text = fireData.fires[index][0][2].ToString();
            transform.Translate(new Vector3(xtransform, ytransform));
        }
        catch (Exception e)
        {
            Console.Out.WriteLine("-----------------");
            Console.Out.WriteLine(e.Message);
        }

    }
}
