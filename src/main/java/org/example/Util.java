package org.example;


import java.nio.ByteBuffer;

public class Util {

    /**
     * 从 byte 数组中删除指定索引位置的元素。
     *
     * @param array 要操作的 byte 数组
     * @param index 要删除的元素索引
     * @return 删除元素后的 byte 数组
     */
    public static byte[] deleteAt(byte[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        byte[] result = new byte[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        return result;
    }

    /**
     * 在 byte 数组末尾添加一个元素。
     *
     * @param array 要操作的 byte 数组
     * @param value 要添加的元素值
     * @return 添加元素后的 byte 数组
     */
    public static byte[] addAtLast(byte[] array, byte value) {
        byte[] result = new byte[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = value;
        return result;
    }
    public static byte[] addAtIndex(byte[] bytes, int index, byte value) {
        if (index < 0 || index > bytes.length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        // 创建一个新数组，比原数组大1个元素
        byte[] newBytes = new byte[bytes.length + 1];

        // 复制前半部分数据
        System.arraycopy(bytes, 0, newBytes, 0, index);

        // 插入新元素
        newBytes[index] = value;

        // 复制后半部分数据
        System.arraycopy(bytes, index, newBytes, index + 1, bytes.length - index);

        return newBytes;
    }
}
