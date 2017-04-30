using UnityEngine;
using System.Collections;
using System;
using System.Net;
using System.IO;

public class InitSmokeyData : MonoBehaviour {

    private const string URL = "http://10.171.23.160:5000/route_info";
    //private const string DATA = @"{""object"":{""name"":""Name""}}";
    private HttpWebRequest request;
    private StreamWriter requestWriter;
    private DateTime time;
    System.Random random;

    // Use this for initialization
    void Start () {
        request = (HttpWebRequest)WebRequest.Create(URL);
        request.Method = "GET";
        //request.ContentType = "application/json";
        //request.ContentLength = DATA.Length;
        requestWriter = new StreamWriter(request.GetRequestStream(), System.Text.Encoding.ASCII);
        time = DateTime.Now;
        random = new System.Random();
    }
	
	// Update is called once per frame
	void Update () {
        if ((DateTime.Now - time).Milliseconds > 1000)
        {
            return;
        }
        time = DateTime.Now;
        try
        {
            //requestWriter.Write(DATA);
            //requestWriter.Close();
            WebResponse webResponse = request.GetResponse();
            Stream webStream = webResponse.GetResponseStream();
            StreamReader responseReader = new StreamReader(webStream);
            string response = responseReader.ReadToEnd();
            SmokeySingleton.Response objResponse = JsonUtility.FromJson<SmokeySingleton.Response>(response);
            SmokeySingleton.getInstance().fireData = objResponse;
            transform.Translate(new Vector3(transform.position.x, transform.position.y));
            Console.Out.WriteLine(objResponse);
            responseReader.Close();
        }
        catch (Exception e)
        {
            Console.Out.WriteLine("-----------------");
            Console.Out.WriteLine(e.Message);
        }
    }
}
