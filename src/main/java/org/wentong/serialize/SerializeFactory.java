package org.wentong.serialize;

import org.wentong.constant.Constant;
import org.wentong.serialize.impl.hessian.HessianDeserializer;
import org.wentong.serialize.impl.hessian.HessianSerializer;
import org.wentong.serialize.impl.json.JSONDeserializer;
import org.wentong.serialize.impl.json.JSONSerializer;

public class SerializeFactory {

    public static Serializer getSerializer(int type) {
        if (type == Constant.ProtocolConstant.SerialType.json) {
            return new JSONSerializer();
        } else if (type == Constant.ProtocolConstant.SerialType.hessian) {
            return new HessianSerializer();
        } else {
            throw new UnsupportedOperationException("Can't find " + type + " of serializer!");
        }
    }

    public static DeSerializer getDeSerializer(int type) {
        if (type == Constant.ProtocolConstant.SerialType.json) {
            return new JSONDeserializer();
        } else if (type == Constant.ProtocolConstant.SerialType.hessian) {
            return new HessianDeserializer();
        } else {
            throw new UnsupportedOperationException("Can't find " + type + " of deSerializer!");
        }
    }

}
