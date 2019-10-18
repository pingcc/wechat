package com.ping.wechat.util.crypto;

public class DESUtils {

	private static int BLOCK_SIZE = 8;

	private static final byte PC1[] = // Permutated Choice 1
	{ 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42,
			34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6,
			61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27,
			19, 11, 3 };

	private static final byte PC2[] = // Permutated Choice 2
	{ 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6,
			26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43,
			48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };

	private static final byte IP[] = // Initial Permutation
	{ 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45,
			37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7, 56, 48, 40, 32,
			24, 16, 8, 0, 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28,
			20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6 };

	private static final byte EP[] = // Expansion Permutation
	{ 31, 0, 1, 2, 3, 4, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10, 11, 12, 11, 12, 13, 14,
			15, 16, 15, 16, 17, 18, 19, 20, 19, 20, 21, 22, 23, 24, 23, 24, 25,
			26, 27, 28, 27, 28, 29, 30, 31, 0 };

	private static final byte SBox[][] = // Substitution Box 0~7
	{
			{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 4, 1, 14,
					8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 0, 15, 7, 4,
					14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 15, 12, 8, 2, 4,
					9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 },

			{ 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 0, 14, 7,
					11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 3, 13, 4, 7,
					15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 13, 8, 10, 1, 3,
					15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 },

			{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 6, 4,
					9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 13, 7, 0, 9, 3,
					4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 1, 10, 13, 0, 6, 9,
					8, 7, 4, 15, 14, 3, 11, 5, 2, 12 },

			{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 10, 6, 9,
					0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 13, 8, 11, 5,
					6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 3, 15, 0, 6, 10, 1,
					13, 8, 9, 4, 5, 11, 12, 7, 2, 14 },

			{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 4, 2, 1,
					11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 14, 11, 2, 12,
					4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 11, 8, 12, 7, 1, 14,
					2, 13, 6, 15, 0, 9, 10, 4, 5, 3 },

			{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 9, 14, 15,
					5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 10, 15, 4, 2, 7,
					12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 4, 3, 2, 12, 9, 5, 15,
					10, 11, 14, 1, 7, 6, 0, 8, 13 },

			{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 1, 4, 11,
					13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 13, 0, 11, 7,
					4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 6, 11, 13, 8, 1, 4,
					10, 7, 9, 5, 0, 15, 14, 2, 3, 12 },

			{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 7, 11, 4,
					1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 1, 15, 13, 8,
					10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 2, 1, 14, 7, 4, 10,
					8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

	private static final byte SP[] = // S-Box Output Permutation
	{ 15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4, 17, 30, 9, 1, 7, 23, 13,
			31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24 };

	private static final byte FP[] = // Inverse Initial Permutation
	{ 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45,
			13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11,
			51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49,
			17, 57, 25, 32, 0, 40, 8, 48, 16, 56, 24 };

	private long[] subkey = new long[16];

	private static final DESUtils instance = new DESUtils();

	private DESUtils() {
	}

	/**
	 * 获取本类的实例，通过本方法获取一个DESUtils实例对象
	 * 
	 * @return 获取一个DESUtils实例对象
	 */
	public static DESUtils getInstance() {
		return DESUtils.instance;
	}

	/**
	 * 以默认的密钥解密密文。
	 * 
	 * @param s
	 *            密文字符串。
	 * @return 明文字节。
	 */
	public byte[] decrypt(String s) {
		return decrypt(s.getBytes(), getKey());
	}

	/**
	 * 以默认的密钥解密密文。
	 * 
	 * @param sBytes
	 *            密文字节。
	 * @return 明文字节。
	 */
	public byte[] decrypt(byte[] sBytes) {
		return decrypt(sBytes, getKey());
	}

	/**
	 * 以给定的密钥解密密文字节。
	 * 
	 * @param s
	 *            密文字符串。
	 * @param key
	 *            密钥。
	 * @return
	 */
	public byte[] decrypt(String s, long key) {
		return decrypt(s.getBytes(), key);
	}

	/**
	 * 以给定的密钥解密密文字节。
	 * 
	 * @param sBytes
	 *            密文字节。
	 * @param key
	 *            密钥。
	 * @return byte[]，明文字节。
	 */
	public byte[] decrypt(byte[] sBytes, long key) {
		// DESUtils des = new DESUtils();
		keySchedule(key);
		int resultNum = sBytes.length / 2; // Plaintext is half of cipher
		byte[] byteResult = new byte[resultNum];
		for (int i = 0; i < (sBytes.length / 16); i++) {
			byte[] theBytes = new byte[8];
			for (int j = 0; j <= 7; j++) {
				byte byte1 = (byte) (sBytes[16 * i + 2 * j] - 'a');
				byte byte2 = (byte) (sBytes[16 * i + 2 * j + 1] - 'a');
				theBytes[j] = (byte) (byte1 * 16 + byte2);
			}
			long x = bytes2long(theBytes);
			byte[] tmpResult = new byte[8];
			long2bytes(des(x, false), tmpResult);
			for (int k = 0; k < 8; k++) {
				byteResult[i * 8 + k] = tmpResult[k];
			}
		}
		int length = byteResult[resultNum - 1];
		byte[] result = new byte[resultNum - length];
		System.arraycopy(byteResult, 0, result, 0, resultNum - length);
		return result;
		// return byteResult;
	}

	/**
	 * 以默认的密钥，加密明文字符串。
	 * 
	 * @param s
	 *            明文字符串。
	 * @return 密文字节。
	 */
	public byte[] encrypt(String s) {
		return encrypt(s.getBytes(), getKey());
	}

	/**
	 * 以默认的密钥，加密明文字节。
	 * 
	 * @param sBytes
	 *            明文字节。
	 * @return 密文字节。
	 */
	public byte[] encrypt(byte[] sBytes) {
		return encrypt(sBytes, getKey());
	}

	/**
	 * 以指定的密钥，加密明文字符串。
	 * 
	 * @param s
	 *            明文字符串.
	 * @param key
	 *            密钥。
	 * @return 密文字节。
	 */
	public byte[] encrypt(String s, long key) {
		return encrypt(s.getBytes(), key);
	}

	/**
	 * 以指定的密钥，加密明文字节。
	 */
	public byte[] encrypt(byte[] sBytes, long key) {
		// DESUtils des = new DESUtils();
		keySchedule(key);
		byte space = 0x20;
		int length = sBytes.length;
		int newLength = length + (8 - length % 8) % 8 + 8;
		byte[] newBytes = new byte[newLength];

		for (int i = 0; i < newLength; i++) {
			if (i <= length - 1) {
				newBytes[i] = sBytes[i];
			} else if (i == newLength - 1) {
				newBytes[i] = (byte) (newLength - length);
			} else {
				newBytes[i] = space;
			}
		}

		byte[] rt = new byte[0];
		for (int i = 0; i < (newLength / 8); i++) {
			byte[] theBytes = new byte[8];
			for (int j = 0; j <= 7; j++) {
				theBytes[j] = newBytes[8 * i + j];
			}
			long x = bytes2long(theBytes);
			byte[] result = new byte[8];
			long2bytes(des(x, true), result);
			byte[] doubleResult = new byte[16];
			for (int j = 0; j < 8; j++) {
				doubleResult[2 * j] = (byte) (((((char) result[j]) & 0xF0) >> 4) + 'a');
				doubleResult[2 * j + 1] = (byte) ((((char) result[j]) & 0x0F) + 'a');
			}
			byte[] tmp = new byte[rt.length + doubleResult.length];
			System.arraycopy(rt, 0, tmp, 0, rt.length);
			System.arraycopy(doubleResult, 0, tmp, rt.length,
					doubleResult.length);
			rt = tmp;
		}

		return rt;
	}

	private static long getKey() {
		long l = 3472328296227680304l;
		return l;
	}

	/**
	 * 将字节转换为长整型。
	 * 
	 * @param rd
	 *            输入字节。
	 * @return 输出长整型。
	 */
	public static long bytes2long(byte[] rd) {
		long dd = 0;
		for (int i = 0; i <= 7; i++)
			dd = (dd << 8) | ((long) rd[i] & 0xff);
		return dd;
	}

	/**
	 * 将输入长整型加解密为输出长整型。
	 * 
	 * @param PT
	 *            输入长整型数据。
	 * @param encrypt
	 *            加解密标志位。
	 * @return 处理后的输出长整型数据。
	 */
	public long des(long PT, boolean encrypt) {
		byte i, j, Si;
		int L, R, T1, T2;
		long LR;
		LR = 0; // Initial Permutation
		for (i = 0; i < 64; ++i) {
			LR <<= 1;
			LR |= ((PT & (0x8000000000000000L >>> IP[i])) != 0) ? 1 : 0;
		}
		L = (int) (LR >> 32);
		R = (int) (LR & 0x00000000ffffffffL);
		for (i = 0; i < 16; ++i) {
			PT = 0; // Expansion Permutation for R
			for (j = 0; j < 48; ++j) {
				PT <<= 1;
				PT |= ((R & (0x80000000 >>> EP[j])) != 0) ? 1 : 0;
			}
			if (encrypt)
				PT ^= subkey[i];
			else
				PT ^= subkey[15 - i];
			T1 = 0;
			for (j = 0; j < 8; ++j)

			// Substitute value in S-Boxes
			{
				Si = (byte) (((PT & 0xf80000000000L) >>> 43) | ((PT & 0x040000000000L) >>> 37));
				PT <<= 6;
				T1 <<= 4;
				T1 |= SBox[j][Si];
			}
			T2 = 0; // Permutation after Substitution
			for (j = 0; j < 32; ++j) {
				T2 <<= 1;
				T2 |= ((T1 & (0x80000000 >>> SP[j])) != 0) ? 1 : 0;
			}
			T1 = L ^ T2;
			L = R; // Switch Left and Right half block
			R = T1;
		}
		// Concatenate two half block
		LR = ((long) R << 32) | ((long) L & 0x00000000ffffffffL);
		PT = 0; // Inverse Initial Permutation
		for (i = 0; i < 64; ++i) {
			PT <<= 1;
			PT |= ((LR & (0x8000000000000000L >>> FP[i])) != 0) ? 1 : 0;
		}
		return PT;
	}

	private void keySchedule(long key) {
		byte i, j;
		int C, D;
		long CD; // 56-bit key

		CD = 0; // key input permutation choice 1
		for (i = 0; i < 56; ++i) {
			CD <<= 1;
			CD |= ((key & (0x8000000000000000L >>> PC1[i])) != 0) ? 1 : 0;
		}
		C = (int) (CD >>> 28);
		D = (int) (CD & 0x000000000fffffffL);
		for (i = 0; i < 16; ++i) { // Left Rotate 1 bit
			C <<= 1;
			C |= ((C & 0x10000000) != 0) ? 1 : 0;
			D <<= 1;
			D |= ((D & 0x10000000) != 0) ? 1 : 0;
			if (i != 0 && i != 1 && i != 8 && i != 15) { // Left Rotate 1 bit
				// again
				C <<= 1;
				C |= ((C & 0x10000000) != 0) ? 1 : 0;
				D <<= 1;
				D |= ((D & 0x10000000) != 0) ? 1 : 0;
			}
			CD = (long) C << 28 | (long) D & 0x000000000fffffffL;
			key = 0; // SubKey output permutation choice 2
			for (j = 0; j < 48; ++j) {
				key <<= 1;
				key |= ((CD & (0x80000000000000L >>> PC2[j])) != 0) ? 1 : 0;
			}
			subkey[i] = key;
		}
	}

	/**
	 * 将长整型转换为字节。
	 * 
	 * @param sd
	 *            长整型。
	 * @param dd
	 *            字节。
	 */
	public static void long2bytes(long sd, byte[] dd) {
		for (int i = 7; i >= 0; i--) {
			dd[i] = (byte) sd;
			sd >>>= 8;
		}
	}

	/**
	 * 将输入字节，按照标志位加密/解密为输出字节。缓冲器的大小必须大于8位。
	 * 
	 * @param in
	 *            输入缓冲器。
	 * @param out
	 *            输出缓冲器。
	 * @param encrypt
	 *            加解密的标志位。
	 */
	public void des(byte[] in, byte[] out, boolean encrypt) {
		byte[] temp = new byte[BLOCK_SIZE];
		int blockCount = in.length / BLOCK_SIZE;
		for (int i = 0; i < blockCount; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++) {
				temp[j] = in[i * BLOCK_SIZE + j];
			}

			long2bytes(des(bytes2long(temp), encrypt), temp);

			for (int k = 0; k < BLOCK_SIZE; k++) {
				out[i * BLOCK_SIZE + k] = temp[k];
			}
		}
	}

	public static void main(String[] args) {
		String password = "password";
		byte[] encodePassword = DESUtils.getInstance().encrypt(password);
		byte[] decodePassword = DESUtils.getInstance().decrypt(
				new String(encodePassword));
		System.out.println("明文：" + password);
		System.out.println("密文：" + new String(encodePassword));
		System.out.println("解密：" + new String(decodePassword));
	}
}