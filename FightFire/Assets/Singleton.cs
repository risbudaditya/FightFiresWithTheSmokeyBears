using UnityEngine;
using System.Collections;

public class SmokeySingleton {

    private static SmokeySingleton instance;

    public Response fireData { get; set; }

    public static SmokeySingleton getInstance()
    {
        if(instance == null)
        {
            instance = new SmokeySingleton();
        }
        return instance;
    }

    public class Response
    {
        public double[][] clusters { get; set; }
        public double[][] destinations { get; set; }
        public double[][][] fires { get; set; }
    }

    private SmokeySingleton()
    {

    }
}
